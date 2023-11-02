package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.pago.PagoAddDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoDto;
import com.proyecto.g2.equipay.models.Pago;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    Pago toEntity(PagoAddDto dto);

    @Mapping(source = "pago.grupo.id", target = "idGrupo")
    @Mapping(source = "pago.realiza.correo", target = "realiza.correo")
    @Mapping(source = "pago.realiza.nombre", target = "realiza.nombre")
    @Mapping(source = "pago.realiza.apellido", target = "realiza.apellido")
    @Mapping(source = "pago.recibe.correo", target = "recibe.correo")
    @Mapping(source = "pago.recibe.nombre", target = "recibe.nombre")
    @Mapping(source = "pago.recibe.apellido", target = "recibe.apellido")
    PagoDto toPagoDto(Pago pago);

    List<PagoDto> toPagoDtoList(List<Pago> pagos);

}
