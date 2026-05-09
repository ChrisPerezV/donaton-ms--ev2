package com.donaton.donaciones.model.entity;

import com.donaton.donaciones.model.enums.CategoriaItem;
import com.donaton.donaciones.model.enums.UnidadMedida;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_donacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item_donado")
    private String idItemDonado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaItem categoria;

    @Column(name = "descripcion_item", nullable = false)
    private String descripcionItem;

    @Column(nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida")
    private UnidadMedida unidadMedida;

    // Relación de vuelta a la Donación principal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_donacion", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Donacion donacion;
}