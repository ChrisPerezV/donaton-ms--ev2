package com.donaton.logistica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-necesidades", url = "${URL_MS_NECESIDADES:http://localhost:8082}/api/v1/items-necesidad")
public interface NecesidadesClient {

    @PutMapping("/necesidad/{idNecesidad}/cubrir")
    void registrarAporte(
            @PathVariable("idNecesidad") String idNecesidad,
            @RequestParam("categoria") String categoria,
            @RequestParam("cantidadAportada") Integer cantidadAportada);
}