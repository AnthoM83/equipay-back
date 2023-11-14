package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGrupoRepository
        extends JpaRepository<Grupo, Integer> {
    Optional<Grupo> findByCodigo(String codigo);

}
