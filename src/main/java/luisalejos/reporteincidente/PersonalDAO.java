/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;

import clases.Personal;
import clases.PersonalOperativo;
import clases.PersonalSeguridad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alexis
 */
public class PersonalDAO {
    
    public Personal obtenerPorId(String id) throws SQLException, Exception {
        
        String sql = "SELECT dni_personal, nombre, apellido, correo, rol FROM "
                + "personal WHERE dni_personal = ?";
        
        
        
        try (Connection conn = BaseDeDatos.getConexion();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String rol = rs.getString("rol");
                    Personal p;
                    if ("operativo".equals(rol)) {
                        p = new PersonalOperativo();
                      
                    } else if ("seguridad".equals(rol)) {
                        p = new PersonalSeguridad();
                        
                    } else {
                        throw new Exception("error roles");
                    }
                    p.setDniPersonal(rs.getString("dni_personal"));
                    p.setNombre(rs.getString("nombre"));
                    p.setApellido(rs.getString("apellido"));
                    p.setCorreo(rs.getString("correo"));
                    
                    return p;
                            
                } else {
                    return null;
                }
            }
        }
          
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(new  PersonalDAO().obtenerPorId("73576762").getNombre());
    }
    
}
