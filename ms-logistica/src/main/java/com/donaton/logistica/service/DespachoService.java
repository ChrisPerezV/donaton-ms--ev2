package com.donaton.logistica.service;

import com.donaton.logistica.client.NecesidadesClient;
import com.donaton.logistica.dto.DespachoRequest;
import com.donaton.logistica.dto.ItemDespachoRequest;
import com.donaton.logistica.model.entity.CentroAcopio;
import com.donaton.logistica.model.entity.DespachoTrazabilidad;
import com.donaton.logistica.model.entity.Inventario;
import com.donaton.logistica.model.entity.ItemDespacho;
import com.donaton.logistica.model.enums.EstadoDespacho;
import com.donaton.logistica.repository.CentroAcopioRepository;
import com.donaton.logistica.repository.DespachoRepository;
import com.donaton.logistica.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepo;

    @Autowired
    private CentroAcopioRepository centroRepo;

    @Autowired
    private InventarioRepository inventarioRepo;

    @Autowired
    private NecesidadesClient necesidadesClient;

    @Transactional
    public DespachoTrazabilidad crearDespacho(DespachoRequest request) {
        CentroAcopio centro = centroRepo.findById(request.getIdCentroOrigen())
                .orElseThrow(() -> new RuntimeException("Centro de acopio no encontrado"));

        DespachoTrazabilidad despacho = new DespachoTrazabilidad();
        despacho.setCentroOrigen(centro);
        despacho.setIdNecesidadDestino(request.getIdNecesidadDestino());
        despacho.setPatenteVehiculo(request.getPatenteVehiculo());
        despacho.setEstado(EstadoDespacho.EN_TRANSITO);
        despacho.setFechaSalida(LocalDateTime.now());

        // Revisar inventario y descontar
        List<Inventario> stockBodega = inventarioRepo.findByCentroAcopioId(centro.getId());

        for (ItemDespachoRequest dto : request.getItems()) {
            Inventario itemBodega = stockBodega.stream()
                    .filter(inv -> inv.getCategoria().equals(dto.getCategoria()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No hay " + dto.getCategoria() + " en esta bodega"));

            if (itemBodega.getCantidad() < dto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente de " + dto.getCategoria() + ". Hay: " + itemBodega.getCantidad());
            }

            itemBodega.setCantidad(itemBodega.getCantidad() - dto.getCantidad());
            inventarioRepo.save(itemBodega);

            ItemDespacho itemCamion = new ItemDespacho();
            itemCamion.setCategoria(dto.getCategoria());
            itemCamion.setCantidad(dto.getCantidad());
            despacho.agregarItem(itemCamion);
        }

        return despachoRepo.save(despacho);
    }

    public DespachoTrazabilidad actualizarEstado(String idDespacho, EstadoDespacho nuevoEstado) {
        DespachoTrazabilidad despacho = despachoRepo.findById(idDespacho)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));

        despacho.setEstado(nuevoEstado);
        DespachoTrazabilidad guardado = despachoRepo.save(despacho);

        // Notificamos a ms-necesidades enviando la categoría de cada ítem
        if (nuevoEstado == EstadoDespacho.ENTREGADO && despacho.getIdNecesidadDestino() != null) {
            for (ItemDespacho item : despacho.getItems()) {
                try {
                    necesidadesClient.registrarAporte(
                            despacho.getIdNecesidadDestino(),
                            item.getCategoria().name(),
                            item.getCantidad()
                    );
                } catch (Exception e) {
                    System.err.println("Error notificando entrega de " + item.getCategoria() + ": " + e.getMessage());
                }
            }
        }

        return guardado;
    }

    public List<DespachoTrazabilidad> obtenerTodos() {
        return despachoRepo.findAll();
    }
}