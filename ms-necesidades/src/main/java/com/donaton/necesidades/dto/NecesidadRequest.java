package com.donaton.necesidades.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class NecesidadRequest {

    @NotBlank(message = "El ID del usuario creador es obligatorio")
    private String idUsuarioCreador;

    @NotBlank(message = "El título de la emergencia no puede estar vacío")
    @Size(min = 10, max = 150, message = "El título debe tener entre 10 y 150 caracteres")
    private String tituloEmergencia;

    @NotNull(message = "El ID de la comuna es obligatorio")
    private Integer idComuna;

    private String direccionEspecifica;

    @NotEmpty(message = "Debes agregar al menos un ítem a la necesidad")
    @Valid
    private List<ItemNecesidadRequest> items;
}