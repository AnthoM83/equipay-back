package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.commons.dtos.security.AuthRequest;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.security.JwtService;
import com.proyecto.g2.equipay.services.UsuarioService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Dependencias
    @Autowired
    UsuarioService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Métodos
    @PostMapping("/registro")
    public void crearUsuario(@Valid @RequestBody UsuarioAddDto dto) {
        try {
            service.crearUsuario(dto);
        } catch (EntityExistsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El correo ingresado ya existe.", exc);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                service.saveExpoPushToken(authRequest.getCorreo(), authRequest.getExpoPushToken());
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getCorreo()));
            } else {
                throw new UsernameNotFoundException("Login invalido");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El usuario o la contraseña ingresada no son correctos, vuelva a intentarlo.");
        }
    }

}
