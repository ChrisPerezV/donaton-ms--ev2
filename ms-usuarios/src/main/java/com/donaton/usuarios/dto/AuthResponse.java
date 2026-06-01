package com.donaton.usuarios.dto;

public class AuthResponse {
    private String token;
    private String idUsuario;
    private String rol;

    //Constructor vacio
    public AuthResponse() {}

    // Constructor con ambos parámetros
    public AuthResponse(String token, String idUsuario, String rol) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.rol = rol;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRol() { return rol;
    }

    public void setRol(String rol) { this.rol = rol;
    }
}