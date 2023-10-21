package com.proyecto.g2.equipay.commons.dtos.gasto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoUpdateDto {

    // Propiedades
    @NotNull(message = "El gasto debe tener un monto.")
    private Long monto;
    @NotBlank(message = "El gasto debe tener una moneda.")
    private String moneda;
    private String descripcion;
    private LocalDate fecha;

    // Asociaciones
    @NotBlank(message = "El gasto debe tener un grupo.")
    private String idGrupo;
    @NotBlank(message = "El gasto debe ser cubierto por un usuario.")
    private String idCubiertoPor;
    @NotEmpty(message = "El gasto debe beneficiar a al menos un usuario.")
    private List<String> idBeneficiados;
    private String idCategoria;
}
