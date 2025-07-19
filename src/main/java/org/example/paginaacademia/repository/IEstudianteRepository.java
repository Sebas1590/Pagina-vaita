package org.example.paginaacademia.repository;

import org.example.paginaacademia.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEstudianteRepository extends JpaRepository<Estudiante,String> {
    List<Estudiante> findByNivelId(Long nivelId);
}
