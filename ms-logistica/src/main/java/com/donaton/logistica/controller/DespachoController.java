package com.donaton.logistica.controller;

import com.donaton.logistica.dto.DespachoRequest;
import com.donaton.logistica.service.DespachoService;
import com.donaton.logistica.model.entity.DespachoTrazabilidad;
import com.donaton.logistica.model.enums.EstadoDespacho;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistica/despachos")
public class DespachoController {

    @Autowired
    private DespachoService service;

    @PostMapping
    public ResponseEntity<DespachoTrazabilidad> iniciarDespacho(@Valid @RequestBody DespachoRequest request) {
        return new ResponseEntity<>(service.crearDespacho(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<DespachoTrazabilidad> cambiarEstado(
            @PathVariable String id,
            @RequestParam EstadoDespacho nuevoEstado) {
        return ResponseEntity.ok(service.actualizarEstado(id, nuevoEstado));
    }

    @GetMapping
    public ResponseEntity<List<DespachoTrazabilidad>> listarDespachos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }
}