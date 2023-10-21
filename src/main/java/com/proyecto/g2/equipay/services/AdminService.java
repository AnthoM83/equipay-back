package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.admin.AdminAddDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminUpdateDto;
import com.proyecto.g2.equipay.commons.mappers.AdminMapper;
import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.repositories.IAdminRepository;
import jakarta.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    // Dependencias
    @Autowired
    IAdminRepository repo;
    @Autowired
    AdminMapper mapper;

    // Métodos
    public AdminDto buscarAdmin(String id) {
        Admin admin = repo.findById(id).orElseThrow();
        return mapper.toAdminDto(admin);
    }

    public List<AdminDto> listarAdmins() {
        List<Admin> admins = repo.findAll();
        return mapper.toAdminDtoList(admins);
    }

    public void crearAdmin(AdminAddDto dto) {
        Optional<Admin> find = repo.findById(dto.getCorreo());
        if (find.isEmpty()) {
            Admin admin = mapper.toEntity(dto);
            repo.save(admin);
        } else {
            throw new EntityExistsException();
        }
    }

    public void modificarAdmin(String id, AdminUpdateDto dto) {
        if (repo.existsById(id)) {
            Admin adminModificado = mapper.toEntity(dto);
            repo.save(adminModificado);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void eliminarAdmin(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

}
