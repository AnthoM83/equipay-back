package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.EquipayApplication;
import com.proyecto.g2.equipay.services.ResumenService;
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
public class ResumenServiceTest {

    @Autowired
    private ResumenService resumenSvc;

    String userTest = "alejandro.perez@mail.com";
    String noUser = "no-user@mail.com";

    @Test
    public void cantidadDeGastosCubiertosPorUsuario_success() {
        assertDoesNotThrow(() -> resumenSvc.cantidadDeGastosCubiertosPorUsuario(userTest));
    }

    @Test
    public void cantidadDeGastosCubiertosPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.cantidadDeGastosCubiertosPorUsuario(noUser);
        });
    }

    @Test
    @Transactional
    public void valorTotalDeGastosCubiertosPorUsuario_success() {
        assertDoesNotThrow(() -> resumenSvc.valorTotalDeGastosCubiertosPorUsuario(userTest));
    }

    @Test
    public void valorTotalDeGastosCubiertosPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.valorTotalDeGastosCubiertosPorUsuario(noUser);
        });
    }

    @Test
    public void cantidadDeGastosEnGrupo_success() {
        assertDoesNotThrow(() -> resumenSvc.cantidadDeGastosEnGrupo(1));
    }

    @Test
    public void cantidadDeGastosEnGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.cantidadDeGastosEnGrupo(99);
        });
    }

    @Test
    @Transactional
    public void valorTotalDeGastosEnGrupo_success() {
        assertDoesNotThrow(() -> resumenSvc.valorTotalDeGastosEnGrupo(1));
    }

    @Test
    public void valorTotalDeGastosEnGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.valorTotalDeGastosEnGrupo(99);
        });
    }

    @Test
    public void cantidadDePagosRealizadosPorUsuario_success() {
        assertDoesNotThrow(() -> resumenSvc.cantidadDePagosRealizadosPorUsuario(userTest));
    }

    @Test
    public void cantidadDePagosRealizadosPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.cantidadDePagosRealizadosPorUsuario(noUser);
        });
    }

    @Test
    public void valorTotalDePagosRealizadosPorUsuario_success() {
        assertDoesNotThrow(() -> resumenSvc.valorTotalDePagosRealizadosPorUsuario(userTest));
    }

    @Test
    public void valorTotalDePagosRealizadosPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.valorTotalDePagosRealizadosPorUsuario(noUser);
        });
    }

    @Test
    public void cantidadDePagosEnGrupo_success() {
        assertDoesNotThrow(() -> resumenSvc.cantidadDePagosEnGrupo(1));
    }

    @Test
    public void cantidadDePagosEnGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.cantidadDePagosEnGrupo(99);
        });
    }

    @Test
    public void valorTotalDePagosEnGrupo_success() {
        assertDoesNotThrow(() -> resumenSvc.valorTotalDePagosEnGrupo(1));
    }

    @Test
    public void valorTotalDePagosEnGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.valorTotalDePagosEnGrupo(99);
        });
    }

    @Test
    public void valorTotalDePagosRealizadosPorUsuarioEnGrupos_success() {
        assertDoesNotThrow(() -> resumenSvc.valorTotalDePagosRealizadosPorUsuarioEnGrupos(userTest, "UYU"));
    }

    @Test
    public void valorTotalDePagosRealizadosPorUsuarioEnGrupos_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.valorTotalDePagosRealizadosPorUsuarioEnGrupos(noUser, "UYU");
        });
    }

    @Test
    public void valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses_success() {
        assertDoesNotThrow(() -> resumenSvc.valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses(userTest, "UYU"));
    }

    @Test
    public void valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            resumenSvc.valorTotalGastosCubiertosPorUsuarioEnUltimosDoceMeses(noUser, "UYU");
        });
    }

}
