package com.donaton.donaciones.dto;

import com.donaton.donaciones.model.enums.CategoriaItem;
import com.donaton.donaciones.model.enums.UnidadMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDonacionRequest {

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaItem categoria;

    @NotBlank(message = "La descripción del ítem no puede estar vacía")
    private String descripcionItem;

    private UnidadMedida unidadMedida;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}