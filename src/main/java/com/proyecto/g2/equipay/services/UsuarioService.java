package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDetailsDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.commons.enums.EstadoUsuario;
import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import jakarta.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    // Dependencias
    @Autowired
    protected IUsuarioRepository usuarioRepo;
    @Autowired
    UsuarioMapper mapper;
    @Autowired
    PasswordEncoder encoder;

    // Métodos
    public UsuarioDto buscarUsuario(String id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        return mapper.toUsuarioDto(usuario);
    }

    public List<UsuarioDto> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return mapper.toUsuarioDtoList(usuarios);
    }

    public List<UsuarioDetailsDto> listarUsuariosDetails() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return mapper.toUsuarioDetailsDtoList(usuarios);
    }

    @Transactional
    public void crearUsuario(UsuarioAddDto dto) {
        Optional<Usuario> find = usuarioRepo.findById(dto.getCorreo());
        if (find.isEmpty()) {
            Usuario usuario = mapper.toEntity(dto);
            usuario.setPassword(encoder.encode(dto.getPassword()));
            usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
            usuario.setFechaCreacion(LocalDate.now());
            usuarioRepo.save(usuario);
        } else {
            throw new EntityExistsException();
        }
    }

    @Transactional
    public void modificarUsuario(String id, UsuarioUpdateDto dto) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(encoder.encode(dto.getPassword()));
        }
        usuarioRepo.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(String id) {
        if (usuarioRepo.existsById(id)) {
            usuarioRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void bloquearUsuario(String id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        usuario.setEstadoUsuario(EstadoUsuario.BLOQUEADO);
        usuarioRepo.save(usuario);
    }

    @Transactional
    public void desbloquearUsuario(String id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
        usuarioRepo.save(usuario);
    }

}
