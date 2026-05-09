package com.donaton.necesidades.dto;

import com.donaton.necesidades.model.enums.CategoriaItem;
import com.donaton.necesidades.model.enums.PrioridadItem;
import com.donaton.necesidades.model.enums.UnidadMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemNecesidadRequest {

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaItem categoria;

    @NotBlank(message = "Debes describir el ítem (ej: 'Agua sin gas de 5 litros')")
    private String descripcionItem;

    @NotNull(message = "La cantidad requerida es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidadRequerida;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida;

    private PrioridadItem prioridad = PrioridadItem.MEDIA;
}