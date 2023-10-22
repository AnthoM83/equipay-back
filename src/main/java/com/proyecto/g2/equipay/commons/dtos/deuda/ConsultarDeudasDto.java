package com.proyecto.g2.equipay.commons.dtos.deuda;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarDeudasDto {

    private String idUsuario;
    private Integer idGrupo;
    private List<DeudaDto> deudas;
}
