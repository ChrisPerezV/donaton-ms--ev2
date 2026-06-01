package com.donaton.logistica.controller;

import com.donaton.logistica.model.entity.Comuna;
import com.donaton.logistica.service.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistica/comunas")
public class ComunaController {

    @Autowired
    private ComunaService service;

    @GetMapping
    public ResponseEntity<List<Comuna>> listarComunas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }
}