package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.services.EstadisticaService;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    // Dependencias
    @Autowired
    EstadisticaService estadisticaSvc;

    // Métodos
    @GetMapping("/grupos-con-actividad-en-mes")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> valorTotalDeGastosEnMes(YearMonth yearMonth) {
        try {
            return ResponseEntity.ok(estadisticaSvc.gruposConActividadEnMes(yearMonth));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

    @GetMapping("/promedio-gastos")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> promedioDeGastos() {
        try {
            var query = estadisticaSvc.promedioDeGastos();
            return ResponseEntity.ok(query.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al consultar la estadística.");
        }
    }

}
