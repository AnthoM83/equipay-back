package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.balance.BalanceDto;
import com.proyecto.g2.equipay.models.Balance;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    @Mapping(source = "balance.usuario.correo", target = "idUsuario")
    @Mapping(source = "balance.grupo.id", target = "idGrupo")
    BalanceDto toBalanceDto(Balance balance);

    List<BalanceDto> toBalanceDtoList(List<Balance> balances);

}
