package com.proyecto.g2.equipay.commons.dtos.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEditCategoriaDto {

    @NotBlank
    String nombre;
}
