package com.proyecto.g2.equipay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index").permitAll() ;// Permite el acceso a /index sin autenticación
                //.anyRequest().authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login") // Página de inicio de sesión personalizada
                //.permitAll();
    }
}
