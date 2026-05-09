package com.donaton.logistica.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "centro_acopio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CentroAcopio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_centro")
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(name = "id_comuna", nullable = false)
    private Integer idComuna;

    @Column(name = "capacidad_maxima_kilos")
    private Integer capacidadMaximaKilos;

    @OneToMany(mappedBy = "centroAcopio", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Inventario> inventarios = new ArrayList<>();
}