package com.proyecto.g2.equipay.unit.context;

import com.proyecto.g2.equipay.services.CategoriaService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoriaServiceTestContextConfig {

    @Bean
    public CategoriaService categoriaService() {
        return new CategoriaService();
    }

}
