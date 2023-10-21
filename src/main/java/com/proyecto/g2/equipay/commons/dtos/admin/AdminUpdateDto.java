package com.proyecto.g2.equipay.commons.dtos.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDto {

    @Email(message = "Correo inválido.")
    private String correo;
    @NotBlank(message = "El administrador debe tener un nombre.")
    private String nombre;
    private String apellido;
    @NotBlank(message = "El administrador debe tener una contraseña.")
    private String password;
}
