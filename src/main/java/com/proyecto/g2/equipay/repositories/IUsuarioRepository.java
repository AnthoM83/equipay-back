package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository
        extends CrudRepository<Usuario, String> {
    
}
