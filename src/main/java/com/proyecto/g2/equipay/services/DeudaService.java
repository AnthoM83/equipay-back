package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.balance.BalanceDto;
import com.proyecto.g2.equipay.commons.dtos.deuda.ConsultarDeudasDto;
import com.proyecto.g2.equipay.commons.dtos.deuda.DeudaDto;
import com.proyecto.g2.equipay.commons.dtos.deuda.Sugerencia;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
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
    @Autowired
    IUsuarioRepository usuarioRepo;

    public ConsultarDeudasDto consultarDeudasDeUsuarioEnGrupo(Integer idGrupo, String idUsuario) {
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        ConsultarDeudasDto dto = new ConsultarDeudasDto();
        dto.setUsuario(new UsuarioDto(usuario.getCorreo(), usuario.getNombre(), usuario.getApellido()));
        dto.setIdGrupo(idGrupo);

        List<BalanceDto> deudasConsultado = balanceService.listarBalancesDeUsuarioEnGrupo(idUsuario, idGrupo);
        List<BalanceDto> aFavorOtros = balanceService.listarBalancesDeOtrosEnGrupo(idUsuario, idGrupo);
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
                Usuario otroUsuario = usuarioRepo.findById(otro.getIdUsuario()).orElseThrow();
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
                    Sugerencia sugerencia = new Sugerencia(new UsuarioDto(otroUsuario.getCorreo(), otroUsuario.getNombre(), otroUsuario.getApellido()), pagoEnEstaIteracion, otro.getMoneda());
                    deuda.getSugerencias().add(sugerencia);
                }
            }
            deudas.add(deuda);
        });
        dto.setDeudas(deudas);
        return dto;
    }

}
