package com.proyecto.g2.equipay.commons.dtos.estadistica;

import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses {

    private YearMonth mes;
    private Double valor;
}
