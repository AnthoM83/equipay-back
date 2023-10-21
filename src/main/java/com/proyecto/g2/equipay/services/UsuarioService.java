package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.commons.enums.EstadoUsuario;
import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import jakarta.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    // Dependencias
    @Autowired
    IUsuarioRepository repo;
    @Autowired
    UsuarioMapper mapper;

    // Métodos
    public UsuarioDto buscarUsuario(String id) {
        Usuario usuario = repo.findById(id).orElseThrow();
        return mapper.toUsuarioDto(usuario);
    }

    public List<UsuarioDto> listarUsuarios() {
        List<Usuario> usuarios = repo.findAll();
        return mapper.toUsuarioDtoList(usuarios);
    }

    public void crearUsuario(UsuarioAddDto dto) {
        Optional<Usuario> find = repo.findById(dto.getCorreo());
        if (find.isEmpty()) {
            Usuario usuario = mapper.toEntity(dto);
            usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
            repo.save(usuario);
        } else {
            throw new EntityExistsException();
        }
    }

    public void modificarUsuario(String id, UsuarioUpdateDto dto) {
        Usuario usuario = repo.findById(id).orElseThrow();
        Usuario usuarioModificado = mapper.toEntity(dto);
        if (!dto.getPassword().isBlank()) {
            usuarioModificado.setPassword(usuario.getPassword());
        }
        usuarioModificado.setCorreo(usuario.getCorreo());
        repo.save(usuarioModificado);
    }

    public void eliminarUsuario(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void bloquearUsuario(String id) {
        Usuario usuario = repo.findById(id).orElseThrow();
        usuario.setEstadoUsuario(EstadoUsuario.BLOQUEADO);
        repo.save(usuario);
    }

    public void desbloquearUsuario(String id) {
        Usuario usuario = repo.findById(id).orElseThrow();
        usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
        repo.save(usuario);
    }

}
