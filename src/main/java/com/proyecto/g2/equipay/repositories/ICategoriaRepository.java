package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository
        extends JpaRepository<Categoria, Integer> {

}
