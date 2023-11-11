package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.pago.PagoAddDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoDto;
import com.proyecto.g2.equipay.services.PagoService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    // Dependencias
    @Autowired
    PagoService service;

    // Métodos
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public PagoDto buscarPago(@PathVariable Integer id) {
        try {
            return service.buscarPago(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pago no encontrado.", exc);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public List<PagoDto> listarPagos() {
        return service.listarPagos();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('Usuario')") 
    public void crearPago(@Valid @RequestBody PagoAddDto dto) {
        service.crearPago(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Usuario')") 
    public void eliminarPago(@PathVariable Integer id) {
        try {
            service.eliminarPago(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pago no encontrado.", exc);
        }
    }

}
