package com.evaluacion2.evaluacionapp.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.evaluacion2.evaluacionapp.model.entity.Permiso;
import com.evaluacion2.evaluacionapp.model.entity.Usuario;




public class UsuarioDTO {

    

    private Long idUsuario;

    private String nombreUsuario;

    private String email;

    private String nombresApellidos;

    private Boolean activo;

    private String password;

    private Date fechaIns;

    private String usuarioIns;

    private Date fechaUpd;

    private String usuarioUpd;

    private List<Long> permisos;

    public UsuarioDTO()
    {
        idUsuario=null;
        nombreUsuario="";
        email="";
        nombresApellidos="";
        activo=true;
        password="";
        fechaIns=new Date();
        usuarioIns="";
        fechaUpd=new Date();
        usuarioUpd="";
        permisos=new ArrayList<>();
    }

    public UsuarioDTO(Usuario user)
    {
        idUsuario=user.getIdUsuario();
        nombreUsuario=user.getNombreUsuario();
        email=user.getEmail();
        nombresApellidos=user.getNombresApellidos();
        activo=user.getActivo()==1;
        password=user.getPassword();
        fechaIns=user.getFechaIns();
        usuarioIns=user.getUsuarioIns();
        fechaUpd=user.getFechaUpd();
        usuarioUpd=user.getUsuarioUpd();
        permisos=new ArrayList<>() ;
        for(Permiso p : user.getPermisos())
        {
            permisos.add(p.getIdPermiso());
        }

        
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaIns() {
        return fechaIns;
    }

    public void setFechaIns(Date fechaIns) {
        this.fechaIns = fechaIns;
    }

    public String getUsuarioIns() {
        return usuarioIns;
    }

    public void setUsuarioIns(String usuarioIns) {
        this.usuarioIns = usuarioIns;
    }

    public Date getFechaUpd() {
        return fechaUpd;
    }

    public void setFechaUpd(Date fechaUpd) {
        this.fechaUpd = fechaUpd;
    }

    public String getUsuarioUpd() {
        return usuarioUpd;
    }

    public void setUsuarioUpd(String usuarioUpd) {
        this.usuarioUpd = usuarioUpd;
    }

    public List<Long> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Long> permisos) {
        this.permisos = permisos;
    }

   

}
