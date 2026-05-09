package com.donaton.necesidades.service;

import com.donaton.necesidades.dto.ItemNecesidadRequest;
import com.donaton.necesidades.dto.NecesidadRequest;
import com.donaton.necesidades.model.entity.ItemNecesidad;
import com.donaton.necesidades.model.entity.Necesidad;
import com.donaton.necesidades.repository.NecesidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NecesidadService {

    @Autowired
    private NecesidadRepository repository;

    // Crear Necesidad con Ítems (Mapeo de DTO a Entidad)
    public Necesidad crearConItems(NecesidadRequest request) {
        Necesidad n = new Necesidad();
        n.setIdUsuarioCreador(request.getIdUsuarioCreador());
        n.setTituloEmergencia(request.getTituloEmergencia());
        n.setIdComuna(request.getIdComuna());
        n.setDireccionEspecifica(request.getDireccionEspecifica());

        for (ItemNecesidadRequest itemDto : request.getItems()) {
            ItemNecesidad item = new ItemNecesidad();
            item.setCategoria(itemDto.getCategoria());
            item.setDescripcionItem(itemDto.getDescripcionItem());
            item.setCantidadRequerida(itemDto.getCantidadRequerida());
            item.setUnidadMedida(itemDto.getUnidadMedida());
            item.setPrioridad(itemDto.getPrioridad());

            n.agregarItem(item);
        }

        return repository.save(n);
    }

    // Obtener todas
    public List<Necesidad> obtenerTodas() {
        return repository.findAll();
    }

    // Obtener por ID
    public Necesidad obtenerPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Necesidad no encontrada en la BD"));
    }

    public Necesidad actualizarEstado(String id, String nuevoEstado) {
        Necesidad necesidad = obtenerPorId(id);
        necesidad.setEstado(nuevoEstado);
        return repository.save(necesidad);
    }

    public void eliminarNecesidad(String id) {
        Necesidad necesidad = obtenerPorId(id);
        necesidad.setEstadoRegistro("ELIMINADO");
        repository.save(necesidad);
    }
}