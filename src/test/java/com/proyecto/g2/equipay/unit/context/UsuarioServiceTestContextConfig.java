package com.proyecto.g2.equipay.unit.context;

import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.services.UsuarioService;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class UsuarioServiceTestContextConfig {

    @Bean
    public UsuarioService usuarioService() {
        return new UsuarioService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsuarioMapper usuarioMapper() {
        return Mappers.getMapper(UsuarioMapper.class);
    }

}
