package com.proyecto.g2.equipay.commons.dtos.grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitarAmigosDto {
   Integer idGrupo;
   List<String> idUsuarios;
}
