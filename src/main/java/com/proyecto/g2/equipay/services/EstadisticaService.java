package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.estadistica.ValorCount;
import com.proyecto.g2.equipay.commons.dtos.estadistica.ValorTotal;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.commons.mappers.GrupoMapper;
import com.proyecto.g2.equipay.models.Gasto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Pago;
import com.proyecto.g2.equipay.repositories.IGastoRepository;
import com.proyecto.g2.equipay.repositories.IPagoRepository;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EstadisticaService {

    @Autowired
    IGastoRepository gastoRepo;
    @Autowired
    IPagoRepository pagoRepo;
    @Autowired
    GrupoMapper grupoMapper;

    public List<ValorTotal> promedioDeGastos() {
        List<Gasto> gastos = gastoRepo.findAll();
        Map<String, ValorCount> monedas = new HashMap<>();
        List<ValorTotal> retorno = new ArrayList<>();
        for (var gasto : gastos) {
            String moneda = gasto.getMoneda();
            if (monedas.containsKey(moneda)) {
                ValorCount current = monedas.get(moneda);
                monedas.replace(moneda, new ValorCount(current.getValor() + gasto.getMonto(), current.getCount() + 1));
            } else {
                monedas.putIfAbsent(moneda, new ValorCount(gasto.getMonto(), 1));
            }
        }
        monedas.forEach((moneda, total) -> {
            Double toAdd = total.getValor() / total.getCount();
            retorno.add(new ValorTotal(moneda, Precision.round(toAdd, 2)));
        });
        return retorno;
    }

    public List<GrupoDto> gruposConActividadEnMes(YearMonth yearMonth) {
        List<Grupo> grupos = new ArrayList<>();
        Specification<Gasto> specGastos = com.proyecto.g2.equipay.commons.specifications.GastoSpecifications.onMonth(yearMonth);
        List<Gasto> gastos = gastoRepo.findAll(specGastos);
        for (var gasto : gastos) {
            if (!grupos.contains(gasto.getGrupo())) {
                grupos.add(gasto.getGrupo());
            }
        }

        Specification<Pago> specPagos = com.proyecto.g2.equipay.commons.specifications.PagoSpecifications.onMonth(yearMonth);
        List<Pago> pagos = pagoRepo.findAll(specPagos);
        for (var pago : pagos) {
            if (!grupos.contains(pago.getGrupo())) {
                grupos.add(pago.getGrupo());
            }
        }
        return grupoMapper.toGrupoDtoList(grupos);
    }

}
