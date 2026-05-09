package com.donaton.logistica.dto;

import com.donaton.logistica.model.enums.CategoriaItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDespachoRequest {

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaItem categoria;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debe enviar al menos 1 ítem")
    private Integer cantidad;
}