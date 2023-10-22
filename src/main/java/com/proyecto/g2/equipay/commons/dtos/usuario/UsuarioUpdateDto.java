package com.proyecto.g2.equipay.commons.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDto {

    @NotBlank(message = "El usuario debe tener un nombre.")
    private String nombre;
    private String apellido;
    @NotBlank(message = "El usuario debe tener una contraseña.")
    private String password;
}
