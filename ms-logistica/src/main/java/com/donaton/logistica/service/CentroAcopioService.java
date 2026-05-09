package com.donaton.logistica.service;

import com.donaton.logistica.dto.CentroAcopioRequest;
import com.donaton.logistica.model.entity.CentroAcopio;
import com.donaton.logistica.repository.CentroAcopioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroAcopioService {

    @Autowired
    private CentroAcopioRepository repository;

    public CentroAcopio crearCentro(CentroAcopioRequest request) {
        CentroAcopio centro = new CentroAcopio();
        centro.setNombre(request.getNombre());
        centro.setDireccion(request.getDireccion());
        centro.setIdComuna(request.getIdComuna());
        centro.setCapacidadMaximaKilos(request.getCapacidadMaximaKilos());

        return repository.save(centro);
    }

    public List<CentroAcopio> obtenerTodos() {
        return repository.findAll();
    }
    public void eliminarCentro(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El ID del centro no existe.");
        }
        repository.deleteById(id);
    }
}