package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBalanceRepository
        extends JpaRepository<Balance, Integer> {

}
