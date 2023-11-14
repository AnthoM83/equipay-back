package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPagoRepository
        extends JpaRepository<Pago, Integer>, JpaSpecificationExecutor<Pago> {

}
