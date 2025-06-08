package com.evaluacion2.evaluacionapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evaluacion2.evaluacionapp.model.entity.Permiso;
import com.evaluacion2.evaluacionapp.model.entity.Usuario;
import com.evaluacion2.evaluacionapp.repository.PermisoRepository;


@Service
public class PermisoService {
    PermisoRepository permisoRepository;


    public PermisoService(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }


    public List<Permiso> obtenerTodos() {
       return permisoRepository.findAll();
    }

     public Permiso buscarPorId(Long idPermiso) {
        return permisoRepository.findByIdPermiso(idPermiso);
    }
   

}
