package com.proyecto.g2.equipay.commons.dtos.pago;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDto {

    // Propiedades
    private Integer id;
    private Double monto;
    private String moneda;
    private LocalDate fecha;

    // Asociaciones
    private Integer idGrupo;
    private UsuarioDto realiza;
    private UsuarioDto recibe;
}
