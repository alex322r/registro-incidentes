/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;


import clases.Adjunto;
import clases.IncidenteTecnico;
import clases.Incidente;
import clases.IncidenteInstalaciones;
import clases.IncidenteSeguridad;
import clases.Personal;
import clases.PersonalOperativo;
import clases.PersonalSeguridad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class IncidenteDAO {

    
    public boolean crearIncidente(Incidente incidente) throws SQLException {
        Connection conn = BaseDeDatos.getConexion();
        
        try {
            conn.setAutoCommit(false);

            // --- PASO 1: Insertar el Adjunto (si existe) y obtener su ID generado ---
            Integer nuevoAdjuntoId = null; // Usamos el wrapper Integer para poder asignarle null.
            
            if (incidente.getAdjunto() != null) {
                // No incluimos 'id' en la sentencia, la BD lo genera.
                String sqlAdjunto = "INSERT INTO Adjunto (nombre, ruta) VALUES (?, ?) RETURNING id";
                
                try (PreparedStatement pstmtAdjunto = conn.prepareStatement(sqlAdjunto)) {
                    pstmtAdjunto.setString(1, incidente.getAdjunto().getNombre());
                    pstmtAdjunto.setString(2, incidente.getAdjunto().getRuta());
                    
                    ResultSet rs = pstmtAdjunto.executeQuery();
                    if (rs.next()) {
                        nuevoAdjuntoId = rs.getInt(1);
                    } else {
                        throw new SQLException("La inserción del adjunto falló, no se obtuvo ID.");
                    }
                }
            }

            // --- PASO 2: Insertar el Incidente base y obtener su ID generado ---
            String sqlIncidente = "INSERT INTO incidente (tipo, titulo, descripcion, estado, reportado_por_dni, prioridad, adjunto_id) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
            int nuevoIncidenteId;

            try (PreparedStatement pstmtIncidente = conn.prepareStatement(sqlIncidente)) {
                if (incidente instanceof IncidenteTecnico) {
                    pstmtIncidente.setString(1, "tecnico");
                } else if (incidente instanceof IncidenteInstalaciones) {
                    pstmtIncidente.setString(1, "instalaciones");
                } else {
                    pstmtIncidente.setString(1, "general"); 
                }

                pstmtIncidente.setString(2, incidente.getTitulo());
                pstmtIncidente.setString(3, incidente.getDescripcion());
                pstmtIncidente.setString(4, incidente.getEstado());
                pstmtIncidente.setString(5, incidente.getReportadoPor().getDniPersonal());
                pstmtIncidente.setString(6, incidente.getPrioridad());
                
                // Manejar el caso de que no haya adjunto (ID nulo)
                if (nuevoAdjuntoId != null) {
                    pstmtIncidente.setInt(7, nuevoAdjuntoId);
                } else {
                    pstmtIncidente.setNull(7, Types.INTEGER);
                }

                ResultSet rs = pstmtIncidente.executeQuery();
                if (rs.next()) {
                    nuevoIncidenteId = rs.getInt(1);
                } else {
                    throw new SQLException("La creación del incidente falló, no se obtuvo ID.");
                }
            }

            // --- PASO 3: Insertar en la tabla específica ---
            if (incidente instanceof IncidenteTecnico it) {
                String sqlTecnico = "INSERT INTO incidente_tecnico (incidente_id, dispositivo_afectado, marca, modelo, numero_serie, ubicacion) "
                                  + "VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmtTecnico = conn.prepareStatement(sqlTecnico)) {
                    pstmtTecnico.setInt(1, nuevoIncidenteId);
                    pstmtTecnico.setString(2, it.getDispositivoAfectado());
                    pstmtTecnico.setString(3, it.getMarca());
                    pstmtTecnico.setString(4, it.getModelo());
                    pstmtTecnico.setString(5, it.getNumeroSerie());
                    pstmtTecnico.setString(6, it.getUbicacion());
                    pstmtTecnico.executeUpdate();
                }
            } else if (incidente instanceof IncidenteInstalaciones ii) {
                String sqlInstalaciones = "INSERT INTO incidente_instalaciones (incidente_id, edificio, piso, numero) "
                                        + "VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmtInstalaciones = conn.prepareStatement(sqlInstalaciones)) {
                    pstmtInstalaciones.setInt(1, nuevoIncidenteId);
                    pstmtInstalaciones.setString(2, ii.getEdificio());
                    pstmtInstalaciones.setString(3, ii.getPiso());
                    pstmtInstalaciones.setString(4, ii.getNumero());
                    pstmtInstalaciones.executeUpdate();
                }
            } else if (incidente instanceof IncidenteSeguridad is) {
                String sqlSeguridad = "";
                
                try (PreparedStatement pstmtSeguridad = conn.prepareStatement(sqlSeguridad)) {
                    pstmtSeguridad.executeUpdate();
                }
                
                
            }
            
            conn.commit();
            System.out.println("Incidente creado exitosamente. ID de incidente: " + nuevoIncidenteId);
            return true;
            

        } catch (SQLException e) {
            System.err.println("Error al crear incidente, revirtiendo transacción. Causa: " + e.getMessage());
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }
    
    
    public List<Incidente> recuperarIncidentes() {
        
        List<Incidente> incidentes = new ArrayList<>();
        
        String sql = "SELECT id, tipo, titulo, descripcion, fecha, estado, "
                + "reportado_por_dni, asignado_a_dni, prioridad, adjunto_id"
                + " FROM incidente";
        
        
        try (Connection conn = BaseDeDatos.getConexion();
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
    
    
    public List<Incidente> recuperarIncidentesByReportadoPor(String dni) {
        
        List<Incidente> incidentes = new ArrayList<>();
        
        String sql = "SELECT id, tipo, titulo, descripcion, fecha, estado, "
                + "reportado_por_dni, asignado_a_dni, prioridad, adjunto_id"
                + " FROM incidente WHERE reportado_por_dni = ? ";
        
        
        try (Connection conn = BaseDeDatos.getConexion();
                PreparedStatement statement = conn.prepareStatement(sql);) {
           
            
            
            statement.setString(1, dni);
            
            ResultSet rs = statement.executeQuery();
            
            
        
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
            
            statement.close();
            
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return incidentes;
    } 
    
    
    
    
     public List<Incidente> recuperarIncidentesPorReportante(String reportadoPorDni) {
        
         System.out.println(reportadoPorDni);
         
        List<Incidente> incidentes = new ArrayList<>();
        
        // 1. La consulta SQL ahora incluye una cláusula WHERE con un marcador de posición (?)
        String sql = "SELECT id, tipo, titulo, descripcion, fecha, estado, "
                   + "reportado_por_dni, asignado_a_dni, prioridad, adjunto_id"
                   + " FROM incidente WHERE reportado_por_dni = ?";
        
        // 2. Se utiliza un PreparedStatement dentro del try-with-resources
        try (Connection conn = BaseDeDatos.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // 3. Se establece el valor del parámetro en la consulta
            pstmt.setString(1, reportadoPorDni); // El '1' corresponde al primer '?' en el SQL
            
            // 4. Se ejecuta la consulta sin pasar el SQL de nuevo
            try (ResultSet rs = pstmt.executeQuery()) {
            
                while(rs.next()) {
                    
                    Incidente incidente = null;
                    // Lógica para crear la instancia correcta según el tipo
                    if("tecnico".equals(rs.getString("tipo"))) {
                        incidente = new IncidenteTecnico();
                    } else {
                        // Es buena práctica manejar otros tipos o tener un tipo por defecto
                        // Por ejemplo: incidente = new IncidenteGenerico();
                        // Si no, podría lanzar un NullPointerException en incidente.setId()
                        // Para este ejemplo, asumimos que siempre habrá un tipo válido.
                    }
                    
                    if (incidente != null) {
                      
                        PersonalOperativo po = new PersonalOperativo();
                        Adjunto ad = new Adjunto();
                        ad.setRuta("/001.jpg");
                        
                        incidente.setAdjunto(ad);
                        incidente.setId(rs.getString("id"));
                        incidente.setTitulo(rs.getString("titulo"));
                        incidente.setDescripcion(rs.getString("descripcion"));
                        incidente.setFecha(rs.getTimestamp("fecha"));
                        incidente.setEstado(rs.getString("estado"));
                        
                        po.setDniPersonal(rs.getString("reportado_por_dni"));
                        incidente.setReportadoPor(po);
                 
                        
                        incidente.setPrioridad(rs.getString("prioridad"));
                        
                     /*
                        String adjuntoId = rs.getString("adjunto_id");
                        if (!rs.wasNull()) { 
                             ad.setId(adjuntoId);
                             ad.setRuta("./imagenes/001.jpg"); 
                             incidente.setAdjunto(ad);
                        }
                       */ 
                        incidentes.add(incidente);
                    }
                }
            }
            
        } catch(SQLException e) {
            // Es una buena práctica registrar el error en un sistema de logging
            // en lugar de solo imprimirlo en la consola.
            e.printStackTrace();
        }
        
        return incidentes;
    }
    
    
    
    
    public static void main(String[] args) throws SQLException {
        
        
        PersonalOperativo ps = new PersonalOperativo();
        ps.setDniPersonal("71829423");
        
        Adjunto ad = new Adjunto();
        ad.setRuta("/001.jpg");
        ad.setNombre("adjunto");
        
        IncidenteTecnico it = new IncidenteTecnico();
        it.setTitulo("La computadora no enciende");
        it.setDescripcion("Esta viendo videos de gatitos y la computadora de repende dejo de funcionar");
        it.setEstado("abierto");
        it.setReportadoPor(ps);
        it.setPrioridad("media");
        it.setAdjunto(ad);
        it.setDispositivoAfectado("PC");
        it.setMarca("HP");
        it.setNumeroSerie("32001");
        it.setUbicacion("aula 302-a");
        
        new IncidenteDAO().crearIncidente(it);
        
        
        List<Incidente> incidentes = new IncidenteDAO().recuperarIncidentesPorReportante(ps.getDniPersonal());
        for(Incidente i : incidentes) {
            System.out.println(i.toString());
        }
    }
}