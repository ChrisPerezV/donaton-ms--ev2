package com.donaton.logistica.controller;

import com.donaton.logistica.dto.CentroAcopioRequest;
import com.donaton.logistica.model.entity.CentroAcopio;
import com.donaton.logistica.service.CentroAcopioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistica/centros-acopio")
public class CentroAcopioController {

    @Autowired
    private CentroAcopioService service;

    @PostMapping
    public ResponseEntity<CentroAcopio> registrarCentro(@Valid @RequestBody CentroAcopioRequest request) {
        CentroAcopio nuevoCentro = service.crearCentro(request);
        return new ResponseEntity<>(nuevoCentro, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CentroAcopio>> listarCentros() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCentro(@PathVariable String id) {
        service.eliminarCentro(id);
        // Devolvemos 204 No Content, que es el estándar para borrados exitosos
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CentroAcopio> cambiarEstado(@PathVariable String id, @RequestParam String estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }
}