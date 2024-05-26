
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import modelo.vo.Tareas;


public class TareasDAO {

    public void insetaTarea(Connection conn,Integer idProyectoAsociado, String nombre, String descripcion, String estado, Integer puntos, String prioridad) throws SQLException {
        
        String consulta = "INSERT INTO Tareas (idproyecto, nombre_tarea, descripcion, estado, puntos_tarea, prioridad_tarea) VALUES(?,?,?,?,?,?)";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setInt(1, idProyectoAsociado);
        sentencia.setString(2,nombre);
        sentencia.setString(3, descripcion);
        sentencia.setString(4, estado);
        sentencia.setInt(5, puntos);
        sentencia.setString(6,prioridad);
        
        sentencia.executeUpdate();
    }
    

    public void getAllTareas(Connection conn, DefaultListModel listModelTareas) throws SQLException {
        
        String consulta = "SELECT * FROM Tareas";
        Statement sentencia = conn.createStatement();
        
        ResultSet rs = sentencia.executeQuery(consulta);
        
        while(rs.next()){
            Tareas tarea = new Tareas(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getString(7)
            );
            
            listModelTareas.addElement(tarea);
        }
    
    }

    public void borrarTarea(Connection conn, Integer id_tarea) throws SQLException {
        String consulta = "DELETE FROM Tareas WHERE id_tarea=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setInt(1, id_tarea);
        sentencia.executeUpdate();
    
    }

    public Tareas tareaById(Connection conn, Integer id_tarea) throws SQLException {
        Tareas tarea = null;
        String consulta = "SELECT * FROM Tareas WHERE id_tarea=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, id_tarea); 
        ResultSet rs = sentencia.executeQuery();

        if(rs.next()){
            tarea = new Tareas(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getString(7)
            );
                return tarea;
        }
        return null;
    }

    public void actualizarTarea(Connection conn, Tareas tarea) throws SQLException {
        String consulta = "UPDATE Tareas SET idproyecto=?,nombre_tarea=?,descripcion=?,estado=?,puntos_tarea=?,prioridad_tarea=? WHERE id_tarea=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setInt(1, tarea.getProyecto());
        sentencia.setString(2, tarea.getNombre_tarea());
        sentencia.setString(3, tarea.getDescripcion());
        sentencia.setString(4, tarea.getEstado());
        sentencia.setInt(5, tarea.getPuntos_tarea());
        sentencia.setString(6,tarea.getPrioridad_tarea());
        sentencia.setInt(7,tarea.getId_tarea());
        
        sentencia.executeUpdate();
    
    }


    
    
    
    
    
    
    
}
