package com.donaton.logistica.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comuna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comuna {

    @Id
    @Column(name = "id_comuna")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "id_region")
    private Integer idRegion;
}