package com.proyecto.g2.equipay.commons.dtos.grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDto {

    // Propiedades
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;

    // Asociaciones
    private String idDueño;
}
