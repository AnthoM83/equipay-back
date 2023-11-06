package com.proyecto.g2.equipay.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

    @Service
    public class EmailService {

        private final JavaMailSender javaMailSender;

        @Autowired
        public EmailService(JavaMailSender javaMailSender) {
            this.javaMailSender = javaMailSender;
        }

        public void enviarCorreo(String destinatario, String asunto, String mensaje) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(destinatario);
            mailMessage.setSubject(asunto);
            mailMessage.setText(mensaje);
            javaMailSender.send(mailMessage);
        }
    }
