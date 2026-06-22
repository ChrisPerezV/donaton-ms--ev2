package com.donaton.logistica.dto;

import lombok.Data;
import java.util.List;

@Data
public class DonacionSqsEvent {
    private String id;
    private String idDonante;
    private String tipoDonacion;
    private String idCentroAcopio;
    private List<ItemDonacionSqsEvent> items;
}