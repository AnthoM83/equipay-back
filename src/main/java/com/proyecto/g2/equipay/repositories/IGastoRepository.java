package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGastoRepository
        extends JpaRepository<Gasto, Integer> {

}
