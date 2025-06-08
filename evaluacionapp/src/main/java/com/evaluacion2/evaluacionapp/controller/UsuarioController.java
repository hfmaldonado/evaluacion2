package com.evaluacion2.evaluacionapp.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.evaluacion2.evaluacionapp.model.entity.Permiso;
import com.evaluacion2.evaluacionapp.model.entity.Usuario;
import com.evaluacion2.evaluacionapp.service.PermisoService;
import com.evaluacion2.evaluacionapp.service.UsuarioService;
import com.evaluacion2.evaluacionapp.utils.PermisoDTO;
import com.evaluacion2.evaluacionapp.utils.UsuarioDTO;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component(value = "usuarioMB")
@ViewScoped
public class UsuarioController  implements Serializable {
   
    @Autowired
    private UsuariosController controladorUsers;

    @Autowired
    private UsuarioService userService;
    @Autowired
    private PermisoService permisoService;
    
    private UsuarioDTO usuario;
    private List<PermisoDTO> permisosDisponibles;

    public UsuarioController()
    {
        

    }
    @PostConstruct
    public void init() {

        permisosDisponibles=new ArrayList<>() ;        
        for(Permiso p : permisoService.obtenerTodos())
        {
            permisosDisponibles.add(new PermisoDTO(p));
        }
        usuario=new UsuarioDTO();
        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String idUsuario = params.get("idUsuario");
    
        if (idUsuario != null) {
         
            Usuario user= userService.buscarPorId(Long.parseLong(idUsuario));
            this.usuario=new UsuarioDTO(user);
            this.usuario.setPassword("");
        }
    
      
    }

    public String guardarUsuario() {
         
        Usuario existe=userService.buscarPorId(usuario.getIdUsuario());
        if(existe!=null)
        {
            String mensaje="El ID de usuario ya existe";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            return null;
        }
        List<Permiso> permiList=new ArrayList<>();
        for(Long pi:usuario.getPermisos())
        {
            Permiso pp=permisoService.buscarPorId(pi);
            permiList.add(pp);
        }
        Usuario user=new Usuario();
        user.setValores(usuario,permiList);

        userService.guardarUsuario(user);
        controladorUsers.buscarUsuario();
        return "usuarios.xhtml?faces-redirect=true";
    }

   
    public String actualizarUsuario() {
        Usuario user = userService.buscarPorId(usuario.getIdUsuario());
        List<Permiso> permiList=new ArrayList<>();
        List<Long> permisosSeleccionados=new ArrayList<>();
        for(Long pi:usuario.getPermisos())
        {
            Permiso pp=permisoService.buscarPorId(pi);
            permiList.add(pp);
             permisosSeleccionados.add(pi);
        }
       
        user.setValores(usuario,permiList);
        
        userService.actualizarUsuario(user, permisosSeleccionados);
        controladorUsers.buscarUsuario();
       return "usuarios.xhtml?faces-redirect=true";
    }
   
   
}
