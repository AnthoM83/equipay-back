package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.EquipayApplication;
import com.proyecto.g2.equipay.services.DeudaService;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EquipayApplication.class)
@AutoConfigureTestDatabase
public class DeudaServiceTest {

    @Autowired
    private DeudaService deudaSvc;

    @Test
    public void consultarDeudasDeUsuarioEnGrupo_success(Integer idGrupo, String idUsuario) {
        assertDoesNotThrow(() -> deudaSvc.consultarDeudasDeUsuarioEnGrupo(1, "alejandro.perez@mail.com"));
    }

    @Test
    public void consultarDeudasDeUsuarioEnGrupo_usuarioNoExiste_exception(Integer idGrupo, String idUsuario) {
        assertThrows(NoSuchElementException.class, () -> {
            deudaSvc.consultarDeudasDeUsuarioEnGrupo(1, "no-user@test.com");
        });
    }

    @Test
    public void consultarDeudasDeUsuarioEnGrupo_grupoNoExiste_exception(Integer idGrupo, String idUsuario) {
        assertThrows(NoSuchElementException.class, () -> {
            deudaSvc.consultarDeudasDeUsuarioEnGrupo(99, "alejandro.perez@test.com");
        });
    }

}
