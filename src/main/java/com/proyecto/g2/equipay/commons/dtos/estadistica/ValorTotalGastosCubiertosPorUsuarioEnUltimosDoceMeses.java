package com.proyecto.g2.equipay.commons.dtos.estadistica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses implements Comparable<ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses> {

    private String fechaCompleta;
    private String mesAbreviado;
    private Double valor;

    @Override
    public int compareTo(ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses otro) {
        return this.fechaCompleta.compareTo(otro.fechaCompleta);
    }
}
