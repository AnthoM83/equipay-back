package com.proyecto.g2.equipay.commons.dtos.grupo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDtoFull {

    // Propiedades
    private Integer id;
    private String nombre;
    private String descripcion;

    // Asociaciones
    private String idDueño;
    private List<String> idMiembros;
}
