package com.proyecto.g2.equipay.security;

import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.models.UsuarioBase;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private final String name;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(UsuarioBase usuario) {
        name = usuario.getCorreo();
        password = usuario.getPassword();
        String rolesArray = usuario instanceof Usuario ? Usuario.getRoles() : Admin.getRoles();
        authorities = Arrays.stream(rolesArray.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
