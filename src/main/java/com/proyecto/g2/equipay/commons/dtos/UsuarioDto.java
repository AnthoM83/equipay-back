package com.proyecto.g2.equipay.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private String correo;
    private String nombre;
    private String apellido;
    private String estadoUsuario;
}
