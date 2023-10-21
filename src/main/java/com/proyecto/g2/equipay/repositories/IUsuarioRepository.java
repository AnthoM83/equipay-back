package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository
        extends JpaRepository<Usuario, String> {

}
