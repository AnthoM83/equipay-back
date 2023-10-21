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
    private Long monto;
    private String moneda;
    private LocalDate fecha;

    // Asociaciones
    private String idGrupo;
    private String idRealiza;
    private String idRecibe;
}
