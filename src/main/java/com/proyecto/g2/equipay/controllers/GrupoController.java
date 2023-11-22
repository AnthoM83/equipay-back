package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.*;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.services.GastoService;
import com.proyecto.g2.equipay.services.GrupoService;
import com.proyecto.g2.equipay.services.PagoService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

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
    public GrupoDtoFull buscarGrupo(@PathVariable Integer id) {
        return grupoService.buscarGrupo(id);
    }

    @GetMapping("/")
    public List<GrupoDto> listarGrupos() {
        return grupoService.listarGrupos();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<Integer> crearGrupo(@Valid @RequestBody GrupoAddDto dto) {
        try {
            Integer grupoId = grupoService.crearGrupo(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(grupoId);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El grupo ya existe.", exc);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Usuario')")
    public void modificarGrupo(@PathVariable Integer id, @Valid @RequestBody GrupoUpdateDto dto) {
        try {
            grupoService.modificarGrupo(id, dto);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Usuario')")
    public void eliminarGrupo(@PathVariable Integer id) {
        try {
            grupoService.eliminarGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @PostMapping("/{id}/usuarios")
    @PreAuthorize("hasAuthority('Usuario')")
    public void agregarUsuarioAGrupo(@PathVariable Integer id, @Valid @RequestBody AgregarUsuarioAGrupoDto dto) {
        try {
            grupoService.agregarUsuarioAGrupo(id, dto.getIdUsuario());
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El usuario elegido ya pertenece al grupo.", exc);
        }
    }

    @PostMapping("/{id}/usuarios-link")
    @PreAuthorize("hasAuthority('Usuario')")
    public void agregarUsuarioAGrupoLink(@PathVariable Integer id, @RequestParam("idUsuario") @NotBlank String idUsuario) {
        try {
            grupoService.agregarUsuarioAGrupo(id, idUsuario);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @PostMapping("/usuarios-codigo")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public ResponseEntity<String> agregarUsuarioAGrupoCodigo(@RequestBody UnirseAGrupoDto unirseAGrupoDto) {
        try {
            grupoService.agregarUsuarioAGrupo(unirseAGrupoDto.getCodGrupo(), unirseAGrupoDto.getIdUsuario());
            return ResponseEntity.status(HttpStatus.OK).body("Se ha unido al grupo correctamente");
        }
        catch (NoSuchElementException exc) {
            return ResponseEntity.status(HttpStatus.OK).body(exc.getMessage());
        }
    }

    @GetMapping("/{id}/gastos")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<GastoDto> gastosEnGrupo(@PathVariable Integer id) {
        try {
            return gastoService.listarGastosEnGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/gastos/{idUsuario}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<GastoDto> gastosEnGrupo(@PathVariable Integer id, @PathVariable String idUsuario) {
        try {
            return gastoService.listarGastosDeUsuarioEnGrupo(idUsuario, id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/pagos")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<PagoDto> pagosEnGrupo(@PathVariable Integer id) {
        try {
            return pagoService.listarPagosEnGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/pagos/{idUsuario}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<PagoDto> pagosEnGrupo(@PathVariable Integer id, @PathVariable String idUsuario) {
        try {
            return pagoService.listarPagosDeUsuarioEnGrupo(idUsuario, id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @GetMapping("/{id}/usuarios")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public List<UsuarioDto> usuariosEnGrupo(@PathVariable Integer id) {
        try {
            return grupoService.listarUsuariosEnGrupo(id);
        } catch (NoSuchElementException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Grupo no encontrado.", exc);
        }
    }

    @PostMapping("/{id}/invitar-amigo/{correo}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public void invitarAmigo(@PathVariable Integer id, @PathVariable String correo){
        grupoService.invitarAmigo(id, correo);
    }

    @PostMapping("/invitar-amigos")
    @PreAuthorize("hasAnyAuthority('Admin', 'Usuario')")
    public void invitarAmigos(@RequestBody InvitarAmigosDto invitarAmigosDto){
        grupoService.invitarAmigos(invitarAmigosDto.getIdGrupo(), invitarAmigosDto.getIdUsuarios());
    }

}
