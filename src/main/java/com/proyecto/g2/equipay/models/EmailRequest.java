package com.proyecto.g2.equipay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailRequest {
    private String destinatario;
    private String asunto;
    private String mensaje;

}

