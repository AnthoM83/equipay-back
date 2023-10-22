package com.proyecto.g2.equipay.commons.dtos.grupo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoUpdateDto {

    // Propiedades
    @NotBlank(message = "El grupo debe tener un nombre.")
    private String nombre;
    private String descripcion;
}
