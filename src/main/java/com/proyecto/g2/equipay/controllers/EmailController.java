package com.proyecto.g2.equipay.controllers;

import com.proyecto.g2.equipay.models.EmailRequest;
import com.proyecto.g2.equipay.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/enviar-correo")
    public void enviarCorreo(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.enviarCorreo(emailRequest.getDestinatario(), emailRequest.getAsunto(), emailRequest.getMensaje());
        } catch (MailException e) {
            // En caso de excepción relacionada con el envío del correo
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar el correo: " + e.getMessage(), e);
        } catch (Exception e) {
            // Otras excepciones
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error en la solicitud: " + e.getMessage(), e);
        }
    }
}

