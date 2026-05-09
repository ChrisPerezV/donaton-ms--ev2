package com.donaton.donaciones.dto;

import lombok.Data;

@Data
public class InventarioCargaRequest {
    private String categoria;
    private Integer cantidad;
    private String unidadMedida;
    private String idCentro;
}