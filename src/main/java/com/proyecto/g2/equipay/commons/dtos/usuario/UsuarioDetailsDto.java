package com.proyecto.g2.equipay.commons.dtos.usuario;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDetailsDto {

    private String correo;
    private String nombre;
    private String apellido;
    private LocalDate fechaCreacion;
    private String estadoUsuario;
}
