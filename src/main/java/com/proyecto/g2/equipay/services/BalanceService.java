package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.balance.BalanceDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoAddDto;
import com.proyecto.g2.equipay.commons.mappers.BalanceMapper;
import com.proyecto.g2.equipay.models.Balance;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IBalanceRepository;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceService {

    // Dependencias
    @Autowired
    IBalanceRepository balanceRepo;
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IGrupoRepository grupoRepo;
    @Autowired
    BalanceMapper mapper;

    // Métodos
    public BalanceDto buscarBalance(Integer id) {
        Balance balance = balanceRepo.findById(id).orElseThrow();
        return mapper.toBalanceDto(balance);
    }

    public List<BalanceDto> listarBalances() {
        List<Balance> balances = balanceRepo.findAll();
        return mapper.toBalanceDtoList(balances);
    }

    public List<BalanceDto> listarBalancesDeUsuario(String usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow();
        Balance balance = Balance.builder()
                .usuario(usuario)
                .build();
        Example example = Example.of(balance);
        List<Balance> balances = balanceRepo.findAll(example);
        return mapper.toBalanceDtoList(balances);
    }

    public List<BalanceDto> listarBalancesEnGrupo(Integer grupoId) {
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Balance balance = Balance.builder()
                .grupo(grupo)
                .build();
        Example example = Example.of(balance);
        List<Balance> balances = balanceRepo.findAll(example);
        return mapper.toBalanceDtoList(balances);
    }

    public List<BalanceDto> listarBalancesDeUsuarioEnGrupo(String usuarioId, Integer grupoId) {
        Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow();
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Balance balance = Balance.builder()
                .usuario(usuario)
                .grupo(grupo)
                .build();
        Example example = Example.of(balance);
        List<Balance> balances = balanceRepo.findAll(example);
        return mapper.toBalanceDtoList(balances);
    }

    public List<BalanceDto> listarBalancesDeOtrosEnGrupo(String usuarioId, Integer grupoId) {
        Grupo grupo = grupoRepo.findById(grupoId).orElseThrow();
        Balance balance = Balance.builder()
                .grupo(grupo)
                .build();
        Example example = Example.of(balance);
        List<Balance> balances = balanceRepo.findAll(example);
        balances.removeIf(balanceFilter -> (balanceFilter.getUsuario().getCorreo().equals(usuarioId)));
        return mapper.toBalanceDtoList(balances);
    }

    @Transactional
    protected void crearBalance(Balance balance) {
        balanceRepo.save(balance);
    }

    @Transactional
    protected void eliminarBalance(Integer id) {
        if (balanceRepo.existsById(id)) {
            balanceRepo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    protected void reajustarBalancePorGasto(GastoAddDto dto) {
        Grupo grupo = grupoRepo.findById(dto.getIdGrupo()).orElseThrow();
        Usuario cubiertoPor = usuarioRepo.findById(dto.getIdCubiertoPor()).orElseThrow();
        List<Usuario> beneficiados = usuarioRepo.findAllById(dto.getIdBeneficiados());
        Double valorACadaBeneficiado = dto.getMonto() / (dto.getIdBeneficiados().size() + 1);

        Balance balanceCubiertoPor;
        List<Balance> optionalBalanceCubiertoPor = balanceRepo.findAll(
                Example.of(Balance.builder()
                        .grupo(grupo)
                        .usuario(cubiertoPor)
                        .moneda(dto.getMoneda())
                        .build()));
        if (optionalBalanceCubiertoPor.isEmpty()) {
            balanceCubiertoPor = Balance.builder()
                    .grupo(grupo)
                    .usuario(cubiertoPor)
                    .moneda(dto.getMoneda())
                    .deuda(0.0)
                    .build();
        } else {
            balanceCubiertoPor = optionalBalanceCubiertoPor.get(0);
        }
        Double deudaCubiertoPor = balanceCubiertoPor.getDeuda();
        deudaCubiertoPor -= dto.getMonto();
        balanceCubiertoPor.setDeuda(Precision.round(deudaCubiertoPor, 2));
        balanceRepo.save(balanceCubiertoPor);

        beneficiados.forEach(beneficiado -> {
            Balance balanceBeneficiado;
            List<Balance> optionalBalanceBeneficiado = balanceRepo.findAll(
                    Example.of(Balance.builder()
                            .grupo(grupo)
                            .usuario(beneficiado)
                            .moneda(dto.getMoneda())
                            .build()));
            if (optionalBalanceBeneficiado.isEmpty()) {
                balanceBeneficiado = Balance.builder()
                        .grupo(grupo)
                        .usuario(beneficiado)
                        .moneda(dto.getMoneda())
                        .deuda(0.0)
                        .build();
            } else {
                balanceBeneficiado = optionalBalanceBeneficiado.get(0);
            }
            Double deudaBeneficiado = balanceBeneficiado.getDeuda();
            deudaBeneficiado += valorACadaBeneficiado;
            balanceBeneficiado.setDeuda(Precision.round(deudaBeneficiado, 2));
            balanceRepo.save(balanceBeneficiado);
        });
    }

    @Transactional
    protected void reajustarBalancePorPago(PagoAddDto dto) {
        Grupo grupo = grupoRepo.findById(dto.getIdGrupo()).orElseThrow();
        Usuario realiza = usuarioRepo.findById(dto.getIdRealiza()).orElseThrow();
        Usuario recibe = usuarioRepo.findById(dto.getIdRecibe()).orElseThrow();

        Balance balanceRealiza;
        List<Balance> optionalBalanceRealiza = balanceRepo.findAll(
                Example.of(Balance.builder()
                        .grupo(grupo)
                        .usuario(realiza)
                        .moneda(dto.getMoneda())
                        .build()));
        if (optionalBalanceRealiza.isEmpty()) {
            balanceRealiza = Balance.builder()
                    .grupo(grupo)
                    .usuario(realiza)
                    .moneda(dto.getMoneda())
                    .deuda(0.0)
                    .build();
        } else {
            balanceRealiza = optionalBalanceRealiza.get(0);
        }
        Double deudaRealiza = balanceRealiza.getDeuda();
        deudaRealiza -= dto.getMonto();
        balanceRealiza.setDeuda(Precision.round(deudaRealiza, 2));
        balanceRepo.save(balanceRealiza);

        Balance balanceRecibe;
        List<Balance> optionalBalanceRecibe = balanceRepo.findAll(
                Example.of(Balance.builder()
                        .grupo(grupo)
                        .usuario(recibe)
                        .moneda(dto.getMoneda())
                        .build()));
        if (optionalBalanceRecibe.isEmpty()) {
            balanceRecibe = Balance.builder()
                    .grupo(grupo)
                    .usuario(recibe)
                    .moneda(dto.getMoneda())
                    .deuda(0.0)
                    .build();
        } else {
            balanceRecibe = optionalBalanceRecibe.get(0);
        }
        Double deudaRecibe = balanceRecibe.getDeuda();
        deudaRecibe -= dto.getMonto();
        balanceRecibe.setDeuda(Precision.round(deudaRecibe, 2));
        balanceRepo.save(balanceRecibe);
    }

}
