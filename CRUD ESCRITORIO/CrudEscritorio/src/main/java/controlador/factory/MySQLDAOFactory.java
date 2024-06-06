package controlador.factory;

import java.sql.Connection;
import java.sql.SQLException;
import controlador.pool.BasicConnectionPool;
import modelo.dao.ProyectosDAO;
import modelo.dao.TareasDAO;
import modelo.dao.UsuariosDAO;

public class MySQLDAOFactory extends DAOFactory {

    final static String user = "root";
    final static String password = "root";
    final static String BD = "proyectofct"; 
    final static String IP = "127.0.0.1";  
    final static String url = "jdbc:mysql://" + IP + ":3306/" + BD;

    static BasicConnectionPool bcp;


    public MySQLDAOFactory() {

        try {
            bcp = BasicConnectionPool.create(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        return bcp.getConnection();
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        return bcp.releaseConnection(connection);
    }

    @Override
    public int getSize() {
        return bcp.getSize();
    }
    //add getUser, getURL....

    @Override
    public void shutdown() throws SQLException {
        bcp.shutdown();
    }

    @Override
    public TareasDAO getTareasDAO() {
        return new TareasDAO();
    }

    @Override
    public UsuariosDAO getUsuariosDAO() {
        return new UsuariosDAO();
    }

    @Override
    public ProyectosDAO getProyectosDAO() {
        return new ProyectosDAO();
    }
   

}
