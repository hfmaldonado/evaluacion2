package com.evaluacion2.evaluacionapp.model.entity;

import java.time.LocalDateTime;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import com.evaluacion2.evaluacionapp.controller.AuditListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@RevisionEntity(AuditListener.class)
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "usuario_ins")
    private String usuarioIns;

    @Column(name = "fecha_ins")
    private LocalDateTime fechaIns;

    @Column(name = "usuario_upd")
    private String usuarioUpd;

    @Column(name = "fecha_upd")
    private LocalDateTime fechaUpd;

    public String getUsuarioIns() {
        return usuarioIns;
    }

    public void setUsuarioIns(String usuarioIns) {
        this.usuarioIns = usuarioIns;
    }

    public LocalDateTime getFechaIns() {
        return fechaIns;
    }

    public void setFechaIns(LocalDateTime fechaIns) {
        this.fechaIns = fechaIns;
    }

    public String getUsuarioUpd() {
        return usuarioUpd;
    }

    public void setUsuarioUpd(String usuarioUpd) {
        this.usuarioUpd = usuarioUpd;
    }

    public LocalDateTime getFechaUpd() {
        return fechaUpd;
    }

    public void setFechaUpd(LocalDateTime fechaUpd) {
        this.fechaUpd = fechaUpd;
    }

    // Getters y Setters
    
}
