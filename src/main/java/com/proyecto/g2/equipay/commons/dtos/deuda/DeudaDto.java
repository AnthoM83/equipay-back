package com.proyecto.g2.equipay.commons.dtos.deuda;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeudaDto {

    private String moneda;
    private Double deudaEnGrupo;
    private List<Sugerencia> sugerencias;
}
