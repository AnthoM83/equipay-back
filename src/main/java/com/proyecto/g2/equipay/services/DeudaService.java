package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.balance.BalanceDto;
import com.proyecto.g2.equipay.commons.dtos.deuda.ConsultarDeudasDto;
import com.proyecto.g2.equipay.commons.dtos.deuda.DeudaDto;
import com.proyecto.g2.equipay.commons.dtos.deuda.Sugerencia;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeudaService {

    @Autowired
    BalanceService balanceService;

    public ConsultarDeudasDto consultarDeudasDeUsuarioEnGrupo(Integer idGrupo, String idUsuario) {
        ConsultarDeudasDto dto = new ConsultarDeudasDto();
        dto.setIdUsuario(idUsuario);
        dto.setIdGrupo(idGrupo);

        List<BalanceDto> deudasConsultado = balanceService.listarBalancesDeUsuarioEnGrupo(idUsuario, idGrupo);
        List<BalanceDto> aFavorOtros = balanceService.listarBalancesDeOtrosEnGrupo(idUsuario, idGrupo);

        deudasConsultado.removeIf(filter -> (Double.compare(filter.getDeuda(), 0) < 0));
        if (deudasConsultado.isEmpty()) {
            return dto; // No tiene deudas
        }

        aFavorOtros.removeIf(filter -> (Double.compare(filter.getDeuda(), 0) > 0));

        Comparator mayorAMenor = Comparator.comparingDouble(BalanceDto::getDeuda);
        aFavorOtros.sort(mayorAMenor);

        List<DeudaDto> deudas = new ArrayList<>();
        deudasConsultado.forEach(deudaConsultado -> {
            String moneda = deudaConsultado.getMoneda();
            DeudaDto deuda = new DeudaDto();
            deuda.setMoneda(moneda);
            deuda.setSugerencias(new ArrayList<>());
            Double restanteAPagar = deudaConsultado.getDeuda();
            deuda.setDeudaEnGrupo(Precision.round(restanteAPagar, 2));
            for (BalanceDto otro : aFavorOtros) {
                Double pagoEnEstaIteracion;
                if (otro.getMoneda().equals(moneda) && restanteAPagar != 0.0) {
                    Double otroAFavorAbs = Math.abs(otro.getDeuda());
                    if (Double.compare(restanteAPagar, otroAFavorAbs) > 0) {
                        pagoEnEstaIteracion = otroAFavorAbs;
                        restanteAPagar -= otroAFavorAbs;
                    } else {
                        pagoEnEstaIteracion = restanteAPagar;
                        restanteAPagar = 0.0;
                    }
                    Sugerencia sugerencia = new Sugerencia(otro.getIdUsuario(), pagoEnEstaIteracion);
                    deuda.getSugerencias().add(sugerencia);
                }
            }
            deudas.add(deuda);
        });
        dto.setDeudas(deudas);
        return dto;
    }

}
