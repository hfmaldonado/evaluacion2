package com.evaluacion2.evaluacionapp.utils;

import java.util.Date;

import com.evaluacion2.evaluacionapp.model.entity.Permiso;



public class PermisoDTO {
 private Long idPermiso;

    
    private String nombre;

   
    private Date fechaIns;

  
    private String usuarioIns;

   
    private Date fechaUpd;

   
    private String usuarioUpd;


    public PermisoDTO(Permiso permiso)
    {
        this.idPermiso=permiso.getIdPermiso();

    
     this.nombre=permiso.getNombre();

   
     this.fechaIns=permiso.getFechaIns();

  
     this.usuarioIns=permiso.getUsuarioIns();

   
     this.fechaUpd=permiso.getFechaUpd();

   
    this.usuarioUpd=permiso.getUsuarioUpd();
    }


    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
