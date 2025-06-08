package com.evaluacion2.evaluacionapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evaluacion2.evaluacionapp.repository.UsuarioRepository;
import com.evaluacion2.evaluacionapp.model.entity.Permiso;
import com.evaluacion2.evaluacionapp.model.entity.Usuario;
import com.evaluacion2.evaluacionapp.repository.PermisoRepository;

import jakarta.transaction.Transactional;


 @Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PermisoRepository permisoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,PermisoRepository permisoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.permisoRepository = permisoRepository;
    }


    public List<Usuario> obtenerTodos() {
       return usuarioRepository.findAll();
    }

    public List<Usuario> buscarUsuarios(String query) {
        return usuarioRepository.buscarUsuarios(query);
    }

    public Usuario validarUsuario(String nombreUsuario,String password)
    {
        Usuario res=null;

        res=usuarioRepository.findByNombreUsuarioAndPassword(nombreUsuario, password);

        return res;
    }

    public Usuario buscarPorId(Long idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario);
    }

    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void actualizarUsuario(Usuario usuario, List<Long> permisosSeleccionados) {
        if (permisosSeleccionados != null) {
            List<Permiso> permisos = permisoRepository.findAllById(permisosSeleccionados);
            usuario.setPermisos(permisos);
        } else {
            usuario.setPermisos(List.of()); 
        }
        usuarioRepository.save(usuario);
    }

    
}
