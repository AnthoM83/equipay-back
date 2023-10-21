package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoUpdateDto;
import com.proyecto.g2.equipay.models.Gasto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GastoMapper {

    Gasto toEntity(GastoAddDto dto);

    Gasto toEntity(GastoUpdateDto dto);

    GastoDto toGastoDto(Gasto gasto);

    List<GastoDto> toGastoDtoList(List<Gasto> gastos);

//    default Grupo stringGrupoToGrupo(String grupo) {
//        return null; // Esto se hace en el servicio. Este método sólo existe para que el compilador no tire errores.
//    }
//
//    default Usuario stringCubiertoPorToCubiertoPor(String cubiertoPor) {
//        return null; // Esto se hace en el servicio. Este método sólo existe para que el compilador no tire errores.
//    }
//
//    default String categoriaToCategoriaString(Categoria categoria) {
//        return categoria.getId().toString();
//    }
//
//    default Categoria stringCategoriaToCategoria(String categoria) {
//        return null; // Esto se hace en el servicio. Este método sólo existe para que el compilador no tire errores.
//    }
//
//    default String grupoToStringGrupo(Grupo grupo) {
//        return grupo.getId().toString();
//    }
//
//    default String cubiertoPorToStringCubiertoPor(Usuario cubiertoPor) {
//        return cubiertoPor.getCorreo();
//    }
//
//    default List<String> miembrosToStringBeneficiados(List<Usuario> beneficiados) {
//        List<String> strings = new ArrayList<>();
//        beneficiados.forEach(beneficiado -> {
//            strings.add(beneficiado.getCorreo());
//        });
//        return strings;
//    }
}
