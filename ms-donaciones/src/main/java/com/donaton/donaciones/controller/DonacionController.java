package com.donaton.donaciones.controller;

import com.donaton.donaciones.dto.DonacionRequest;
import com.donaton.donaciones.model.entity.Donacion;
import com.donaton.donaciones.service.DonacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService service;

    @PostMapping
    public ResponseEntity<Donacion> crearDonacion(@Valid @RequestBody DonacionRequest request) {
        Donacion nuevaDonacion = service.crearConItems(request);
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Donacion>> listarTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }
}