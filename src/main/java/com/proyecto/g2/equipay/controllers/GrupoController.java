package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.grupo.AgregarUsuarioAGrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.services.GrupoService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/grupos")
public class GrupoController {

    // Dependencias
    @Autowired
    GrupoService service;

    // Métodos
    @GetMapping("/{id}")
    public GrupoDto buscarGrupo(@PathVariable Integer id) {
        return service.buscarGrupo(id);
    }

    @GetMapping("/")
    public List<GrupoDto> listarGrupos() {
        return service.listarGrupos();
    }

    @PostMapping("/")
    public void crearGrupo(@Valid @RequestBody GrupoAddDto dto) {
        try {
            service.crearGrupo(dto);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El grupo ya existe.", exc);
        }
    }

    @PutMapping("/{id}")
    public void modificarGrupo(@PathVariable Integer id, @Valid @RequestBody GrupoUpdateDto dto) {
        try {
            service.modificarGrupo(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarGrupo(@PathVariable Integer id) {
        try {
            service.eliminarGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @PostMapping("/{id}/usuarios")
    public void agregarUsuarioAGrupo(@PathVariable Integer id, @Valid @RequestBody AgregarUsuarioAGrupoDto dto) {
        try {
            service.agregarUsuarioAGrupo(id, dto.getIdUsuario());
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

}
