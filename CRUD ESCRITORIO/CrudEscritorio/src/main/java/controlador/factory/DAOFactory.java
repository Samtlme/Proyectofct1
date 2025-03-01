package controlador.factory;

import java.sql.Connection;
import modelo.dao.ProyectosDAO;
import modelo.dao.TareasDAO;
import modelo.dao.UsuariosDAO;


public abstract class DAOFactory {
	// List of DAO types supported by the factory
	  public static final int MYSQL = 1;	
	
	  public static DAOFactory getDAOFactory(
	      int whichFactory) {	  
	    switch (whichFactory) {
	      case MYSQL: 
	          return new MySQLDAOFactory();
	      default           : 
	          return null;
	    }
	  }
        public abstract Connection getConnection() throws Exception ;
        
	public boolean releaseConnection(Connection connection) {
		// TODO Auto-generated method stub
		return false;
	}
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub		
	}
        
        public abstract TareasDAO getTareasDAO();
        public abstract UsuariosDAO getUsuariosDAO();
        public abstract ProyectosDAO getProyectosDAO();

}
