package com.donaton.necesidades.service;

import com.donaton.necesidades.model.entity.ItemNecesidad;
import com.donaton.necesidades.model.enums.CategoriaItem;
import com.donaton.necesidades.model.enums.EstadoItem;
import com.donaton.necesidades.repository.ItemNecesidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemNecesidadService {

    @Autowired
    private ItemNecesidadRepository repository;

    // Método original (por ID de ítem)
    public void sumarCantidadCubierta(String idItem, Integer cantidadAportada) {
        ItemNecesidad item = repository.findById(idItem)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado"));

        item.setCantidadCubierta(item.getCantidadCubierta() + cantidadAportada);

        if (item.getCantidadCubierta() >= item.getCantidadRequerida()) {
            item.setEstado(EstadoItem.CUBIERTO);
        }

        repository.save(item);
    }

    // NUEVO MÉTODO: Por ID de Necesidad y Categoría (Multi-drop)
    public void sumarPorNecesidadYCategoria(String idNecesidad, CategoriaItem categoria, Integer cantidadAportada) {
        ItemNecesidad item = repository.findByNecesidadIdAndCategoria(idNecesidad, categoria)
                .orElseThrow(() -> new RuntimeException("La necesidad no requiere el ítem: " + categoria));

        item.setCantidadCubierta(item.getCantidadCubierta() + cantidadAportada);

        if (item.getCantidadCubierta() >= item.getCantidadRequerida()) {
            item.setEstado(EstadoItem.CUBIERTO);
        }

        repository.save(item);
    }
}