package com.evaluacion2.evaluacionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evaluacion2.evaluacionapp.model.entity.Permiso;



public interface PermisoRepository extends JpaRepository<Permiso, Long> {
     Permiso findByIdPermiso(Long idPermiso);
}
