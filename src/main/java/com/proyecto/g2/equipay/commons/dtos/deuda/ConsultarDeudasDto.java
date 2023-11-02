package com.proyecto.g2.equipay.commons.dtos.deuda;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarDeudasDto {

    private UsuarioDto usuario;
    private Integer idGrupo;
    private List<DeudaDto> deudas;
}
