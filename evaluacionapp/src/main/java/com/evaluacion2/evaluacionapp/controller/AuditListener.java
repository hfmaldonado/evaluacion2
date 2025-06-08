package com.evaluacion2.evaluacionapp.controller;

import java.time.LocalDateTime;

import org.hibernate.envers.RevisionListener;

import com.evaluacion2.evaluacionapp.model.entity.AuditRevisionEntity;
import com.evaluacion2.evaluacionapp.model.entity.Usuario;

import jakarta.faces.context.FacesContext;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpSession;

public class AuditListener implements RevisionListener{
    
   
    
    @PrePersist
    public void setCreationAudit(AuditRevisionEntity revisionEntity) {
        revisionEntity.setUsuarioIns(getCurrentUser());
        revisionEntity.setFechaIns(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdateAudit(AuditRevisionEntity revisionEntity) {
        revisionEntity.setUsuarioUpd(getCurrentUser());
        revisionEntity.setFechaUpd(LocalDateTime.now());
    }

    private String getCurrentUser() {
        return getNombreUsuarioCorto();// SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevision = (AuditRevisionEntity) revisionEntity;
        auditRevision.setUsuarioIns(getCurrentUser());
        auditRevision.setFechaIns(LocalDateTime.now());
    }

    public String getNombreUsuarioCorto() {
        
       FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Usuario usr = (Usuario) session.getAttribute("usuario");
            return usr != null ? usr.getNombreUsuario() : "invitado";
        }
        return "invitado";
    }
    
}
