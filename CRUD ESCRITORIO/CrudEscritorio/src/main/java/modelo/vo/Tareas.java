package modelo.vo;

public class Tareas {

    private Integer id_tarea;
    private Integer proyecto;
    private String nombre_tarea;
    private String descripcion;
    private String estado; // 'pendiente' o 'completada'
    private Integer puntos_tarea; // Por defecto, 0
    private String prioridad_tarea; // 'S', 'A', 'B'

    public Tareas() {
    }

    public Tareas(Integer id_tarea, Integer proyecto, String nombre_tarea, String descripcion, String estado, Integer puntos_tarea, String prioridad_tarea) {
        this.id_tarea = id_tarea;
        this.proyecto = proyecto;
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

    public Integer getProyecto() {
        return proyecto;
    }

    public void setProyecto(Integer proyecto) {
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

    @Override
    public String toString() {
        return "" + id_tarea +" || "+ proyecto + " |||| " + nombre_tarea + " |||| " + descripcion + " |||| " + estado + " || " + puntos_tarea + " || " + prioridad_tarea;
    }
    
    
    
}
