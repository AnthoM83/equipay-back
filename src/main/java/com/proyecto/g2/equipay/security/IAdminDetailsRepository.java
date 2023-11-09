package com.proyecto.g2.equipay.security;

import com.proyecto.g2.equipay.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAdminDetailsRepository
        extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

}
