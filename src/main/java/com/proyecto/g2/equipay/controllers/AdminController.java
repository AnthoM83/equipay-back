package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.admin.AdminAddDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminUpdateDto;
import com.proyecto.g2.equipay.services.AdminService;
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
@RequestMapping("/api")
public class AdminController {

    // Dependencias
    @Autowired
    AdminService service;

    // Métodos
    @GetMapping("/admins/{id}")
    public AdminDto buscarAdmin(@PathVariable String id) {
        try {
            return service.buscarAdmin(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Admin no encontrado.", exc);
        }
    }

    @GetMapping("/admins")
    public List<AdminDto> listarAdmins() {
        return service.listarAdmins();
    }

    @PostMapping("/admins")
    public void crearAdmin(@Valid @RequestBody AdminAddDto dto) {
        try {
            service.crearAdmin(dto);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El usuario ya existe.", exc);
        }
    }

    @PutMapping("/admins/{id}")
    public void modificarAdmin(@PathVariable String id, @Valid @RequestBody AdminUpdateDto dto) {
        try {
            service.modificarAdmin(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Admin no encontrado.", exc);
        }
    }

    @DeleteMapping("/admins/{id}")
    public void eliminarAdmin(@PathVariable String id) {
        try {
            service.eliminarAdmin(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Admin no encontrado.", exc);
        }
    }

}
