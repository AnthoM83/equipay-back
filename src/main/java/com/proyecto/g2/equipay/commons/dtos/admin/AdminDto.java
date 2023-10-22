package com.proyecto.g2.equipay.commons.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    private String correo;
    private String nombre;
    private String apellido;
}
