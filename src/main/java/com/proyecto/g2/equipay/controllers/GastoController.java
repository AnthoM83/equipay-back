package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoUpdateDto;
import com.proyecto.g2.equipay.services.GastoService;
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
@RequestMapping("/api/gastos")
public class GastoController {

    // Dependencias
    @Autowired
    GastoService service;

    // Métodos
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public GastoDto buscarGasto(@PathVariable Integer id) {
        try {
            return service.buscarGasto(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Gasto no encontrado.", exc);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public List<GastoDto> listarGastos() {
        return service.listarGastos();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('Usuario')")
    public void crearGasto(@Valid @RequestBody GastoAddDto dto) {
        service.crearGasto(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Usuario')")
    public void modificarGasto(@PathVariable Integer id, @Valid @RequestBody GastoUpdateDto dto) {
        try {
            service.modificarGasto(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Gasto no encontrado.", exc);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Usuario')")
    public void eliminarGasto(@PathVariable Integer id) {
        try {
            service.eliminarGasto(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Gasto no encontrado.", exc);
        }
    }

}
