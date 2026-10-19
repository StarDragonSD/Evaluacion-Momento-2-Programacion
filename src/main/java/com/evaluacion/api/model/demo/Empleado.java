package com.evaluacion.api.model.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "empleados")
public class Empleado extends Usuario {

    @NotBlank(message = "El cargo no puede estar vacío")
    private String cargo;

    @NotNull(message = "El salario no puede ser nulo")
    @Min(value = 1, message = "El salario debe ser mayor a cero")
    private Double salario;

    public Empleado() {}

    public Empleado(Long id, String nombre, String correo, String cargo, Double salario) {
        super(id, nombre, correo);
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    @Override
    public String obtenerDescripcion() {
        return "Empleado - Nombre: " + getNombre() + " | Cargo: " + cargo + " | Salario: $" + salario;
    }
}