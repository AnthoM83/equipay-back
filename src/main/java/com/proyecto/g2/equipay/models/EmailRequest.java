package com.proyecto.g2.equipay.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {
    private String destinatario;
    private String asunto;
    private String mensaje;

}

