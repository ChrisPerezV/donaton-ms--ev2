package com.donaton.necesidades.model.entity;

import com.donaton.necesidades.model.enums.CategoriaItem;
import com.donaton.necesidades.model.enums.PrioridadItem;
import com.donaton.necesidades.model.enums.UnidadMedida;
import com.donaton.necesidades.model.enums.EstadoItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_necesidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemNecesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item_req")
    private String idItemReq;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaItem categoria;

    @Column(name = "descripcion_item", nullable = false)
    private String descripcionItem;

    @Column(name = "cantidad_requerida", nullable = false)
    private Integer cantidadRequerida;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private UnidadMedida unidadMedida = UnidadMedida.UNIDADES;

    @Column(name = "cantidad_cubierta")
    private Integer cantidadCubierta = 0;

    @Enumerated(EnumType.STRING)
    private PrioridadItem prioridad = PrioridadItem.MEDIA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoItem estado = EstadoItem.PENDIENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_necesidad", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Necesidad necesidad;
}