package com.proyecto.g2.equipay.commons.mappers;

import com.proyecto.g2.equipay.commons.dtos.admin.AdminAddDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminFullDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminUpdateDto;
import com.proyecto.g2.equipay.models.Admin;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin toEntity(AdminAddDto dto);

    Admin toEntity(AdminUpdateDto dto);

    AdminDto toAdminDto(Admin admin);

    List<AdminDto> toAdminDtoList(List<Admin> admins);

    AdminFullDto toAdminFullDto(Admin admin);

    List<AdminFullDto> toAdminFullDtoList(List<Admin> admins);
}
