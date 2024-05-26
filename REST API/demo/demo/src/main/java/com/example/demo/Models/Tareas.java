package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tareas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID
    private Integer id_tarea; 

    @ManyToOne
    @JoinColumn(name = "idproyecto", nullable = false) // FK proyecto
    private Proyectos proyecto;

    //@JsonIgnore
    @ManyToMany(mappedBy = "tareas")
    @JsonIgnoreProperties("tareas")
    private Set<Usuarios> usuarios = new HashSet<>();
    
    @Column(nullable = false)
    private String nombre_tarea;

    @Column(nullable = false, length = 500) // Max  500
    private String descripcion;

    private String estado; // 'pendiente' o 'completada'

    private Integer puntos_tarea; // Por defecto, 0

    private String prioridad_tarea; // 'S', 'A', 'B'

    //constructor

    public Tareas() {
    }
    
    
    // Getters y Setters
    public Integer getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(Integer id_tarea) {
        this.id_tarea = id_tarea;
    }

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPuntos_tarea() {
        return puntos_tarea;
    }

    public void setPuntos_tarea(Integer puntos_tarea) {
        this.puntos_tarea = puntos_tarea;
    }

    public String getPrioridad_tarea() {
        return prioridad_tarea;
    }

    public void setPrioridad_tarea(String prioridad_tarea) {
        this.prioridad_tarea = prioridad_tarea;
    }

    public Set<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }
    
    
}

