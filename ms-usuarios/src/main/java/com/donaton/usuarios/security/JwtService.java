package com.donaton.usuarios.security;

import com.donaton.usuarios.model.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Esta es la firma secreta (NO COMPARTIR EN PRODUCCIÓN). Debe tener al menos 256 bits.
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generarToken(Usuario usuario) {
        // Aquí agregamos los datos que queremos que viajen dentro del token
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", usuario.getIdUsuario());
        claims.put("rol", usuario.getRol().name());
        claims.put("email", usuario.getEmail());

        return Jwts.builder()
                .setClaims(claims) // Los datos extra (payload)
                .setSubject(usuario.getIdUsuario()) // A quién le pertenece
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // Caduca en 2 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Se firma con algoritmo y la llave secreta
                .compact(); // Construye el token final
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}