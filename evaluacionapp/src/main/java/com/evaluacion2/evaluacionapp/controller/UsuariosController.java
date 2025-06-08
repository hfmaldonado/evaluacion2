package com.evaluacion2.evaluacionapp.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.evaluacion2.evaluacionapp.model.entity.Permiso;
import com.evaluacion2.evaluacionapp.model.entity.Usuario;
import com.evaluacion2.evaluacionapp.service.PermisoService;
import com.evaluacion2.evaluacionapp.service.UsuarioService;
import com.evaluacion2.evaluacionapp.utils.UsuarioDTO;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Getter
@Setter
@Component(value = "usuariosMB")
//@ViewScoped
@Controller
public class UsuariosController  implements Serializable {
   
   @Inject
    private HttpSession session;
   
    @Autowired
    private UsuarioService userService;
    @Autowired
    private PermisoService permisoService;
    @Autowired
    private DataSource dataSource;
    private UsuarioDTO usuario;
    private List<Usuario> usuarios;
   
    private String searchValue;

   private Map<Long, Boolean> usuariosSeleccionados;
  

    public UsuariosController()
    {
        usuario=new UsuarioDTO();
        usuariosSeleccionados= new HashMap<>();
 
    }

    public void verificarSesion() {
        Usuario usr = (Usuario) session.getAttribute("usuario");
        if (usr == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
    }
    public boolean tienePermiso(String perName) {
        Usuario usr = (Usuario) session.getAttribute("usuario");
        Boolean permiso = false;
        for(Permiso p : usr.getPermisos() )
        {
            if(p.getNombre().equalsIgnoreCase(perName))
            {
                permiso=true;
                break;
            }
        }

        return permiso ;
    }
    public String getNombreUsuario() {
        
        Usuario usr = (Usuario) session.getAttribute("usuario");
        return usr != null ? usr.getNombresApellidos() : "Invitado";
    }
    public String getNombreUsuarioCorto() {
        
        Usuario usr = (Usuario) session.getAttribute("usuario");
        return usr != null ? usr.getNombreUsuario() : "Invitado";
    }

   @PostConstruct
    public void mostrarUsuarios() {
       
        usuarios = userService.obtenerTodos();

    }

    public void buscarUsuario() {
        
        usuarios = userService.buscarUsuarios(searchValue);
    }
    public String nuevoUsuario() {
        
         return "nuevo.xhtml?faces-redirect=true";
       
    }

    public void editarUsuario(Usuario usuario) {
       
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getFlash().put("usuarioId", usuario.getIdUsuario());
        try {
            externalContext.redirect("editar.xhtml?idUsuario=" + usuario.getIdUsuario());
        } catch (IOException e) {
             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al redirigir a la página de edición", null));
        }
                  
    }
    
    public void verUsuario(Usuario usuario) {
       
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getFlash().put("usuarioId", usuario.getIdUsuario());
        try {
            externalContext.redirect("ver.xhtml?idUsuario=" + usuario.getIdUsuario());
        } catch (IOException e) {
             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al redirigir a la página de edición", null));
        }
    }
    
    public String filtrosReporte() {
         return "reportefiltro.xhtml?faces-redirect=true";
    }
    
   
    public void generarReporte( String formato ) throws Exception {
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();

        //obtener id usuarios
        String ids="";
       
        if(this.usuariosSeleccionados.size()>0)
        {
            for (Map.Entry<Long, Boolean> entry : usuariosSeleccionados.entrySet()) {
                if (entry.getValue()) {   
                    Long id=entry.getKey();  
                    ids=ids+"'"+id+"',";
                }
            }
            if(ids.length()>0)
            {
                ids=ids.substring(0,ids.length()-1);
            }else
            {
                ids="'TODOS'";
            }
            
           
        }else
        {
            ids="'TODOS'";
           
        }


        externalContext.redirect("/reportpdf?ids="+ids+"&formato="+formato);

        

    }

    @GetMapping("/reportpdf")
    public ResponseEntity<byte[]> verReporte(@RequestParam("ids") String ids,@RequestParam("formato") String formato) throws Exception
    {
                
        // Cargar el reporte Jasper
        InputStream reportStream = getClass().getResourceAsStream("/usuarios_report.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

        // Configurar los datos del reporte 
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idusuarios", ids);
        parameters.put("usuario", this.getNombreUsuarioCorto() );
   
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        byte[] reporteBytes;
        HttpHeaders headers = new HttpHeaders();

        // Exportar a PDF o Excel
        if ("pdf".equalsIgnoreCase(formato)) {
            reporteBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            headers.setContentType(MediaType.APPLICATION_PDF);
            //headers.setContentDisposition(ContentDisposition.builder("attachment").filename("usuarios.pdf").build());
            headers.setContentDisposition(ContentDisposition.builder("inline").filename("usuarios.pdf").build());
        } else if ("excel".equalsIgnoreCase(formato)) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            reporteBytes = outputStream.toByteArray();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("usuarios.xlsx").build());
        } else {
            
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al redirigir a la página de edición", null));
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(reporteBytes, headers, HttpStatus.OK);
        
    }
   
   

}
