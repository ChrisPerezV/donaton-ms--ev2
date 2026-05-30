package com.donaton.donaciones.service;

import com.donaton.donaciones.client.LogisticaClient;
import com.donaton.donaciones.client.UsuariosClient;
import com.donaton.donaciones.dto.DonacionRequest;
import com.donaton.donaciones.dto.InventarioCargaRequest;
import com.donaton.donaciones.dto.ItemDonacionRequest;
import com.donaton.donaciones.model.entity.Donacion;
import com.donaton.donaciones.model.entity.ItemDonacion;
import com.donaton.donaciones.model.enums.UnidadMedida; // <-- Import limpio
import com.donaton.donaciones.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repository;

    @Autowired
    private LogisticaClient logisticaClient;

    @Autowired
    private UsuariosClient usuariosClient;

    public Donacion crearConItems(DonacionRequest request) {
        try {
            usuariosClient.obtenerUsuarioPorId(request.getIdDonante());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: El usuario donante (ID: " + request.getIdDonante() + ") no existe en el sistema.");
        }

        Donacion donacion = new Donacion();
        donacion.setIdDonante(request.getIdDonante());
        donacion.setTipoDonacion(request.getTipoDonacion());

        for (ItemDonacionRequest itemDto : request.getItems()) {
            ItemDonacion item = new ItemDonacion();
            item.setCategoria(itemDto.getCategoria());
            item.setDescripcionItem(itemDto.getDescripcionItem());
            item.setCantidad(itemDto.getCantidad());

            // Asignamos el Enum (o UNIDADES por defecto) limpio
            item.setUnidadMedida(itemDto.getUnidadMedida() != null ? itemDto.getUnidadMedida() : UnidadMedida.UNIDADES);

            donacion.agregarItem(item);
        }

        // 1. Guardamos la donación en la base de datos local
        Donacion donacionGuardada = repository.save(donacion);

        // 2. Enviamos los ítems a ms-logistica (Centro de Acopio)
        if (request.getIdCentroAcopio() != null && !request.getIdCentroAcopio().trim().isEmpty()) {
            for (ItemDonacion item : donacionGuardada.getItems()) {
                InventarioCargaRequest carga = new InventarioCargaRequest();
                carga.setCategoria(item.getCategoria().name());
                carga.setCantidad(item.getCantidad());

                // Extraemos el nombre del Enum para mandarlo a Logística
                carga.setUnidadMedida(item.getUnidadMedida().name());

                carga.setIdCentro(request.getIdCentroAcopio());

                try {
                    logisticaClient.cargarInventario(carga);
                    System.out.println("Ítem enviado exitosamente a la bodega: " + carga.getCategoria());
                } catch (Exception e) {
                    System.err.println("Error sumando stock en ms-logistica: " + e.getMessage());
                }
            }
        }

        return donacionGuardada;
    }

    public List<Donacion> obtenerTodas() {
        return repository.findAll();
    }
}