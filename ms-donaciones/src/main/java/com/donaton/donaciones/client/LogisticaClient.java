package com.donaton.donaciones.client;

import com.donaton.donaciones.dto.InventarioCargaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Apuntamos al puerto 8084 de Logística
@FeignClient(name = "ms-logistica", url = "http://localhost:8084/api/v1/logistica/inventario")
public interface LogisticaClient {

    @PostMapping("/cargar")
    void cargarInventario(@RequestBody InventarioCargaRequest request);
}