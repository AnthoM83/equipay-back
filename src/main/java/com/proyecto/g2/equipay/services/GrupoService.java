package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDtoFull;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.mappers.GrupoMapper;
import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.proyecto.g2.equipay.commons.specifications.GrupoSpecifications.hasUsuario;
import static com.proyecto.g2.equipay.commons.specifications.UsuarioSpecifications.hasGrupo;


@Service
public class GrupoService {

    // Dependencias
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    GrupoMapper mapper;
    @Autowired
    UsuarioMapper usuarioMapper;
    @Autowired
    EmailService emailService;

    // Métodos
    public GrupoDtoFull buscarGrupo(Integer id) {
        Grupo grupo = grupoRepo.findById(id).orElseThrow();
        return mapper.toGrupoDtoFull(grupo);
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
        Specification<Grupo> specification = hasUsuario(usuarioId);
        List<Grupo> gruposMiembro = grupoRepo.findAll(specification);
        grupos.addAll(gruposMiembro);
        Collections.sort(grupos, Comparator.comparing(Grupo::getId));
        return mapper.toGrupoDtoList(grupos);
    }
    
    public List<UsuarioDto> listarMiembrosEnGrupo(Integer id) {
        Specification<Usuario> specification = hasGrupo(id);
        List<Usuario> usuariosEnGrupo = usuarioRepo.findAll(specification);
        return usuarioMapper.toUsuarioDtoList(usuariosEnGrupo);
    }
    
    public List<UsuarioDto> listarUsuariosEnGrupo(Integer id) {
        Specification<Usuario> specification = hasGrupo(id);
        List<Usuario> usuariosEnGrupo = usuarioRepo.findAll(specification);
        Grupo grupo = grupoRepo.findById(id).orElseThrow();
        Usuario dueño = grupo.getDueño();
        usuariosEnGrupo.add(dueño);
        return usuarioMapper.toUsuarioDtoList(usuariosEnGrupo);
    }

    @Transactional
    public Integer crearGrupo(GrupoAddDto dto) {
        Grupo grupo = mapper.toEntity(dto);
        grupo.setDueño(usuarioRepo.findById(dto.getIdDueño()).orElseThrow());
        grupo.setFechaCreacion(LocalDate.now());
        grupo.setCodigo(generarCodigoUnico());
        Grupo grupoSave = grupoRepo.save(grupo);
        return grupoSave.getId();
    }

    @Transactional
    public void modificarGrupo(Integer id, GrupoUpdateDto dto) {
        Grupo grupo = grupoRepo.findById(id).orElseThrow();
        grupo.setNombre(dto.getNombre());
        grupo.setDescripcion(dto.getDescripcion());
        grupoRepo.save(grupo);
    }

    @Transactional
    public void eliminarGrupo(Integer id) {
        if (grupoRepo.existsById(id)) {
            grupoRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void agregarUsuarioAGrupo(Integer idGrupo, String idUsuario) {
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow();
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        Specification<Grupo> specification = hasUsuario(idUsuario);
        List<Grupo> gruposMiembro = grupoRepo.findAll(specification);
        if (grupo.getDueño().equals(usuario) || gruposMiembro.contains(grupo)) {
            throw new IllegalArgumentException();
        }
        grupo.getMiembros().add(usuario);
        grupoRepo.save(grupo);
    }

    @Transactional
    public void agregarUsuarioAGrupo(String codigo, String idUsuario) {
        Grupo grupo = buscarGrupoPorCodigo(codigo);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        if (grupo != null && !usuario.getMiembroDe().contains(grupo)) {
            grupo.getMiembros().add(usuario);
            grupoRepo.save(grupo);
        }
        else if (grupo != null && usuario.getMiembroDe().contains(grupo)) {
            throw new NoSuchElementException("El usuario ya se ha unido al grupo");
        }
        else{
            throw new NoSuchElementException("El codigo de grupo ingresado no existe");
        }

    }

    public void invitarAmigo(Integer idGrupo, String idUsuario){
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow();
        String link = "http://localhost:3000/unirse-grupo-link?groupId=" + idGrupo + "&?userId=" + idUsuario;
        String mensaje = "Te invitaron a unirte al grupo " + grupo.getNombre()
                + "\n Puedes unirte en el link: " + link + " o ingrasando el codigo: " + grupo.getCodigo();
        emailService.enviarCorreo(idUsuario, "Te invitaron a unirte a un grupo", mensaje);
    }

    private String generarCodigoUnico() {
        return (java.util.UUID.randomUUID().toString()).substring(0,8);
    }

    private Grupo buscarGrupoPorCodigo(String codigo) {
         Optional<Grupo> optionalGrupo = grupoRepo.findByCodigo(codigo);
         return optionalGrupo.orElse(null);
    }


}
