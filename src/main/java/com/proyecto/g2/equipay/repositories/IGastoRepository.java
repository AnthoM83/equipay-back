package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IGastoRepository
        extends JpaRepository<Gasto, Integer>, JpaSpecificationExecutor<Gasto> {

}
