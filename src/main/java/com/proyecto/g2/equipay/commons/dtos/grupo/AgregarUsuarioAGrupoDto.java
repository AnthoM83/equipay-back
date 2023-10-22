package com.proyecto.g2.equipay.commons.dtos.grupo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgregarUsuarioAGrupoDto {

    @NotBlank
    String idUsuario;
}
