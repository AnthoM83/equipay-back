package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDetailsDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.commons.enums.EstadoUsuario;
import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.models.UsuarioBase;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import jakarta.persistence.EntityExistsException;
import com.proyecto.g2.equipay.services.EmailService;

import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    // Dependencias
    @Autowired
    protected IUsuarioRepository usuarioRepo;
    @Autowired
    UsuarioMapper mapper;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";

    private static final int LONGITUD_CONTRASENA = 12;

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

    public void recuperarContrasena(String correo) {
        // Genera una nueva contraseña segura
        String nuevaContrasena = generarContrasenaSegura();

        // Actualiza la contraseña en la base de datos
        Usuario usuario = usuarioRepo.findById(correo).orElseThrow();
        usuario.setPassword(nuevaContrasena);

        usuarioRepo.save(usuario);

        // Envía un correo electrónico al usuario con la nueva contraseña
        String mensaje = "Tu nueva contraseña es: " + nuevaContrasena;
        emailService.enviarCorreo(usuario.getCorreo(), "Recuperación de Contraseña", mensaje);
    }

    public static String generarContrasenaSegura() {
        SecureRandom random = new SecureRandom();
        StringBuilder contrasena = new StringBuilder(LONGITUD_CONTRASENA);

        for (int i = 0; i < LONGITUD_CONTRASENA; i++) {
            int indice = random.nextInt(CARACTERES.length());
            char caracter = CARACTERES.charAt(indice);
            contrasena.append(caracter);
        }

        return contrasena.toString();
    }

    @Transactional
    public void saveExpoPushToken(String id, String expoPushToken) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        usuario.setExpoPushToken(expoPushToken);
        usuarioRepo.save(usuario);
    }

}
