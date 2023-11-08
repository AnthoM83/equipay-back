package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.mappers.UsuarioMapper;
import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.models.UsuarioBase;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    UsuarioMapper mapper;
    @Autowired
    AuthenticationManager authenticationManager;

    public String iniciarSesion(String correo, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    correo,
                    password
            );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UsuarioBase usuarioBase = usuarioRepo.findById(correo).orElseThrow();

            if (usuarioBase.getCorreo().isEmpty()) {
                throw new Exception("usuario y/o contraseña inválidos.");
            } else {
                return generateToken(usuarioBase);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken(UsuarioBase usuarioBase) {
        String rol = null;
        if (usuarioBase instanceof Admin) {
            rol = "Admin";
        } else if (usuarioBase instanceof Usuario) {
            rol = "Usuario";
        }

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 30 * 60 * 1000);
        //new Date(System.currentTimeMillis()

        Map<String, Object> claims = new HashMap<>();
        // Agregar atributos personalizados al token
        claims.put("nombre", usuarioBase.getNombre());
        claims.put("apellido", usuarioBase.getApellido());
        claims.put("rol", rol);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(usuarioBase.getCorreo())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();

        return token;
    }
}
