package com.example.demo.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Proyectos") 
public class Proyectos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproyecto;

    @Column(name = "nombre_proyecto", length = 255)
    private String nombre_proyecto;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;

    // Getters y setters para cada campo
    public Integer getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Integer idproyecto) {
        this.idproyecto = idproyecto;
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

    public Proyectos() {
    }
    
}
