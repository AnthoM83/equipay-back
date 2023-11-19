package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.services.ResumenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumenes")
public class ResumenesController {

    // Dependencias
    @Autowired
    ResumenService resumenSvc;

    // Métodos
    @GetMapping("/usuarios/{id}/cantidad-gastos-cubiertos")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<String> cantidadDeGastosCubiertosPorUsuario(@PathVariable String id) {
        try {
            var query = resumenSvc.cantidadDeGastosCubiertosPorUsuario(id);
            return ResponseEntity.ok(query.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/usuarios/{id}/valor-total-gastos-cubiertos")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<Object> valorTotalDeGastosCubiertosPorUsuario(@PathVariable String id) {
        try {
            return ResponseEntity.ok(resumenSvc.valorTotalDeGastosCubiertosPorUsuario(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/usuarios/{id}/cantidad-pagos-realizados")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<String> cantidadDePagosRealizadosPorUsuario(@PathVariable String id) {
        try {
            var query = resumenSvc.cantidadDePagosRealizadosPorUsuario(id);
            return ResponseEntity.ok(query.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/usuarios/{id}/valor-total-pagos-realizados")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<Object> valorTotalDePagosRealizadosPorUsuario(@PathVariable String id) {
        try {
            return ResponseEntity.ok(resumenSvc.valorTotalDePagosRealizadosPorUsuario(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/grupos/{id}/cantidad-gastos")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> cantidadDeGastosEnGrupo(@PathVariable Integer id) {
        try {
            var query = resumenSvc.cantidadDeGastosEnGrupo(id);
            return ResponseEntity.ok(query.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/grupos/{id}/valor-total-gastos")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> valorTotalDeGastosEnGrupo(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(resumenSvc.valorTotalDeGastosEnGrupo(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/grupos/{id}/cantidad-pagos")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> cantidadDePagosEnGrupo(@PathVariable Integer id) {
        try {
            var query = resumenSvc.cantidadDePagosEnGrupo(id);
            return ResponseEntity.ok(query.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/grupos/{id}/valor-total-pagos")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> valorTotalDePagosEnGrupo(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(resumenSvc.valorTotalDePagosEnGrupo(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/usuarios/{id}/valor-total-pagos-realizados/{moneda}")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<Object> valorTotalDePagosRealizadosPorUsuarioEnGrupos(@PathVariable String id, @PathVariable String moneda) {
        try {
            return ResponseEntity.ok(resumenSvc.valorTotalDePagosRealizadosPorUsuarioEnGrupos(id, moneda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/usuarios/{id}/gastos-cubiertos-ultimos-doce-meses/{moneda}")
    @PreAuthorize("hasAuthority('Usuario')")
    public ResponseEntity<Object> valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses(@PathVariable String id, @PathVariable String moneda) {
        try {
            return ResponseEntity.ok(resumenSvc.valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses(id, moneda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

}
