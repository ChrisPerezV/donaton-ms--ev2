package com.donaton.usuarios.dto;

import com.donaton.usuarios.model.enums.RolUsuario;
import com.donaton.usuarios.model.enums.TipoDonante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRegistroRequest {
    @NotBlank
    private String documentoIdentidad;
    @NotBlank
    private String nombreCompleto;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private RolUsuario rol;
    @NotNull
    private TipoDonante tipoDonante;
}