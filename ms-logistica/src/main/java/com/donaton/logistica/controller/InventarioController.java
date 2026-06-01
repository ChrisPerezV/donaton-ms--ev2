package com.donaton.logistica.controller;

import com.donaton.logistica.dto.InventarioRequest;
import com.donaton.logistica.model.entity.Inventario;
import com.donaton.logistica.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistica/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @PostMapping("/cargar")
    public ResponseEntity<Inventario> cargarItem(@Valid @RequestBody InventarioRequest request) {
        return ResponseEntity.ok(service.agregarStock(request));
    }

    @GetMapping("/centro/{idCentro}")
    public ResponseEntity<List<Inventario>> obtenerInventarioCentro(@PathVariable String idCentro) {
        return ResponseEntity.ok(service.obtenerInventarioPorCentro(idCentro));
    }
}