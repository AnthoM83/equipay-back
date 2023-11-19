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
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PagoService {

    // Dependencias
    @Autowired
    BalanceService balanceService;
    @Autowired
    IPagoRepository pagoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    PagoMapper mapper;
    @Autowired
    NotificationService notificationService;

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
    
    public List<PagoDto> listarPagosDeUsuarioEnGrupo(String usuarioId, Integer grupoId) {
        List<PagoDto> pagosRecibidos = this.listarPagosRecibidosPorUsuarioEnGrupo(usuarioId, grupoId);
        List<PagoDto> pagosRealizados = this.listarPagosRealizadosPorUsuarioEnGrupo(usuarioId, grupoId);
        pagosRecibidos.addAll(pagosRealizados);
        Collections.sort(pagosRecibidos, Comparator.comparing(PagoDto::getId));
        return pagosRecibidos; // + realizados
    }

    @Transactional
    public void crearPago(PagoAddDto dto) {
        Pago pago = mapper.toEntity(dto);
        pago.setMonto(Precision.round(dto.getMonto(), 2));
        pago.setGrupo(grupoRepo.findById(dto.getIdGrupo()).orElseThrow());
        pago.setRealiza(usuarioRepo.findById(dto.getIdRealiza()).orElseThrow());
        pago.setRecibe(usuarioRepo.findById(dto.getIdRecibe()).orElseThrow());
        pagoRepo.save(pago);
        balanceService.reajustarBalancePorPago(dto);

        Usuario destinatario = usuarioRepo.findById(dto.getIdRecibe()).orElseThrow();
        Usuario remitente = usuarioRepo.findById(dto.getIdRealiza()).orElseThrow();
        if(destinatario.getExpoPushToken() != null)
            notificationService.sendNotification(remitente.getNombre(), destinatario.getCorreo(), destinatario.getExpoPushToken(), "Equipay", "%s %s te ha hecho un pago por %s %s".formatted(remitente.getNombre(), remitente.getApellido(), dto.getMoneda(), dto.getMonto()));
    }

    @Transactional
    public void eliminarPago(Integer id) {
        if (pagoRepo.existsById(id)) {
            pagoRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }
}
