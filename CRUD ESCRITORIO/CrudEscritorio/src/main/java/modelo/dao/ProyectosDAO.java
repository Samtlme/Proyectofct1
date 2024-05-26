
package modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import modelo.vo.Proyectos;


public class ProyectosDAO {
     public void insetaProyecto(Connection conn, String nombre_proyecto, String descripcion, Date fecha_inicio, Date fecha_fin) throws SQLException {
        
        String consulta = "INSERT INTO proyectos (nombre_proyecto, descripcion, fecha_inicio, fecha_fin) VALUES(?,?,?,?)";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setString(1, nombre_proyecto);
        sentencia.setString(2, descripcion);
        sentencia.setDate(3, fecha_inicio);
        sentencia.setDate(4, fecha_fin);
        
        sentencia.executeUpdate();
    }
    

    public void getAllProyectos(Connection conn, DefaultListModel listModelProyectos) throws SQLException {
        
        String consulta = "SELECT * FROM Proyectos";
        Statement sentencia = conn.createStatement();
        
        ResultSet rs = sentencia.executeQuery(consulta);
        
        while(rs.next()){
            Proyectos proyecto = new Proyectos(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4),
                    rs.getDate(5)
            );
            
            listModelProyectos.addElement(proyecto);
        }
    
    }

    public void borrarProyecto(Connection conn, Integer id_proyecto) throws SQLException {
        String consulta = "DELETE FROM Proyectos WHERE idproyecto=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setInt(1, id_proyecto);
        sentencia.executeUpdate();
    
    }

    public Proyectos proyectoById(Connection conn, Integer id_proyecto) throws SQLException {
        Proyectos proyecto = null;
        String consulta = "SELECT * FROM proyectos WHERE idproyecto=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, id_proyecto); 
        ResultSet rs = sentencia.executeQuery();

        if(rs.next()){
            proyecto = new Proyectos(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4),
                    rs.getDate(5)
            );
                return proyecto;
        }
        return null;
    }

    public void actualizarProyecto(Connection conn, Proyectos proyecto) throws SQLException {
        String consulta = "UPDATE proyectos SET nombre_proyecto=?,descripcion=?,fecha_inicio=?,fecha_fin=? WHERE idproyecto=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setString(1, proyecto.getNombre_proyecto());
        sentencia.setString(2, proyecto.getDescripcion());
        sentencia.setDate(3, proyecto.getFecha_inicio());
        sentencia.setDate(4, proyecto.getFecha_fin());
        sentencia.setInt(5,proyecto.getId_proyecto());
        
        sentencia.executeUpdate();
    
    }

}
