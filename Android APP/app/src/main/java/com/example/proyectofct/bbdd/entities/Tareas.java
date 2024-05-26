package com.example.proyectofct.bbdd.entities;

import java.util.HashSet;
import java.util.Set;

public class Tareas {
    private Integer id_tarea;
    private Proyectos proyecto;
    private Set<Usuarios> usuarios = new HashSet<>();
    private String nombre_tarea;
    private String descripcion;
    private String estado; // 'pendiente' o 'completada'
    private Integer puntos_tarea; // Por defecto, 0

    private String prioridad_tarea; // 'S', 'A', 'B'

    public Tareas() {
    }

    public Tareas(Integer id_tarea, Proyectos proyecto, Set<Usuarios> usuarios, String nombre_tarea, String descripcion, String estado, Integer puntos_tarea, String prioridad_tarea) {
        this.id_tarea = id_tarea;
        this.proyecto = proyecto;
        this.usuarios = usuarios;
        this.nombre_tarea = nombre_tarea;
        this.descripcion = descripcion;
        this.estado = estado;
        this.puntos_tarea = puntos_tarea;
        this.prioridad_tarea = prioridad_tarea;
    }

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

    public Set<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuarios> usuarios) {
        this.usuarios = usuarios;
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
}
