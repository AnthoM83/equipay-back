package com.proyecto.g2.equipay.commons.dtos.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String correo;
    private String password;
    private String expoPushToken;
}
