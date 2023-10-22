package com.proyecto.g2.equipay.commons.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFullDto {

    private String correo;
    private String nombre;
    private String apellido;
    private String password;
    private String estadoUsuario;
}
