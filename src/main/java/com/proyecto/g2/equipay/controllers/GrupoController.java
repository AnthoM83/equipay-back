package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.AgregarUsuarioAGrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoDto;
import com.proyecto.g2.equipay.services.GastoService;
import com.proyecto.g2.equipay.services.GrupoService;
import com.proyecto.g2.equipay.services.PagoService;
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
    GrupoService grupoService;
    @Autowired
    GastoService gastoService;
    @Autowired
    PagoService pagoService;

    // Métodos
    @GetMapping("/{id}")
    public GrupoDto buscarGrupo(@PathVariable Integer id) {
        return grupoService.buscarGrupo(id);
    }

    @GetMapping("/")
    public List<GrupoDto> listarGrupos() {
        return grupoService.listarGrupos();
    }

    @PostMapping("/")
    public void crearGrupo(@Valid @RequestBody GrupoAddDto dto) {
        try {
            grupoService.crearGrupo(dto);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El grupo ya existe.", exc);
        }
    }

    @PutMapping("/{id}")
    public void modificarGrupo(@PathVariable Integer id, @Valid @RequestBody GrupoUpdateDto dto) {
        try {
            grupoService.modificarGrupo(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarGrupo(@PathVariable Integer id) {
        try {
            grupoService.eliminarGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @PostMapping("/{id}/usuarios")
    public void agregarUsuarioAGrupo(@PathVariable Integer id, @Valid @RequestBody AgregarUsuarioAGrupoDto dto) {
        try {
            grupoService.agregarUsuarioAGrupo(id, dto.getIdUsuario());
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/gastos")
    public List<GastoDto> gastosEnGrupo(@PathVariable Integer id) {
        try {
            return gastoService.listarGastosEnGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/gastos/{idUsuario}")
    public List<GastoDto> gastosEnGrupo(@PathVariable Integer id, @PathVariable String idUsuario) {
        try {
            return gastoService.listarGastosDeUsuarioEnGrupo(idUsuario, id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/pagos")
    public List<PagoDto> pagosEnGrupo(@PathVariable Integer id) {
        try {
            return pagoService.listarPagosEnGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/pagos/{idUsuario}")
    public List<PagoDto> pagosEnGrupo(@PathVariable Integer id, @PathVariable String idUsuario) {
        try {
            return pagoService.listarPagosDeUsuarioEnGrupo(idUsuario, id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

}
