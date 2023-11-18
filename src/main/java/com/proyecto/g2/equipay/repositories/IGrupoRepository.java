package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IGrupoRepository
        extends JpaRepository<Grupo, Integer>, JpaSpecificationExecutor<Grupo> {
    Optional<Grupo> findByCodigo(String codigo);

}
