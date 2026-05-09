package com.donaton.logistica.model.entity;

import com.donaton.logistica.model.enums.EstadoDespacho;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "despacho_trazabilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespachoTrazabilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_despacho")
    private String id;

    @Column(name = "id_necesidad_destino")
    private String idNecesidadDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_origen")
    private CentroAcopio centroOrigen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDespacho estado = EstadoDespacho.PREPARANDO;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Column(name = "patente_vehiculo")
    private String patenteVehiculo;

    // La relación que acabamos de agregar
    @OneToMany(mappedBy = "despacho", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemDespacho> items = new ArrayList<>();

    // Método utilitario para mantener la sincronización en memoria
    public void agregarItem(ItemDespacho item) {
        items.add(item);
        item.setDespacho(this);
    }
}