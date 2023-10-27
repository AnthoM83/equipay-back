package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.commons.enums.EstadoUsuario;
import com.proyecto.g2.equipay.commons.exceptions.GlobalExceptionHandler;
import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.models.UsuarioBase;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.EntityExistsException;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;

@Service
public class UsuarioService {

    // Dependencias
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    UsuarioMapper mapper;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private String SECRET_KEY;

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

    public static String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean isPasswordValid(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String iniciarSesion(String correo, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    correo,
                    password
            );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UsuarioBase usuarioBase = usuarioRepo.findById(correo).orElseThrow();

            if(usuarioBase.getCorreo().isEmpty() || !isPasswordValid(password, usuarioBase.getPassword())) {
                throw new Exception("usuario y/o contraseña inválidos.");
            } else {
                return generateToken(usuarioBase);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken(UsuarioBase usuarioBase) {
        String rol = null;
        if (usuarioBase instanceof Admin){
            rol = "Admin";
        } else if (usuarioBase instanceof Usuario){
            rol = "Usuario";
        }

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 30 * 60 * 1000);
            //new Date(System.currentTimeMillis()

        Map<String, Object> claims = new HashMap<>();
        // Agregar atributos personalizados al token
        claims.put("nombre", usuarioBase.getNombre());
        claims.put("apellido", usuarioBase.getApellido());
        claims.put("rol", rol);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(usuarioBase.getCorreo())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();

        return token;
    }
}
