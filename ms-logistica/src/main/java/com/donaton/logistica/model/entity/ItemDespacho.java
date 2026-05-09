package com.donaton.logistica.model.entity;

import com.donaton.logistica.model.enums.CategoriaItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_despacho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaItem categoria;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_despacho", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private DespachoTrazabilidad despacho;
}