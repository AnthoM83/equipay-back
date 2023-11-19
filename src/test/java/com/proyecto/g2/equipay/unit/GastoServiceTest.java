package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.EquipayApplication;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.services.GastoService;
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
public class GastoServiceTest {

    @Autowired
    GastoService gastoSvc;

    String testUser = "alejandro.perez@mail.com";

    @Test
    public void agregarGasto_success() {
        GastoAddDto gasto = new GastoAddDto(1300.50, "UYU", "La Pasiva", LocalDate.parse("2023-10-20"), 1, testUser, Arrays.asList("eduardo.martinez@mail.com", "matias.sanchez@mail.com"), 1);
        assertDoesNotThrow(() -> gastoSvc.crearGasto(gasto));
    }

    @Test
    public void agregarGasto_grupoNoExiste_exception() {
        GastoAddDto gasto = new GastoAddDto(1300.50, "UYU", "La Pasiva", LocalDate.parse("2023-10-20"), 99, testUser, Arrays.asList("eduardo.martinez@mail.com", "matias.sanchez@mail.com"), 1);
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.crearGasto(gasto);
        });
    }

    @Test
    public void agregarGasto_usuarioQueCubreNoExiste_exception() {
        GastoAddDto gasto = new GastoAddDto(1300.50, "UYU", "La Pasiva", LocalDate.parse("2023-10-20"), 1, "no-user@mail,com", Arrays.asList("eduardo.martinez@mail.com", "matias.sanchez@mail.com"), 1);
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.crearGasto(gasto);
        });
    }

    @Test
    public void agregarGasto_usuarioBeneficiadoNoExiste_exception() {
        GastoAddDto gasto = new GastoAddDto(1300.50, "UYU", "La Pasiva", LocalDate.parse("2023-10-20"), 1, testUser, Arrays.asList("no-user@mail.com", "matias.sanchez@mail.com"), 1);
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.crearGasto(gasto);
        });
    }

    @Test
    @Transactional
    public void buscarGasto_success() {
        assertDoesNotThrow(() -> gastoSvc.buscarGasto(1));
    }

    @Test
    @Transactional
    public void buscarGasto_gastoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.buscarGasto(99);
        });
    }

    @Test
    @Transactional
    public void listarGastosCubiertosPorUsuario_success() {
        assertDoesNotThrow(() -> gastoSvc.listarGastosCubiertosPorUsuario(testUser));
    }

    @Test
    public void listarGastosCubiertosPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.listarGastosCubiertosPorUsuario("no-user@mail.com");
        });
    }

    @Test
    @Transactional
    public void listarGastosDeLosQueUsuarioSeBeneficia_success() {
        assertDoesNotThrow(() -> gastoSvc.listarGastosDeLosQueUsuarioSeBeneficia("matias.sanchez@mail.com"));
    }

    @Test
    public void listarGastosDeLosQueUsuarioSeBeneficia_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.listarGastosDeLosQueUsuarioSeBeneficia("no-user@mail.com");
        });
    }

    @Test
    @Transactional
    public void listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo_success() {
        assertDoesNotThrow(() -> gastoSvc.listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo("matias.sanchez@mail.com", 1));
    }

    @Test
    @Transactional
    public void listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo("no-user@mail.com", 1);
        });
    }

    @Test
    @Transactional
    public void listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            gastoSvc.listarGastosDeLosQueUsuarioSeBeneficiaEnGrupo("matias.sanchez@mail.com", 99);
        });
    }

}
