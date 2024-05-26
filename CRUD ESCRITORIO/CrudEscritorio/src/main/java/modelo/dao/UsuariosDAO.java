
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import modelo.vo.Usuarios;


public class UsuariosDAO {
     public void insetaUsuario(Connection conn, String nombre_usuario, String contrasena, Integer puntos_totales, Integer rol) throws SQLException {
        
        String consulta = "INSERT INTO usuarios (nombre_usuario, contrasena, puntos_totales, rol) VALUES(?,?,?,?)";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setString(1, nombre_usuario);
        sentencia.setString(2,contrasena);
        sentencia.setInt(3,puntos_totales);
        sentencia.setInt(4,rol);
       
        sentencia.executeUpdate();
    }
    

    public void getAllUsuarios(Connection conn, DefaultListModel listModelUsuarios) throws SQLException {
        
        String consulta = "SELECT * FROM Usuarios";
        Statement sentencia = conn.createStatement();
        
        ResultSet rs = sentencia.executeQuery(consulta);
        
        while(rs.next()){
            Usuarios usuario = new Usuarios(rs.getInt(1), 
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5));
            
            listModelUsuarios.addElement(usuario);
        }
    
    }

    public void borrarUsuario(Connection conn, Integer id_usuario) throws SQLException {
        String consulta = "DELETE FROM usuarios WHERE id_usuario=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setInt(1, id_usuario);
        sentencia.executeUpdate();
    
    }

    public Usuarios usuarioById(Connection conn, Integer id_usuario) throws SQLException {
        Usuarios usuario = null;
        String consulta = "SELECT * FROM usuarios WHERE id_usuario=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, id_usuario); 
        ResultSet rs = sentencia.executeQuery();

        if(rs.next()){
             usuario = new Usuarios(rs.getInt(1), 
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5));
             
                return usuario;
        }
        return null;
    }

    public void actualizarUsuario(Connection conn, Usuarios usuario) throws SQLException {
        String consulta = "UPDATE usuarios SET nombre_usuario=?,contrasena=?,puntos_totales=?,rol=? WHERE id_usuario=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        
        sentencia.setString(1, usuario.getNombre_usuario());
        sentencia.setString(2, usuario.getContrasena());
        sentencia.setInt(3, usuario.getPuntos_totales());
        sentencia.setInt(4, usuario.getRol());
        sentencia.setInt(5,usuario.getId_usuario());
        
        sentencia.executeUpdate();
    
    }
}
