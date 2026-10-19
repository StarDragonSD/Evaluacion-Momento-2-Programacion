package com.evaluacion.api.model.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {

    @NotNull(message = "El nivel de acceso es obligatorio")
    @Min(value = 1, message = "El nivel de acceso mínimo es 1")
    @Max(value = 5, message = "El nivel de acceso máximo es 5")
    private Integer nivelAcceso;

    public Administrador() {}

    public Administrador(Long id, String nombre, String correo, Integer nivelAcceso) {
        super(id, nombre, correo);
        this.nivelAcceso = nivelAcceso;
    }

    public Integer getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(Integer nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    @Override
    public String obtenerDescripcion() {
        return "Administrador - Nombre: " + getNombre() + " | Nivel de Acceso: " + nivelAcceso;
    }
}