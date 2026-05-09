package com.donaton.logistica.dto;

import com.donaton.logistica.model.enums.CategoriaItem;
import com.donaton.logistica.model.enums.UnidadMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioRequest {
    @NotNull(message = "La categoría es obligatoria")
    private CategoriaItem categoria;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1)
    private Integer cantidad;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida;

    @NotBlank(message = "Debes indicar el ID del centro de acopio")
    private String idCentro;
}