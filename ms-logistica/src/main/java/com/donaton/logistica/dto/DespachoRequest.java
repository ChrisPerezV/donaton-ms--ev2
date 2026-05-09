package com.donaton.logistica.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class DespachoRequest {

    @NotBlank(message = "El ID del centro de origen es obligatorio")
    private String idCentroOrigen;

    private String idNecesidadDestino;

    @NotBlank(message = "La patente del vehículo es obligatoria")
    private String patenteVehiculo;

    @NotEmpty(message = "Debes enviar al menos un ítem en el camión")
    @Valid
    private List<ItemDespachoRequest> items;
}