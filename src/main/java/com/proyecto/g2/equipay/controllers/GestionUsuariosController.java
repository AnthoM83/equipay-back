package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.RegistrarUsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.UsuarioDto;
import com.proyecto.g2.equipay.services.GestionUsuariosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gestion-usuarios")
public class GestionUsuariosController {
    
    @Autowired
    GestionUsuariosService service;
    
    @GetMapping("/")
    public List<UsuarioDto> listarUsuarios() {
        return service.listarUsuarios();
    }
    
    @PostMapping("/")
    public void crearUsuario(RegistrarUsuarioDto dto) {
        service.crearUsuario(dto);
    }
    
}
