package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.deuda.ConsultarDeudasDto;
import com.proyecto.g2.equipay.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deudas")
public class DeudaController {

    // Dependencias
    @Autowired
    DeudaService service;

    // Métodos
    @GetMapping("/")
    public ConsultarDeudasDto consultarDeudasDeUsuarioEnGrupo(@RequestParam String idUsuario, @RequestParam Integer idGrupo) {
        return service.consultarDeudasDeUsuarioEnGrupo(idGrupo, idUsuario);
    }

}
