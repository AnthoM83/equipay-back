package com.proyecto.g2.equipay.security;

import com.proyecto.g2.equipay.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserDetailsRepository
        extends JpaRepository<Usuario, String>, JpaSpecificationExecutor<Usuario> {

}
