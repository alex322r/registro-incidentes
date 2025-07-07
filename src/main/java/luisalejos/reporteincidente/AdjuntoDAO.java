/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;

import data.BaseDeDatos;
import clases.Adjunto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alexis
 */
public class AdjuntoDAO {
    
    public Adjunto obtenerPorId(int adjuntoId) throws SQLException {
        
        String sql = "SELECT id, nombre, ruta from adjunto WHERE id = ?";
        
        try(Connection conn = BaseDeDatos.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, adjuntoId);
            
            try(ResultSet rs = pstmt.executeQuery()) {
                
                if(rs.next()) {
                    Adjunto a = new Adjunto();
                    a.setId(String.valueOf(rs.getInt("id")));
                    a.setNombre(rs.getString("nombre"));
                    a.setRuta(rs.getString("ruta"));
                    return a;
                } else {
                    return null;
                }
                
                
            }
            
        }
        
    }   
    
    public static void main(String[] args) throws SQLException {
        System.out.println(new AdjuntoDAO().obtenerPorId(7).getRuta());
    }
}
