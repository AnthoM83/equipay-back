package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.categoria.AddEditCategoriaDto;
import com.proyecto.g2.equipay.models.Categoria;
import com.proyecto.g2.equipay.services.CategoriaService;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    // Dependencias
    @Autowired
    CategoriaService service;

    // Métodos
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public Categoria buscarCategoria(@PathVariable Integer id) {
        try {
            return service.buscarCategoria(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Categoría no encontrada.", exc);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<Categoria> listarCategorias() {
        return service.listarCategorias();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public void crearCategoria(@Valid @RequestBody AddEditCategoriaDto categoria) {
        try {
            service.crearCategoria(categoria);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La categoría ya existe.", exc);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void modificarCategoria(@PathVariable Integer id, @Valid @RequestBody AddEditCategoriaDto categoria) {
        try {
            service.modificarCategoria(id, categoria);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Categoría no encontrada.", exc);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void eliminarCategoria(@PathVariable Integer id) {
        try {
            service.eliminarCategoria(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Categoría no encontrada.", exc);
        }
    }

}
