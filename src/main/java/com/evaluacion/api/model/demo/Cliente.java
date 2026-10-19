package com.evaluacion.api.model.demo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    private Integer puntosFidelidad = 0;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("cliente")
    private List<Solicitud> solicitudes = new ArrayList<>();

    public Cliente() {}

    public Cliente(Long id, String nombre, String correo, Integer puntosFidelidad) {
        super(id, nombre, correo);
        this.puntosFidelidad = puntosFidelidad != null ? puntosFidelidad : 0;
    }

    public Integer getPuntosFidelidad() { return puntosFidelidad; }
    public void setPuntosFidelidad(Integer puntosFidelidad) { this.puntosFidelidad = puntosFidelidad; }

    public List<Solicitud> getSolicitudes() { return solicitudes; }
    public void setSolicitudes(List<Solicitud> solicitudes) { this.solicitudes = solicitudes; }

    @Override
    public String obtenerDescripcion() {
        return "Cliente - Nombre: " + getNombre() + " | Puntos de Fidelidad: " + puntosFidelidad;
    }
}