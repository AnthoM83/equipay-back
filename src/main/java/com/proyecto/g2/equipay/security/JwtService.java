package com.proyecto.g2.equipay.security;

import com.proyecto.g2.equipay.commons.enums.EstadoUsuario;
import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IAdminRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
public class JwtService {

    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IAdminRepository adminRepo;

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(userName);
        String nombre = "";
        String apellido = "";
        String rol = "";
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            nombre = usuario.getNombre();
            apellido = usuario.getApellido();
            rol = "Usuario";
            if (usuario.getEstadoUsuario() == EstadoUsuario.ELIMINADO) {
                throw new UsernameNotFoundException("Usuario no existe");
            }
        } else {
            Admin admin = adminRepo.findById(userName).orElseThrow();
            nombre = admin.getNombre();
            apellido = admin.getApellido();
            rol = "Admin";
        }
        claims.put("nombre", nombre);
        claims.put("apellido", apellido);
        claims.put("rol", rol);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
