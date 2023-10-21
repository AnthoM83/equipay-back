package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrupoMapper {

    Grupo toEntity(GrupoAddDto dto);

    Grupo toEntity(GrupoUpdateDto dto);

    GrupoDto toGrupoDto(Grupo grupo);

    List<GrupoDto> toGrupoDtoList(List<Grupo> grupos);

//    default List<String> miembrosToStringMiembros(List<Usuario> miembros) {
//        List<String> strings = new ArrayList<>();
//        miembros.forEach(miembro -> {
//            strings.add(miembro.getCorreo());
//        });
//        return strings;
//    }
//    default String dueñoToStringDueño(Usuario dueño) {
//        return dueño.getCorreo();
//    }
//
//    default Usuario stringDueñoToDueño(String dueño) {
//        return null; // Esto se hace en el servicio. Este método sólo existe para que el compilador no tire errores.
//    }
}
