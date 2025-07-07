/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;

import data.BaseDeDatos;
import clases.PersonalSeguridad;
import clases.PersonalOperativo;
import clases.Personal;
import enums.NivelEscalamiento;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    public boolean registrarUsuario(String dniPersonal, String password, String nombre, String apellido) {
        // Genera el salt y hashea la contraseña
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO personal (dni_personal, password, nombre, apellido) VALUES (?, ?, ?, ?)";

        try (Connection conn = BaseDeDatos.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dniPersonal);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, nombre);
            pstmt.setString(4, apellido);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;

        } catch (SQLException e) {
            // Maneja errores de duplicados (código de error de PostgreSQL para unique_violation es '23505')
            if ("23505".equals(e.getSQLState())) {
                System.err.println("Error: El nombre de usuario o el email ya existen.");
            } else {
                System.err.println("Error de base de datos: " + e.getMessage());
            }
            return false;
        }
    }

    public boolean verificarCredenciales(String dniPersonal, String password) {
        String sql = "SELECT password FROM personal WHERE dni_personal = ?";
        
        
        
        try (Connection conn = BaseDeDatos.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dniPersonal);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String passwordHashGuardado = rs.getString("password");
                    // Compara la contraseña ingresada con el hash guardado
                    
                    return BCrypt.checkpw(password, passwordHashGuardado);
                } else {
                    // El usuario no existe
                    System.out.println("no existe");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            return false;
        }
    
    }
    
    public Personal obtenerDatosPersonalPorDni(String dniPersonal) {
       
       String sql = "SELECT nombre, apellido, nivel_soporte, rol FROM personal WHERE dni_personal = ?";
       
       Personal personal = null;
       
       try (Connection conn = BaseDeDatos.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           
           pstmt.setString(1, dniPersonal);
           
           
           try (ResultSet rs = pstmt.executeQuery()) {
               if(rs.next()) {
                   
                String rol = rs.getString("rol");
               
                if("seguridad".equals(rol)) {
                    PersonalSeguridad personalSeguridad = new PersonalSeguridad();
                    
                    NivelEscalamiento nivel = NivelEscalamiento.valueOf(rs.getString("nivel_soporte"));
                    personalSeguridad.setNivelDeSoporte(nivel);
                    
                    personal = personalSeguridad;
                   
                            
                } else if ("operativo".equals(rol)) {
                   personal = new PersonalOperativo();
                }
               
                personal.setDniPersonal(dniPersonal);
                personal.setNombre(rs.getString("nombre"));
                personal.setApellido(rs.getString("apellido"));
                
                //personal.setCorreo(rs.getString("correo"));
               
}   
           }
           
       } catch(SQLException e){
            System.err.println("Error al obtener datos del personal: " + e.getMessage());
        // Es buena idea registrar el error completo
            e.printStackTrace();
       }
       
       return personal;
       
    } 
    
 
    public static void main(String[] args) {
        new AuthService().registrarUsuario("73576762", "123456", "Alexis", "Rodriguez");
    }
   

        
  
    
}
