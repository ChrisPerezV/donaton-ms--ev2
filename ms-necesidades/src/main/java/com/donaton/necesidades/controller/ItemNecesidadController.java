package com.donaton.necesidades.controller;

import com.donaton.necesidades.model.enums.CategoriaItem;
import com.donaton.necesidades.service.ItemNecesidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items-necesidad")
public class ItemNecesidadController {

    @Autowired
    private ItemNecesidadService service;

    // Ruta original
    @PutMapping("/{idItem}/cubrir")
    public ResponseEntity<Void> registrarAporte(@PathVariable String idItem, @RequestParam Integer cantidadAportada) {
        service.sumarCantidadCubierta(idItem, cantidadAportada);
        return ResponseEntity.ok().build();
    }

    // NUEVA RUTA: Recibe la categoría como parámetro
    @PutMapping("/necesidad/{idNecesidad}/cubrir")
    public ResponseEntity<Void> registrarAportePorCategoria(
            @PathVariable String idNecesidad,
            @RequestParam CategoriaItem categoria,
            @RequestParam Integer cantidadAportada) {

        service.sumarPorNecesidadYCategoria(idNecesidad, categoria, cantidadAportada);
        return ResponseEntity.ok().build();
    }
}