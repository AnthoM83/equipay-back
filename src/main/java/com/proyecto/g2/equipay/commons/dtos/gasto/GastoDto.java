package com.proyecto.g2.equipay.commons.dtos.gasto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDto {

    // Propiedades
    private Integer id;
    private Long monto;
    private String moneda;
    private String descripcion;
    private LocalDate fecha;

    // Asociaciones
    private String idGrupo;
    private String idCubiertoPor;
    private List<String> idBeneficiados;
    private String idCategoria;
}
