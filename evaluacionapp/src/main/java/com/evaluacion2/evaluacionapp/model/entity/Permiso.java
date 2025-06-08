package com.evaluacion2.evaluacionapp.model.entity;

import jakarta.persistence.*;
import java.util.Date;

import org.hibernate.envers.Audited;

import com.evaluacion2.evaluacionapp.utils.PermisoDTO;

@Entity
@Table(name = "permisos")
@Audited
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long idPermiso;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ins", nullable = false)
    private Date fechaIns;

    @Column(name = "usuario_ins", nullable = false, length = 30)
    private String usuarioIns;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_upd")
    private Date fechaUpd;

    @Column(name = "usuario_upd", length = 30)
    private String usuarioUpd;

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

    public void setValores(PermisoDTO permiso) {
       this.idPermiso=permiso.getIdPermiso();

    
     this.nombre=permiso.getNombre();

   
     this.fechaIns=permiso.getFechaIns();

  
     this.usuarioIns=permiso.getUsuarioIns();

   
     this.fechaUpd=permiso.getFechaUpd();

   
    this.usuarioUpd=permiso.getUsuarioUpd();
    }

    
}

