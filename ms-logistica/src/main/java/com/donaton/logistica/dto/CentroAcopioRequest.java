package com.donaton.logistica.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CentroAcopioRequest {

    @NotBlank(message = "El nombre del centro de acopio es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotNull(message = "El ID de la comuna es obligatorio")
    private Integer idComuna;
}