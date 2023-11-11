package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDetailsDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.services.GrupoService;
import com.proyecto.g2.equipay.services.UsuarioService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public UsuarioDto buscarUsuario(@PathVariable String id) {
        try {
            return service.buscarUsuario(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public List<UsuarioDto> listarUsuarios() {
        return service.listarUsuarios();
    }

    @GetMapping("/detalles")
    @PreAuthorize("hasAuthority('Admin')")
    public List<UsuarioDetailsDto> listarUsuariosDetails() {
        return service.listarUsuariosDetails();
    }

    @PostMapping("/")
//    @PreAuthorize("hasAuthority('Admin')")
    public void crearUsuario(@Valid @RequestBody UsuarioAddDto dto) {
        try {
            service.crearUsuario(dto);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El usuario ya existe.", exc);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void modificarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioUpdateDto dto) {
        try {
            service.modificarUsuario(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void eliminarUsuario(@PathVariable String id) {
        try {
            service.eliminarUsuario(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/grupos")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<GrupoDto> listarGrupos(@PathVariable String id) {
        try {
            return grupoService.listarGrupos(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/contrasena")
    public void recuperarContrasena(@PathVariable String id) {
            service.recuperarContrasena(id);
    }

}
