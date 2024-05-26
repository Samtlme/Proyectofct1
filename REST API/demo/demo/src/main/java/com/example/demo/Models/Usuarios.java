package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuarios")
public class Usuarios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDs gen
    private Integer id_usuario;

    @Column(name = "nombre_usuario", length = 255)
    private String nombre;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @Column(name = "puntos_totales")
    private Integer puntos_totales = 0; // Valor por defecto

    @Column(name = "rol")
    private Integer rol = 0; // 0 para normal, 1 para admin
    
    @ManyToMany
    @JsonIgnoreProperties("usuarios")
    @JoinTable(
            name = "Asignaciones",
            joinColumns = {@JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {@JoinColumn(name = "id_tarea")}
    )
    Set<Tareas> tareas = new HashSet<>();

    public Usuarios() {
    }
    
    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre = nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getPuntos_totales() {
        return puntos_totales;
    }

    public void setPuntos_totales(Integer puntos_totales) {
        this.puntos_totales = puntos_totales;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public Set<Tareas> getTareas() {
        return tareas;
    }

    public void setTareas(Set<Tareas> tareas) {
        this.tareas = tareas;
    }
    
    
}
