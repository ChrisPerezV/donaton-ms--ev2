package com.donaton.necesidades.controller;

import com.donaton.necesidades.dto.NecesidadRequest;
import com.donaton.necesidades.model.entity.Necesidad;
import com.donaton.necesidades.service.NecesidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/necesidades")
public class NecesidadController {

    @Autowired
    private NecesidadService necesidadService;

    // Crear una necesidad
    @PostMapping
    public ResponseEntity<Necesidad> crearNecesidad(@Valid @RequestBody NecesidadRequest request) {
        Necesidad nuevaNecesidad = necesidadService.crearConItems(request);
        return new ResponseEntity<>(nuevaNecesidad, HttpStatus.CREATED);
    }

    // Obtener todas las necesidades activas
    @GetMapping
    public ResponseEntity<List<Necesidad>> listarTodas() {
        return ResponseEntity.ok(necesidadService.obtenerTodas());
    }

    // Obtener una necesidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<Necesidad> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(necesidadService.obtenerPorId(id));
    }

    // Cambiar el estado de la emergencia
    @PutMapping("/{id}/estado")
    public ResponseEntity<Necesidad> cambiarEstado(@PathVariable String id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(necesidadService.actualizarEstado(id, nuevoEstado));
    }

    // Eliminar la emergencia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNecesidad(@PathVariable String id) {
        necesidadService.eliminarNecesidad(id);
        return ResponseEntity.noContent().build();
    }
}