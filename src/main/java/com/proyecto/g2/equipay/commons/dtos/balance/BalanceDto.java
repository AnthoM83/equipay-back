package com.proyecto.g2.equipay.commons.dtos.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {

    // Propiedades
    private Integer id;
    private Double deuda;
    private String moneda;

    // Asociaciones
    private Integer idGrupo;
    private String idUsuario;
}
