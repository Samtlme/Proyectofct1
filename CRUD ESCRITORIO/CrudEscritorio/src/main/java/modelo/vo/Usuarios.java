/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author Sam
 */
public class Usuarios {
    private Integer id_usuario;
    private String nombre_usuario;
    private String contrasena;
    private Integer puntos_totales;
    private Integer rol;

    public Usuarios() {
    }

    public Usuarios(Integer id_usuario, String nombre_usuario, String contrasena, Integer puntos_totales, Integer rol) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.puntos_totales = puntos_totales;
        this.rol = rol;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
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

    @Override
    public String toString() {
        return "Usuarios{" + "id_usuario=" + id_usuario + ", nombre_usuario=" + nombre_usuario + ", contrasena=" + contrasena + ", puntos_totales=" + puntos_totales + ", rol=" + rol + '}';
    }
    
    
        
}
