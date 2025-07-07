package data;

import java.sql.*;
import java.util.Properties;

/**
 *
 * @author alexis
 */
public class BaseDeDatos {
    
     private static Connection conexion;
        private static String url;
        private static Properties props;
        
        
        
        public static Connection getConexion() throws SQLException {
            
            url = "jdbc:postgresql://localhost/registro_incidentes_db";
            props = new Properties();
            props.setProperty("user", "alexis");
            props.setProperty("password", "123456");
            
            return DriverManager.getConnection(url, props);
                
        
        }
        
        public void cerrarConexion() {
        if (this.conexion != null) {
            try {
                this.conexion.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
    
    
       
}