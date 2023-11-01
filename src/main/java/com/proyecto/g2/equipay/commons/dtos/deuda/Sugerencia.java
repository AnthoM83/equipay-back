package com.proyecto.g2.equipay.commons.dtos.deuda;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sugerencia {

    private UsuarioDto usuario;
    private Double monto;
}
