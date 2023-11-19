package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.EquipayApplication;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.services.BalanceService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = EquipayApplication.class)
@AutoConfigureTestDatabase
public class BalanceServiceTest {

    @Autowired
    BalanceService balanceSvc;

    String testUser = "alejandro.perez@mail.com";

    @Test
    @Transactional
    public void buscarBalance_success() {
        assertDoesNotThrow(() -> balanceSvc.buscarBalance(1));
    }

    @Test
    @Transactional
    public void buscarBalance_balanceNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            balanceSvc.buscarBalance(99);
        });
    }

    @Test
    @Transactional
    public void listarBalancesCubiertosPorUsuario_success() {
        assertDoesNotThrow(() -> balanceSvc.listarBalancesDeUsuario(testUser));
    }

    @Test
    public void listarBalancesCubiertosPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            balanceSvc.listarBalancesDeUsuario("no-user@mail.com");
        });
    }

    @Test
    @Transactional
    public void listarBalancesDeLosQueUsuarioSeBeneficiaEnBalance_success() {
        assertDoesNotThrow(() -> balanceSvc.listarBalancesDeUsuarioEnGrupo("matias.sanchez@mail.com", 1));
    }

    @Test
    @Transactional
    public void listarBalancesDeLosQueUsuarioSeBeneficiaEnBalance_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            balanceSvc.listarBalancesDeUsuarioEnGrupo("no-user@mail.com", 1);
        });
    }

    @Test
    @Transactional
    public void listarBalancesDeLosQueUsuarioSeBeneficiaEnBalance_balanceNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            balanceSvc.listarBalancesDeUsuarioEnGrupo("matias.sanchez@mail.com", 99);
        });
    }

}
