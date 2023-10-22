package com.proyecto.g2.equipay.commons.dtos.grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDto {

    // Propiedades
    private Integer id;
    private String nombre;
    private String descripcion;

    // Asociaciones
    private String idDueño;
}
