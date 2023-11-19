package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.estadistica.ValorTotal;
import com.proyecto.g2.equipay.commons.dtos.estadistica.ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses;
import com.proyecto.g2.equipay.commons.dtos.estadistica.ValorTotalPagosRealizadosPorUsuarioEnGrupos;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoDto;
import com.proyecto.g2.equipay.models.Gasto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Pago;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGastoRepository;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IPagoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ResumenService {

// Dependencias
    @Autowired
    GrupoService grupoService;
    @Autowired
    PagoService pagoService;
    @Autowired
    GastoService gastoService;
    @Autowired
    IPagoRepository pagoRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    IGastoRepository gastoRepo;

    // Métodos
    public Integer cantidadDeGastosCubiertosPorUsuario(String idUsuario) {
        Usuario cubiertoPor = usuarioRepo.findById(idUsuario).orElseThrow();
        Gasto gasto = Gasto.builder()
                .cubiertoPor(cubiertoPor)
                .build();
        Example example = Example.of(gasto);
        return gastoRepo.findAll(example).size();
    }

    public List<ValorTotal> valorTotalDeGastosCubiertosPorUsuario(String idUsuario) {
        List<GastoDto> gastos = gastoService.listarGastosCubiertosPorUsuario(idUsuario);
        Map<String, Double> monedas = new HashMap<>();
        List<ValorTotal> retorno = new ArrayList<>();
        for (var gasto : gastos) {
            String moneda = gasto.getMoneda();
            if (monedas.containsKey(moneda)) {
                Double current = monedas.get(moneda);
                monedas.replace(moneda, current + gasto.getMonto());
            } else {
                monedas.putIfAbsent(moneda, gasto.getMonto());
            }
        }
        monedas.forEach((moneda, total) -> {
            retorno.add(new ValorTotal(moneda, Precision.round(total, 2)));
        });
        return retorno;
    }

    public Integer cantidadDeGastosEnGrupo(Integer idGrupo) {
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow();
        Gasto gasto = Gasto.builder()
                .grupo(grupo)
                .build();
        Example example = Example.of(gasto);
        return gastoRepo.findAll(example).size();
    }

    public List<ValorTotal> valorTotalDeGastosEnGrupo(Integer idGrupo) {
        List<GastoDto> gastos = gastoService.listarGastosEnGrupo(idGrupo);
        Map<String, Double> monedas = new HashMap<>();
        List<ValorTotal> retorno = new ArrayList<>();
        for (var gasto : gastos) {
            String moneda = gasto.getMoneda();
            if (monedas.containsKey(moneda)) {
                Double current = monedas.get(moneda);
                monedas.replace(moneda, current + gasto.getMonto());
            } else {
                monedas.putIfAbsent(moneda, gasto.getMonto());
            }
        }
        monedas.forEach((moneda, total) -> {
            retorno.add(new ValorTotal(moneda, Precision.round(total, 2)));
        });
        return retorno;
    }

    public Integer cantidadDePagosRealizadosPorUsuario(String usuarioId) {
        Usuario realiza = usuarioRepo.findById(usuarioId).orElseThrow();
        Pago pago = Pago.builder()
                .realiza(realiza)
                .build();
        Example example = Example.of(pago);
        return pagoRepo.findAll(example).size();
    }

    public List<ValorTotal> valorTotalDePagosRealizadosPorUsuario(String usuarioId) {
        List<PagoDto> pagos = pagoService.listarPagosRealizadosPorUsuario(usuarioId);
        Map<String, Double> monedas = new HashMap<>();
        List<ValorTotal> retorno = new ArrayList<>();
        for (var pago : pagos) {
            String moneda = pago.getMoneda();
            if (monedas.containsKey(moneda)) {
                Double current = monedas.get(moneda);
                monedas.replace(moneda, current + pago.getMonto());
            } else {
                monedas.putIfAbsent(moneda, pago.getMonto());
            }
        }
        monedas.forEach((moneda, total) -> {
            retorno.add(new ValorTotal(moneda, Precision.round(total, 2)));
        });
        return retorno;
    }

    public Integer cantidadDePagosEnGrupo(Integer grupoId) {
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Pago pago = Pago.builder()
                .grupo(grupo)
                .build();
        Example example = Example.of(pago);
        return pagoRepo.findAll(example).size();
    }

    public List<ValorTotal> valorTotalDePagosEnGrupo(Integer grupoId) {
        List<PagoDto> pagos = pagoService.listarPagosEnGrupo(grupoId);
        Map<String, Double> monedas = new HashMap<>();
        List<ValorTotal> retorno = new ArrayList<>();
        for (var pago : pagos) {
            String moneda = pago.getMoneda();
            if (monedas.containsKey(moneda)) {
                Double current = monedas.get(moneda);
                monedas.replace(moneda, current + pago.getMonto());
            } else {
                monedas.putIfAbsent(moneda, pago.getMonto());
            }
        }
        monedas.forEach((moneda, total) -> {
            retorno.add(new ValorTotal(moneda, Precision.round(total, 2)));
        });
        return retorno;
    }

    public List<ValorTotalPagosRealizadosPorUsuarioEnGrupos> valorTotalDePagosRealizadosPorUsuarioEnGrupos(String usuarioId, String moneda) {
        List<ValorTotalPagosRealizadosPorUsuarioEnGrupos> total = new ArrayList<>();
        List<GrupoDto> grupos = grupoService.listarGrupos(usuarioId);
        for (var grupo : grupos) {
            List<PagoDto> pagos = pagoService.listarPagosRealizadosPorUsuarioEnGrupo(usuarioId, grupo.getId());
            pagos.removeIf(filter -> !filter.getMoneda().contains(moneda));
            Double valorEnGrupos = 0.0;
            for (var pago : pagos) {
                valorEnGrupos += pago.getMonto();
            }
            total.add(new ValorTotalPagosRealizadosPorUsuarioEnGrupos(grupo.getId(), grupo.getNombre(), Precision.round(valorEnGrupos, 2)));
        }
        return total;
    }
    
    public List<ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses> valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses(String usuarioId, String moneda) {
        List<ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses> total = new ArrayList<>();
        List<YearMonth> ultimosDoceMeses = new ArrayList<>();
        YearMonth ahora = YearMonth.now();
        for(int i = 1; i <= 12; i++) {
            ultimosDoceMeses.add(ahora);
            ahora = ahora.minusMonths(1);
        }
        for (var mes : ultimosDoceMeses) {
            ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses valorMes = new ValorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses(mes, 0.0);
            Specification<Gasto> specGastos = com.proyecto.g2.equipay.commons.specifications.GastoSpecifications.onMonthParaUsuario(mes, usuarioId);
            List<Gasto> gastos = gastoRepo.findAll(specGastos);
            Double valorEnMes = 0.0;
            gastos.removeIf(filter -> !filter.getMoneda().contains(moneda));
            for (var gasto : gastos) {
                valorEnMes += gasto.getMonto();
            }
            valorMes.setValor(Precision.round(valorEnMes, 2));
            total.add(valorMes);
        }
        return total;
    }

}
