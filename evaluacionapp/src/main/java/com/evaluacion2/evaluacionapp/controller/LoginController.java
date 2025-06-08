package com.evaluacion2.evaluacionapp.controller;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import com.evaluacion2.evaluacionapp.model.entity.Usuario;
import com.evaluacion2.evaluacionapp.service.UsuarioService;
import com.evaluacion2.evaluacionapp.utils.EvaluacionUtils;

@Getter
@Setter
@Component(value = "loginMB")
@ViewScoped
public class LoginController implements Serializable{

@Inject
private HttpSession session;

private String username;
private String password;

@Autowired
private UsuarioService userService;


public String validarUsuario() {
    
    String clave=EvaluacionUtils.encriptarSHA256(password);
    Usuario user=userService.validarUsuario(username, clave);
    if(user!=null)
    {
         session.setAttribute("usuario",user);
         return "usuarios.xhtml?faces-redirect=true";
    }else
    {
        String mensaje="Usuario o Clave no válida";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        return null;
    }
    
    
}

public String logout() {
    session.removeAttribute("usuario");
    session.invalidate();
    return "index.xhtml?faces-redirect=true";
}



}
