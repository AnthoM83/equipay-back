package com.proyecto.g2.equipay.commons.dtos.pago;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoAddDto {

    // Propiedades
    private Double monto;
    private String moneda;
    private LocalDate fecha;

    // Asociaciones
    private Integer idGrupo;
    private String idRealiza;
    private String idRecibe;
}
