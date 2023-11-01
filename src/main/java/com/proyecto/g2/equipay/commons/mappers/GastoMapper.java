package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoUpdateDto;
import com.proyecto.g2.equipay.models.Gasto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface GastoMapper {

    Gasto toEntity(GastoAddDto dto);

    Gasto toEntity(GastoUpdateDto dto);

    @Mapping(source = "gasto.grupo.id", target = "idGrupo")
    @Mapping(source = "gasto.cubiertoPor.correo", target = "cubiertoPor.correo")
    @Mapping(source = "gasto.cubiertoPor.nombre", target = "cubiertoPor.nombre")
    @Mapping(source = "gasto.cubiertoPor.apellido", target = "cubiertoPor.apellido")
    @Mapping(source = "gasto.beneficiados", target = "beneficiados")
    @Mapping(source = "gasto.categoria.id", target = "idCategoria")
    GastoDto toGastoDto(Gasto gasto);

    List<GastoDto> toGastoDtoList(List<Gasto> gastos);

}
