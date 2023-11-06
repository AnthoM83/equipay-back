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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    // Dependencias
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    UsuarioMapper mapper;

    // Métodos
    public UsuarioDto buscarUsuario(String id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        return mapper.toUsuarioDto(usuario);
    }

    public List<UsuarioDto> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return mapper.toUsuarioDtoList(usuarios);
    }

    @Transactional
    public void crearUsuario(UsuarioAddDto dto) {
        Optional<Usuario> find = usuarioRepo.findById(dto.getCorreo());
        if (find.isEmpty()) {
            Usuario usuario = mapper.toEntity(dto);
            usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
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
            usuario.setPassword(dto.getPassword());
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

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<Usuario> userDetail = usuarioRepo.findById(correo);
        // Converting userDetail to UserDetails 
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado " + correo));
    }

}
