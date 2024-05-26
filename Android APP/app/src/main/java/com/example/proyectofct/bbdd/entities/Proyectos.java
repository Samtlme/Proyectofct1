package com.example.proyectofct.bbdd.entities;


import java.sql.Date;

public class Proyectos {
    private Integer id_proyecto;
    private String nombre_proyecto;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_fin;

    public Proyectos() {

    }

    public Proyectos(Integer id_proyecto, String nombre_proyecto, String descripcion, Date fecha_inicio, Date fecha_fin) {
        this.id_proyecto = id_proyecto;
        this.nombre_proyecto = nombre_proyecto;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Integer getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(Integer id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getNombre_proyecto() {
        return nombre_proyecto;
    }

    public void setNombre_proyecto(String nombre_proyecto) {
        this.nombre_proyecto = nombre_proyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
}
