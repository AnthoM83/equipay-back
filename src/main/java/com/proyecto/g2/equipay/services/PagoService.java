package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.pago.PagoAddDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoDto;
import com.proyecto.g2.equipay.commons.mappers.PagoMapper;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Pago;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IPagoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    // Dependencias
    @Autowired
    IPagoRepository pagoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    PagoMapper mapper;

    // Métodos
    public PagoDto buscarPago(Integer id) {
        Pago pago = pagoRepo.findById(id).orElseThrow();
        return mapper.toPagoDto(pago);
    }

    public List<PagoDto> listarPagos() {
        List<Pago> pagos = pagoRepo.findAll();
        return mapper.toPagoDtoList(pagos);
    }

    public List<PagoDto> listarPagosRealizadosPorUsuario(String usuarioId) {
        Usuario realiza = usuarioRepo.findById(usuarioId).orElseThrow();
        Pago pago = Pago.builder()
                .realiza(realiza)
                .build();
        Example example = Example.of(pago);
        List<Pago> pagos = pagoRepo.findAll(example);
        return mapper.toPagoDtoList(pagos);
    }

    public List<PagoDto> listarPagosRecibidosPorUsuario(String usuarioId) {
        Usuario recibe = usuarioRepo.findById(usuarioId).orElseThrow();
        Pago pago = Pago.builder()
                .recibe(recibe)
                .build();
        Example example = Example.of(pago);
        List<Pago> pagos = pagoRepo.findAll(example);
        return mapper.toPagoDtoList(pagos);
    }

    public List<PagoDto> listarPagosEnGrupo(Integer grupoId) {
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Pago pago = Pago.builder()
                .grupo(grupo)
                .build();
        Example example = Example.of(pago);
        List<Pago> pagos = pagoRepo.findAll(example);
        return mapper.toPagoDtoList(pagos);
    }

    public List<PagoDto> listarPagosRealizadosPorUsuarioEnGrupo(String usuarioId, Integer grupoId) {
        Usuario realiza = usuarioRepo.findById(usuarioId).orElseThrow();
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Pago pago = Pago.builder()
                .grupo(grupo)
                .realiza(realiza)
                .build();
        Example example = Example.of(pago);
        List<Pago> pagos = pagoRepo.findAll(example);
        return mapper.toPagoDtoList(pagos);
    }

    public List<PagoDto> listarPagosRecibidosPorUsuarioEnGrupo(String usuarioId, Integer grupoId) {
        Usuario recibe = usuarioRepo.findById(usuarioId).orElseThrow();
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Pago pago = Pago.builder()
                .grupo(grupo)
                .realiza(recibe)
                .build();
        Example example = Example.of(pago);
        List<Pago> pagos = pagoRepo.findAll(example);
        return mapper.toPagoDtoList(pagos);
    }

    public void crearPago(PagoAddDto dto) {
        Pago pago = mapper.toEntity(dto);
        pagoRepo.save(pago);
    }

//    public void modificarPago(Integer id, PagoUpdateDto dto) {
//        if (pagoRepo.existsById(id)) {
//            Pago pagoModificado = mapper.toEntity(dto);
//            pagoRepo.save(pagoModificado);
//        } else {
//            throw new NoSuchElementException();
//        }
//    }
    public void eliminarPago(Integer id) {
        if (pagoRepo.existsById(id)) {
            pagoRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }
}
