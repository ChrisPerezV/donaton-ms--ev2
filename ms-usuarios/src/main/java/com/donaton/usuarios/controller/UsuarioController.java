package com.donaton.usuarios.controller;

import com.donaton.usuarios.dto.LoginRequest;
import com.donaton.usuarios.dto.UsuarioRegistroRequest;
import com.donaton.usuarios.model.entity.Usuario;
import com.donaton.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody UsuarioRegistroRequest request) {
        Usuario nuevoUsuario = service.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest request) {
        Usuario usuario = service.iniciarSesion(request);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable("id") String id) {
        Usuario usuario = service.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }
}