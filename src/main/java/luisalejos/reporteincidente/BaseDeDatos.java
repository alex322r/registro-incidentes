package luisalejos.reporteincidente;

import java.sql.*;
import java.util.Properties;

/**
 *
 * @author alexis
 */
public class BaseDeDatos {
    
    
        private static BaseDeDatos instancia;
        private Connection conexion;
        private String url;
        private Properties props;
        
        private BaseDeDatos() {
            
            
        }
        
       
        public static synchronized BaseDeDatos getInstance() {
            if(instancia == null) {
                instancia = new BaseDeDatos();
            }
            return instancia;
        }
        
        public Connection getConexion() throws SQLException {
            
            url = "jdbc:postgresql://172.203.146.248/registro_incidentes_db";
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