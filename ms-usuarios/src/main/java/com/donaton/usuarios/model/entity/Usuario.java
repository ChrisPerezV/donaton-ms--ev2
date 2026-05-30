package com.donaton.usuarios.model.entity;

import com.donaton.usuarios.model.enums.RolUsuario;
import com.donaton.usuarios.model.enums.TipoDonante;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUsuario;

    @Column(nullable = false, unique = true)
    private String documentoIdentidad; // RUT o Razón Social ID

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // En el futuro lo encriptaremos con BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDonante tipoDonante;
}