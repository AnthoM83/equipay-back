package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.EquipayApplication;
import com.proyecto.g2.equipay.services.EstadisticaService;
import java.time.YearMonth;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EquipayApplication.class)
@AutoConfigureTestDatabase
public class EstadisticaServiceTest {

    @Autowired
    private EstadisticaService estadisticaSvc;

    @Test
    public void promedioDeGastos_success() {
        assertDoesNotThrow(() -> estadisticaSvc.promedioDeGastos());
    }

    @Test
    public void gruposConActividadEnMes_success() {
        assertDoesNotThrow(() -> estadisticaSvc.gruposConActividadEnMes(YearMonth.now()));
    }
    
    

}
