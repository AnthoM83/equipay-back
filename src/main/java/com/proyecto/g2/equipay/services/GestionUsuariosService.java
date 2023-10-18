package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.RegistrarUsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.UsuarioDto;
import com.proyecto.g2.equipay.models.EstadoUsuario;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionUsuariosService
        implements IGestionUsuariosService {

    @Autowired
    IUsuarioRepository repo;

    public void crearUsuario(RegistrarUsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.getCorreo());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setPassword(dto.getPassword());
        usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
        repo.save(usuario);
    }

    public List<UsuarioDto> listarUsuarios() {
        List<UsuarioDto> usuariosDto = new ArrayList<>();
        repo.findAll().forEach(usuario -> {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setCorreo(usuario.getCorreo());
            usuarioDto.setNombre(usuario.getNombre());
            usuarioDto.setApellido(usuario.getApellido());
            usuarioDto.setEstadoUsuario(usuario.getEstadoUsuario().toString());
            usuariosDto.add(usuarioDto);
        });
        return usuariosDto;
    }
}
