package com.donaton.necesidades.model.entity;

import com.donaton.necesidades.model.enums.EstadoNecesidad;
import com.donaton.necesidades.model.enums.EstadoRegistro;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "necesidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Necesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_necesidad")
    private String id;

    @Column(name = "id_usuario_creador", nullable = false)
    private String idUsuarioCreador;

    @Column(name = "titulo_emergencia", nullable = false)
    private String tituloEmergencia;

    @Column(name = "id_comuna", nullable = false)
    private Integer idComuna;

    @Column(name = "direccion_especifica")
    private String direccionEspecifica;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoNecesidad estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_registro")
    private EstadoRegistro estadoRegistro;

    @Column(name = "fecha_reporte", updatable = false)
    private LocalDateTime fechaReporte = LocalDateTime.now();

    @OneToMany(mappedBy = "necesidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemNecesidad> items = new ArrayList<>();

    // Método de conveniencia profesional para mantener ambas partes sincronizadas
    public void agregarItem(ItemNecesidad item) {
        items.add(item);
        item.setNecesidad(this);
    }
}