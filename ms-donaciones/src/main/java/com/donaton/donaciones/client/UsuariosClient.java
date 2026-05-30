package com.donaton.donaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8085/api/v1/usuarios")
public interface UsuariosClient {

    // Usamos Object genérico ya que solo nos interesa saber si no lanza error (si existe)
    @GetMapping("/{id}")
    Object obtenerUsuarioPorId(@PathVariable("id") String id);
}