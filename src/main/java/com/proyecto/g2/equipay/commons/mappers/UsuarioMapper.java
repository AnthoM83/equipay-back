package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioFullDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.models.Usuario;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioAddDto dto);

    Usuario toEntity(UsuarioUpdateDto dto);

    UsuarioDto toUsuarioDto(Usuario usuario);

    List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuarios);

    UsuarioFullDto toUsuarioFullDto(Usuario usuario);

    List<UsuarioFullDto> toUsuarioFullDtoList(List<Usuario> usuarios);
}
