package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoUpdateDto;
import com.proyecto.g2.equipay.commons.mappers.GastoMapper;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Gasto;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IGastoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class GastoService {

    // Dependencias
    @Autowired
    IGastoRepository gastoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    GastoMapper mapper;

    // Métodos
    public GastoDto buscarGasto(Integer id) {
        Gasto gasto = gastoRepo.findById(id).orElseThrow();
        return mapper.toGastoDto(gasto);
    }

    public List<GastoDto> listarGastos() {
        List<Gasto> gastos = gastoRepo.findAll();
        return mapper.toGastoDtoList(gastos);
    }

    public List<GastoDto> listarGastosCubiertosPorUsuario(String usuarioId) {
        Usuario cubiertoPor = usuarioRepo.findById(usuarioId).orElseThrow();
        Gasto gasto = Gasto.builder()
                .cubiertoPor(cubiertoPor)
                .build();
        Example example = Example.of(gasto);
        List<Gasto> gastos = gastoRepo.findAll(example);
        return mapper.toGastoDtoList(gastos);
    }

    
//    public List<GastoDto> listarGastosDeLosQueUsuarioSeBeneficia(String usuarioId) {
//        Usuario beneficiado = usuarioRepo.findById(usuarioId).orElseThrow();
//        Gasto gasto = Gasto.builder()
//                .build();
//        Example example = Example.of(gasto);
//        List<Gasto> gastos = gastoRepo.findAll(example);
//        return mapper.toGastoDtoList(gastos);
//    }

    public List<GastoDto> listarGastosEnGrupo(Integer grupoId) {
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Gasto gasto = Gasto.builder()
                .grupo(grupo)
                .build();
        Example example = Example.of(gasto);
        List<Gasto> gastos = gastoRepo.findAll(example);
        return mapper.toGastoDtoList(gastos);
    }

    public List<GastoDto> listarGastosCubiertosPorUsuarioEnGrupo(String usuarioId, Integer grupoId) {
        Usuario cubiertoPor = usuarioRepo.findById(usuarioId).orElseThrow();
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Gasto gasto = Gasto.builder()
                .grupo(grupo)
                .cubiertoPor(cubiertoPor)
                .build();
        Example example = Example.of(gasto);
        List<Gasto> gastos = gastoRepo.findAll(example);
        return mapper.toGastoDtoList(gastos);
    }

//    public List<GastoDto> listarGastosRecibidosPorUsuarioEnGrupo(String usuarioId, Integer grupoId) {
//        Usuario beneficiado = usuarioRepo.findById(usuarioId).orElseThrow();
//        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
//        Gasto gasto = Gasto.builder()
//                .grupo(grupo)
//                .build();
//        Example example = Example.of(gasto);
//        List<Gasto> gastos = gastoRepo.findAll(example);
//        return mapper.toGastoDtoList(gastos);
//    }

    public void crearGasto(GastoAddDto dto) {
        Gasto gasto = mapper.toEntity(dto);
        gastoRepo.save(gasto);
    }

    public void modificarGasto(Integer id, GastoUpdateDto dto) {
        if (gastoRepo.existsById(id)) {
            Gasto gastoModificado = mapper.toEntity(dto);
            gastoRepo.save(gastoModificado);
        } else {
            throw new NoSuchElementException();
        }
    }
    
    public void eliminarGasto(Integer id) {
        if (gastoRepo.existsById(id)) {
            gastoRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }
}
