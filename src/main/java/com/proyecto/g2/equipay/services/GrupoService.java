package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDtoFull;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoUpdateDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.mappers.GrupoMapper;
import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import static com.proyecto.g2.equipay.commons.specifications.UsuarioSpecifications.hasGrupo;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import javax.swing.text.html.Option;

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
    public void crearGrupo(GrupoAddDto dto) {
        Grupo grupo = mapper.toEntity(dto);
        grupo.setDueño(usuarioRepo.findById(dto.getIdDueño()).orElseThrow());
        grupo.setFechaCreacion(LocalDate.now());
        grupo.setCodigo(generarCodigoUnico());
        grupoRepo.save(grupo);
    }

    @Transactional
    public void modificarGrupo(Integer id, GrupoUpdateDto dto) {
        if (grupoRepo.existsById(id)) {
            Grupo grupoModificado = mapper.toEntity(dto);
            grupoRepo.save(grupoModificado);
        } else {
            throw new NoSuchElementException();
        }
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
        grupo.getMiembros().add(usuario);
        grupoRepo.save(grupo);
    }

    @Transactional
    public void agregarUsuarioAGrupo(String codigo, String idUsuario) {
        Grupo grupo = buscarGrupoPorCodigo(codigo);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        if (grupo != null) {
            grupo.getMiembros().add(usuario);
            grupoRepo.save(grupo);
        }

    }

    public void invitarAmigo(Integer idGrupo, String idUsuario){
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow();
        String link = "http://localhost:3000/invitar-amigo?groupId" + idGrupo + "^?userId=" + idUsuario;
        String mensaje = "Te invitaron a unirte al grupo " + grupo.getNombre()
                + "Puedes unirte en el link: " + link + "o ingrasando el codigo: " + grupo.getCodigo();
        emailService.enviarCorreo(idUsuario, "Te invitaron a unirte a un grupo", mensaje);
    }

    private String generarCodigoUnico() {
        return java.util.UUID.randomUUID().toString();
    }

    private Grupo buscarGrupoPorCodigo(String codigo) {
         Optional<Grupo> optionalGrupo = grupoRepo.findByCodigo(codigo);
         return optionalGrupo.orElse(null);
    }


}
