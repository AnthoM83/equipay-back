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
    private Double monto;
    private String moneda;
    private String descripcion;
    private LocalDate fecha;

    // Asociaciones
    private Integer idGrupo;
    private String idCubiertoPor;
    private List<String> idBeneficiados;
    private Integer idCategoria;
}
