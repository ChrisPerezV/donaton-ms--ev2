package com.donaton.donaciones.dto;

import com.donaton.donaciones.model.enums.TipoDonacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class DonacionRequest {

    @NotBlank(message = "El ID del donante es obligatorio")
    private String idDonante;

    @NotNull(message = "El tipo de donación es obligatorio")
    private TipoDonacion tipoDonacion;

    @NotEmpty(message = "Debes agregar al menos un ítem a la donación")
    @Valid
    private List<ItemDonacionRequest> items;

    @NotBlank(message = "Debes indicar en qué centro de acopio se entregó la donación")
    private String idCentroAcopio;
}