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
    @Mapping(source = "pago.realiza.correo", target = "idRealiza")
    @Mapping(source = "pago.recibe.correo", target = "idRecibe")
    PagoDto toPagoDto(Pago pago);

    List<PagoDto> toPagoDtoList(List<Pago> pagos);

}
