package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.services.GrupoService;
import com.proyecto.g2.equipay.services.UsuarioService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // Dependencias
    @Autowired
    UsuarioService service;
    @Autowired
    GrupoService grupoService;

    // Métodos
    @GetMapping("/{id}")
    public UsuarioDto buscarUsuario(@PathVariable String id) {
        try {
            return service.buscarUsuario(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @GetMapping("/")
    public List<UsuarioDto> listarUsuarios() {
        return service.listarUsuarios();
    }

    @PostMapping("/")
    public void crearUsuario(@Valid @RequestBody UsuarioAddDto dto) {
        try {
            String passwordEncoded = UsuarioService.encodePassword(dto.getPassword());
            dto.setPassword(passwordEncoded);
            service.crearUsuario(dto);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El usuario ya existe.", exc);
        }
    }

    @PutMapping("/{id}")
    public void modificarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioUpdateDto dto) {
        try {
            if (!dto.getPassword().isBlank() || !dto.getPassword().isEmpty()) {
                String passwordEncoded = UsuarioService.encodePassword(dto.getPassword());
                dto.setPassword(passwordEncoded);
            }
            service.modificarUsuario(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable String id) {
        try {
            service.eliminarUsuario(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/grupos")
    public List<GrupoDto> listarGrupos(@PathVariable String id) {
        try {
            return grupoService.listarGrupos(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String id, String password) {
        try {
            String token = service.iniciarSesion(id, password);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación");
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion() {
        // Implementa la lógica para cerrar sesión
        return "redirige-a-pagina-de-login";
    }
}
