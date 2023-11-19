package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.EquipayApplication;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoAddDto;
import com.proyecto.g2.equipay.services.PagoService;
import java.time.LocalDate;
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
public class PagoServiceTest {

    @Autowired
    PagoService pagoSvc;

    String testUser = "alejandro.perez@mail.com";
    String testUser2 = "eduardo.martinez@mail.com";

    @Test
    public void agregarPago_success() {
        PagoAddDto pago = new PagoAddDto(1000.0, "UYU", LocalDate.now(), 1, "eduardo.martinez@mail.com", testUser);
        assertDoesNotThrow(() -> pagoSvc.crearPago(pago));
    }

    @Test
    public void agregarPago_grupoNoExiste_exception() {
        PagoAddDto pago = new PagoAddDto(1000.0, "UYU", LocalDate.now(), 99, "eduardo.martinez@mail.com", testUser);
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.crearPago(pago);
        });
    }

    @Test
    public void agregarPago_usuarioRealizaNoExiste_exception() {
        PagoAddDto pago = new PagoAddDto(1000.0, "UYU", LocalDate.now(), 1, "no-user@mail.com", testUser);
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.crearPago(pago);
        });
    }

    @Test
    public void agregarPago_usuarioRecibeNoExiste_exception() {
        PagoAddDto pago = new PagoAddDto(1000.0, "UYU", LocalDate.now(), 1, "eduardo.martinez@mail.com", "no-user@mail.com");
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.crearPago(pago);
        });
    }

    @Test
    @Transactional
    public void buscarPago_success() {
        assertDoesNotThrow(() -> pagoSvc.buscarPago(1));
    }

    @Test
    @Transactional
    public void buscarPago_pagoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.buscarPago(99);
        });
    }

    @Test
    @Transactional
    public void listarPagosRealizadoPorUsuario_success() {
        assertDoesNotThrow(() -> pagoSvc.listarPagosRealizadosPorUsuario(testUser2));
    }

    @Test
    public void listarPagosRealizadoPorUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.listarPagosRealizadosPorUsuario("no-user@mail.com");
        });
    }

    @Test
    @Transactional
    public void listarPagosRecibidosPor_success() {
        assertDoesNotThrow(() -> pagoSvc.listarPagosRecibidosPorUsuario(testUser));
    }

    @Test
    public void listarPagosRecibidosPor_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.listarPagosRecibidosPorUsuario("no-user@mail.com");
        });
    }

    @Test
    @Transactional
    public void listarPagosRecibidosPorEnGrupo_success() {
        assertDoesNotThrow(() -> pagoSvc.listarPagosRecibidosPorUsuarioEnGrupo(testUser, 1));
    }

    @Test
    @Transactional
    public void listarPagosRecibidosPorEnGrupo_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.listarPagosRecibidosPorUsuarioEnGrupo("no-user@mail.com", 1);
        });
    }

    @Test
    @Transactional
    public void listarPagosRecibidosPorEnGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            pagoSvc.listarPagosRecibidosPorUsuarioEnGrupo("matias.sanchez@mail.com", 99);
        });
    }

}
