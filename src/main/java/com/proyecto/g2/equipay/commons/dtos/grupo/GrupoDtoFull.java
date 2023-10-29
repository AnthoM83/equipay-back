package com.proyecto.g2.equipay.commons.dtos.grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDtoFull {

    // Propiedades
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;

    // Asociaciones
    private String idDueño;
    private List<String> idMiembros;
}
