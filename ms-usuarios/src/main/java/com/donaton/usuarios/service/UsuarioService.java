package com.donaton.usuarios.service;

import com.donaton.usuarios.dto.LoginRequest;
import com.donaton.usuarios.dto.UsuarioRegistroRequest;
import com.donaton.usuarios.model.entity.Usuario;
import com.donaton.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(UsuarioRegistroRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        if (repository.existsByDocumentoIdentidad(request.getDocumentoIdentidad())) {
            throw new RuntimeException("El documento ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setDocumentoIdentidad(request.getDocumentoIdentidad());
        usuario.setNombreCompleto(request.getNombreCompleto());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario.setTipoDonante(request.getTipoDonante());

        return repository.save(usuario);
    }

    public Usuario iniciarSesion(LoginRequest request) {
        Usuario usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return usuario; // Si coincide, retorna los datos del usuario
    }
    public Usuario obtenerPorId(String idUsuario) {
        return repository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));
    }
}