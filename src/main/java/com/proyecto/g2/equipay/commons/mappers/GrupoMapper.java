package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDtoFull;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.models.Grupo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface GrupoMapper {

    Grupo toEntity(GrupoAddDto dto);

    Grupo toEntity(GrupoUpdateDto dto);

    @Mapping(source = "grupo.dueño.correo", target = "idDueño")
    GrupoDto toGrupoDto(Grupo grupo);

    List<GrupoDto> toGrupoDtoList(List<Grupo> grupos);

    @Mapping(source = "grupo.dueño.correo", target = "idDueño")
    @Mapping(source = "grupo.miembros", target = "idMiembros")
    GrupoDtoFull toGrupoDtoFull(Grupo grupo);

    List<GrupoDtoFull> toGrupoDtoFullList(List<Grupo> grupos);

}
