package com.donaton.logistica.dto;

import lombok.Data;

@Data
public class ItemDonacionSqsEvent {
    private String categoria;
    private Integer cantidad;
    private String unidadMedida;
    private String descripcionItem;
}