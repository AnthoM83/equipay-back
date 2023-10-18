package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.RegistrarUsuarioDto;
import com.proyecto.g2.equipay.models.EstadoUsuario;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService
        implements IUsuarioService {

    @Autowired
    IUsuarioRepository repo;

    public void registrarUsuario(RegistrarUsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.getCorreo());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setPassword(dto.getPassword());
        usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
        repo.save(usuario);
    }

}
