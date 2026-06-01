package com.donaton.logistica.service;

import com.donaton.logistica.dto.InventarioRequest;
import com.donaton.logistica.model.entity.CentroAcopio;
import com.donaton.logistica.model.entity.Inventario;
import com.donaton.logistica.repository.InventarioRepository;
import com.donaton.logistica.repository.CentroAcopioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepo; // Dejamos solo este

    @Autowired
    private CentroAcopioRepository centroRepo;

    public Inventario agregarStock(InventarioRequest request) {
        CentroAcopio centro = centroRepo.findById(request.getIdCentro())
                .orElseThrow(() -> new RuntimeException("Centro de acopio no encontrado"));

        // Buscamos si ya existe esa categoría en este centro
        List<Inventario> actual = inventarioRepo.findByCentroAcopioId(request.getIdCentro());

        Inventario item = actual.stream()
                .filter(i -> i.getCategoria().equals(request.getCategoria()))
                .findFirst()
                .orElse(new Inventario());

        if (item.getId() == null) {
            item.setCategoria(request.getCategoria());
            item.setUnidadMedida(request.getUnidadMedida());
            item.setCantidad(request.getCantidad());
            item.setCentroAcopio(centro);
        } else {
            item.setCantidad(item.getCantidad() + request.getCantidad());
        }

        return inventarioRepo.save(item);
    }

    // Usamos el inventarioRepo que ya teníamos arriba
    public List<Inventario> obtenerInventarioPorCentro(String idCentroAcopio) {
        return inventarioRepo.findByCentroAcopioId(idCentroAcopio);
    }
}