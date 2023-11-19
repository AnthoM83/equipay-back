package com.proyecto.g2.equipay.commons.dtos.estadistica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorTotalPagosRealizadosPorUsuarioEnGrupos {

    private Integer idGrupo;
    private String nombreGrupo;
    private Double valor;
}
