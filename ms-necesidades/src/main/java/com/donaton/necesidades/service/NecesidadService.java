package com.donaton.necesidades.service;

import com.donaton.necesidades.client.UsuariosClient;
import com.donaton.necesidades.dto.ItemNecesidadRequest;
import com.donaton.necesidades.dto.NecesidadRequest;
import com.donaton.necesidades.model.entity.ItemNecesidad;
import com.donaton.necesidades.model.entity.Necesidad;
import com.donaton.necesidades.model.enums.EstadoNecesidad;
import com.donaton.necesidades.model.enums.EstadoRegistro;
import com.donaton.necesidades.repository.NecesidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NecesidadService {

    @Autowired
    private NecesidadRepository repository;

    @Autowired
    private UsuariosClient usuariosClient;

    // Crear Necesidad con Ítems (Mapeo de DTO a Entidad)
    public Necesidad crearConItems(NecesidadRequest request) {
        try {
            usuariosClient.obtenerUsuarioPorId(request.getIdUsuarioCreador());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: El usuario creador (ID: " + request.getIdUsuarioCreador() + ") no existe en el sistema.");
        }

        // Se unificó la creación de la entidad Necesidad
        Necesidad necesidad = new Necesidad();
        necesidad.setIdUsuarioCreador(request.getIdUsuarioCreador());
        necesidad.setTituloEmergencia(request.getTituloEmergencia());
        necesidad.setIdComuna(request.getIdComuna());
        necesidad.setDireccionEspecifica(request.getDireccionEspecifica());

        // Al crearla, le asignamos valores Enum por defecto
        necesidad.setEstado(EstadoNecesidad.ACTIVA);
        necesidad.setEstadoRegistro(EstadoRegistro.ACTIVO);

        for (ItemNecesidadRequest itemDto : request.getItems()) {
            ItemNecesidad item = new ItemNecesidad();
            item.setCategoria(itemDto.getCategoria());
            item.setDescripcionItem(itemDto.getDescripcionItem());
            item.setCantidadRequerida(itemDto.getCantidadRequerida());
            item.setUnidadMedida(itemDto.getUnidadMedida());
            item.setPrioridad(itemDto.getPrioridad());

            necesidad.agregarItem(item);
        }

        return repository.save(necesidad);
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

    // Actualizar Estado General de la Emergencia
    public Necesidad actualizarEstado(String id, String nuevoEstado) {
        Necesidad necesidad = obtenerPorId(id);

        try {
            // Convertimos el String que llega a tu nuevo Enum
            EstadoNecesidad estadoEnum = EstadoNecesidad.valueOf(nuevoEstado.toUpperCase());
            necesidad.setEstado(estadoEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El estado proporcionado no es válido. Usa: ACTIVA, EN_PROCESO, RESUELTA o CANCELADA.");
        }

        return repository.save(necesidad);
    }

    // Eliminar (Borrado lógico)
    public void eliminarNecesidad(String id) {
        Necesidad necesidad = obtenerPorId(id);

        // Usamos el Enum INACTIVO en lugar del String "ELIMINADO"
        necesidad.setEstadoRegistro(EstadoRegistro.INACTIVO);

        repository.save(necesidad);
    }
}