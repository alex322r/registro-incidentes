/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;

import clases.Adjunto;
import clases.IncidenteTecnico;
import clases.Incidente;
import clases.Personal;
import clases.PersonalOperativo;
import clases.PersonalSeguridad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class IncidenteDAO {

    public void crearIncidente(Incidente incidente) throws SQLException {
        
       
        String sqlIncidenteBase = "WITH nuevo_incidente AS (INSERT "
                + "INTO incidente (tipo, titulo, descripcion, estado,"
                + " reportado_por_dni, prioridad, adjunto_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id) ";
        
       
        String sqlIncidenteEspecifico = "";
        
        
        try (Connection conn = BaseDeDatos.getInstance().getConexion()) {

            conn.setAutoCommit(false);
            
             
            if (incidente instanceof IncidenteTecnico) {
            
                    sqlIncidenteEspecifico = "INSERT INTO incidente_tecnico "
                            + "(incidente_id, dispositivo_afectado, marca, modelo,"
                        + " numero_serie, ubicacion) SELECT id, ?, ?, ?, ?, ? "
                        + "FROM nuevo_incidente";
            }
            
            
            try (PreparedStatement pstmt = conn.prepareStatement(sqlIncidenteBase+sqlIncidenteEspecifico)) {
               
               
                pstmt.setString(2, incidente.getTitulo());
                pstmt.setString(3, incidente.getDescripcion());
                pstmt.setString(4, incidente.getEstado());
                pstmt.setString(5, incidente.getReportadoPor().getDniPersonal());
                pstmt.setString(6, incidente.getPrioridad());
                pstmt.setString(7, incidente.getAdjunto().getId());
                
                if (incidente instanceof IncidenteTecnico it) {
                    pstmt.setString(1, "tecnico");
                    pstmt.setString(8, it.getDispositivoAfectado());
                    pstmt.setString(9, it.getMarca());
                    pstmt.setString(10, it.getModelo());
                    pstmt.setString(11, it.getNumeroSerie());
                    pstmt.setString(12, it.getUbicacion());
                }
                
                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Incidente creado exitosamente. Filas afectadas: " + filasAfectadas);
                
                // Si todo fue bien, ambas tablas fueron afectadas. Hacemos commit.
                conn.commit();
               
      
            } catch (SQLException e) {
                // Si algo sale mal, revertimos la transacción para no dejar datos inconsistentes.
                System.err.println("Error al crear incidente, revirtiendo transacción.");
                conn.rollback();
                // Relanzamos la excepción para que la capa superior la maneje.
                throw e;
            }
        }  
            
    }
    
    public List<Incidente> recuperarIncidentes() {
        
        List<Incidente> incidentes = new ArrayList<>();
        
        String sql = "SELECT id, tipo, titulo, descripcion, fecha, estado, "
                + "reportado_por_dni, asignado_a_dni, prioridad, adjunto_id"
                + " FROM incidente";
        
        
        try (Connection conn = BaseDeDatos.getInstance().getConexion();
                Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            
        
            while(rs.next()) {
                
                Incidente incidente = null;
                PersonalOperativo po = new PersonalOperativo();
                PersonalSeguridad ps = new PersonalSeguridad();
               
                Adjunto ad = new Adjunto();
                
                
                if("tecnico".equals(rs.getString("tipo"))) {
                    incidente = new IncidenteTecnico();
                }
                
                
                incidente.setId(rs.getString("id"));
        
                incidente.setTitulo(rs.getString("titulo"));
                incidente.setDescripcion(rs.getString("descripcion"));
                incidente.setFecha(rs.getTimestamp("fecha"));
                        
                incidente.setEstado(rs.getString("estado"));
                po.setDniPersonal(rs.getString("reportado_por_dni"));
                incidente.setReportadoPor(po);
                ps.setDniPersonal(rs.getString("asignado_a_dni"));
                incidente.setAsignadoA(ps);
                incidente.setPrioridad(rs.getString("prioridad"));
                ad.setRuta("./imagenes/001.jpg");
                
                incidente.setAdjunto(ad);
               
              
                incidentes.add(incidente);
                
            }
            
            
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return incidentes;
    } 
    
    public static void main(String[] args) throws SQLException {
        
        
        PersonalOperativo ps = new PersonalOperativo();
        ps.setDniPersonal("73576762");
        
        Adjunto ad = new Adjunto();
        ad.setRuta("./imagenes/001.jpg");
        
        IncidenteTecnico it = new IncidenteTecnico();
        it.setTitulo("La computadora no enciende");
        it.setDescripcion("Esta viendo videos de gatitos y la computadora de repende dejo de funcionar");
        it.setEstado("abierto");
        it.setReportadoPor(ps);
        it.setPrioridad("baja");
        it.setAdjunto(ad);
        it.setDispositivoAfectado("PC");
        it.setMarca("HP");
        it.setNumeroSerie("32001");
        it.setUbicacion("aula 302-a");
        
        new IncidenteDAO().crearIncidente(it);
        
        
        List<Incidente> incidentes = new IncidenteDAO().recuperarIncidentes();
        for(Incidente i : incidentes) {
            System.out.println(i.toString());
        }
    }
}
