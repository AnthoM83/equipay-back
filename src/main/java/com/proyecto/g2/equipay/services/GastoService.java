package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoUpdateDto;
import com.proyecto.g2.equipay.commons.mappers.GastoMapper;
import com.proyecto.g2.equipay.commons.specifications.GastoSpecifications;
import static com.proyecto.g2.equipay.commons.specifications.GastoSpecifications.hasUsuarioEnGrupo;
import com.proyecto.g2.equipay.models.Gasto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IGastoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.proyecto.g2.equipay.commons.specifications.GastoSpecifications.hasUsuarioComoBeneficiado;
import com.proyecto.g2.equipay.repositories.ICategoriaRepository;
import java.util.ArrayList;

@Service
public class GastoService {

    // Dependencias
    @Autowired
    BalanceService balanceService;
    @Autowired
    IGastoRepository gastoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    ICategoriaRepository categoriaRepo;
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

    public List<GastoDto> listarGastosDeLosQueUsuarioSeBeneficia(String usuarioId) {
        Specification<Gasto> specification = hasUsuarioComoBeneficiado(usuarioId);
        List<Gasto> beneficiadoEn = gastoRepo.findAll(specification);
        return mapper.toGastoDtoList(beneficiadoEn);
    }

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

    public List<GastoDto> listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo(String usuarioId, Integer grupoId) {
        Specification<Gasto> specification = hasUsuarioComoBeneficiado(usuarioId);
        List<Gasto> beneficiadoEn = gastoRepo.findAll(specification);
        beneficiadoEn.removeIf(filter -> filter.getGrupo().getId().equals(grupoId));
        return mapper.toGastoDtoList(beneficiadoEn);
    }
    
        public List<GastoDto> listarGastosDeUsuarioEnGrupo(String usuarioId, Integer grupoId) {
        List<GastoDto> gastosCubiertos = this.listarGastosCubiertosPorUsuarioEnGrupo(usuarioId, grupoId);
        List<GastoDto> gastosBeneficiados = this.listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo(usuarioId, grupoId);
        gastosCubiertos.addAll(gastosBeneficiados);
        Collections.sort(gastosCubiertos, Comparator.comparing(GastoDto::getId));
        return gastosCubiertos; // + beneficiados
    }

    @Transactional
    public void crearGasto(GastoAddDto dto) {
        Gasto gasto = mapper.toEntity(dto);
        gasto.setMonto(Precision.round(dto.getMonto(), 2));
        gasto.setGrupo(grupoRepo.findById(dto.getIdGrupo()).orElseThrow());
        gasto.setCubiertoPor(usuarioRepo.findById(dto.getIdCubiertoPor()).orElseThrow());
        List<Usuario> beneficiados = new ArrayList<>();
        for (String idBeneficiado : dto.getIdBeneficiados()) {
            beneficiados.add(usuarioRepo.findById(idBeneficiado).orElseThrow());
        }
        gasto.setBeneficiados(beneficiados);
        gasto.setCategoria(categoriaRepo.findById(dto.getIdCategoria()).orElseThrow());
        gastoRepo.save(gasto);
        balanceService.reajustarBalancePorGasto(dto);
    }

    @Transactional
    public void modificarGasto(Integer id, GastoUpdateDto dto) {
        if (gastoRepo.existsById(id)) {
            Gasto gasto = gastoRepo.findById(id).orElseThrow();
            gasto.setDescripcion(dto.getDescripcion());
            gastoRepo.save(gasto);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void eliminarGasto(Integer id) {
        if (gastoRepo.existsById(id)) {
            gastoRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }
}
