package com.example.proyectofct.bbdd.entities;

import java.util.HashSet;
import java.util.Set;

public class Usuarios {

    private Integer id_usuario;
    private String nombre;
    private String contrasena;
    private Integer puntos_totales = 0;
    private Integer rol = 0;// 0 para normal, 1 para admin
    private Set<Tareas> tareas = new HashSet<>();

    public Usuarios() {
    }

    public Usuarios(Integer id_usuario, String nombre, String contrasena, Integer puntos_totales, Integer rol, Set<Tareas> tareas) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.puntos_totales = puntos_totales;
        this.rol = rol;
        this.tareas = tareas;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
