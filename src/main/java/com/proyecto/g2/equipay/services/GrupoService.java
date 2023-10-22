package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.commons.mappers.GrupoMapper;
import com.proyecto.g2.equipay.models.Balance;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {

    // Dependencias
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    GrupoMapper mapper;

    // Métodos
    public GrupoDto buscarGrupo(Integer id) {
        Grupo grupo = grupoRepo.findById(id).orElseThrow();
        return mapper.toGrupoDto(grupo);
    }

    public List<GrupoDto> listarGrupos() {
        List<Grupo> grupos = grupoRepo.findAll();
        return mapper.toGrupoDtoList(grupos);
    }

    public List<GrupoDto> listarGrupos(String usuarioId) {
        Usuario dueño = usuarioRepo.findById(usuarioId).orElseThrow();
        Grupo grupo = Grupo.builder()
                .dueño(dueño)
                .build();
        Example example = Example.of(grupo);
        List<Grupo> grupos = grupoRepo.findAll(example);
        return mapper.toGrupoDtoList(grupos);
    }

    public void crearGrupo(GrupoAddDto dto) {
        Grupo grupo = mapper.toEntity(dto);
        grupo.setDueño(usuarioRepo.findById(dto.getIdDueño()).orElseThrow());
        grupoRepo.save(grupo);
    }

    public void modificarGrupo(Integer id, GrupoUpdateDto dto) {
        if (grupoRepo.existsById(id)) {
            Grupo grupoModificado = mapper.toEntity(dto);
            grupoRepo.save(grupoModificado);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void eliminarGrupo(Integer id) {
        if (grupoRepo.existsById(id)) {
            grupoRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void agregarUsuarioAGrupo(Integer idGrupo, String idUsuario) {
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow();
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        grupo.getMiembros().add(usuario);
        grupoRepo.save(grupo);
    }

}
