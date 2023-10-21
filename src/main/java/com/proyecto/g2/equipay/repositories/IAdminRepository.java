package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository
        extends JpaRepository<Admin, String> {

}
