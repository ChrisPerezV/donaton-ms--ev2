package com.donaton.donaciones.model.entity;

import com.donaton.donaciones.model.enums.EstadoDonacion;
import com.donaton.donaciones.model.enums.TipoDonacion;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_donacion")
    private String id;

    // Referencia al usuario (donante) que viene del Frontend / JWT
    @Column(name = "id_donante", nullable = false)
    private String idDonante;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_donacion", nullable = false)
    private TipoDonacion tipoDonacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_donacion")
    private EstadoDonacion estadoDonacion = EstadoDonacion.REGISTRADA;

    @Column(name = "estado_registro")
    private String estadoRegistro = "ACTIVO";

    @Column(name = "fecha_intencion", updatable = false)
    private LocalDateTime fechaIntencion = LocalDateTime.now();

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    // Relación Uno a Muchos con los ítems donados
    @OneToMany(mappedBy = "donacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemDonacion> items = new ArrayList<>();

    // Método de conveniencia para asegurar la relación bidireccional
    public void agregarItem(ItemDonacion item) {
        items.add(item);
        item.setDonacion(this);
    }
}