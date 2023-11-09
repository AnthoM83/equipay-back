package com.proyecto.g2.equipay.controllers;
import com.proyecto.g2.equipay.models.EmailRequest;
import com.proyecto.g2.equipay.services.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        // Configura la inicialización de Mockito y establece el servicio en el controlador
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnviarCorreo_Success() {
        // Simula un envío exitoso de correo
        EmailRequest emailRequest = new EmailRequest("destinatario", "asunto", "mensaje");
        Mockito.doNothing().when(emailService).enviarCorreo(emailRequest.getDestinatario(), emailRequest.getAsunto(), emailRequest.getMensaje());

        // Ejecuta el método y verifica que no se produzca ninguna excepción
        emailController.enviarCorreo(emailRequest);
    }

    @Test
    void testEnviarCorreo_GenericException() {
        EmailService emailService = Mockito.mock(EmailService.class);

        Mockito.doThrow(new ResponseStatusException(
                HttpStatus.BAD_REQUEST)).when(emailService).enviarCorreo(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        EmailController emailController = new EmailController(emailService);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            emailController.enviarCorreo(new EmailRequest("destinatario", "asunto", "mensaje"));
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

}


