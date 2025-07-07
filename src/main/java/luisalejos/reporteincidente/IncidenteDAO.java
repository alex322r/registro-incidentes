/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;


import data.BaseDeDatos;
import clases.Adjunto;
import clases.IncidenteTecnico;
import clases.Incidente;
import clases.IncidenteInstalacion;
import clases.IncidenteSeguridad;
import clases.Personal;
import clases.PersonalOperativo;
import clases.PersonalSeguridad;
import enums.EstadoIncidente;
import enums.NivelDeImpacto;
import enums.Prioridad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class IncidenteDAO {
    
    private IncidenteTecnico recuperarIncidenteTecnico(Connection conn, int id) throws SQLException{

        String sql = "SELECT dispositivo_afectado, marca, modelo, numero_serie, "
                + "ubicacion FROM incidente_tecnico WHERE incidente_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    IncidenteTecnico it = new IncidenteTecnico();
                    it.setDispositivoAfectado(rs.getString("dispositivo_afectado"));
                    it.setMarca(rs.getString("marca"));
                    it.setModelo(rs.getString("modelo"));
                    it.setNumeroSerie(rs.getString("numero_serie"));
                    it.setUbicacion(rs.getString("ubicacion"));
                    return it;
                } else {
                    throw new SQLException();
                }
            }
            
        }
       
    }
    
     private IncidenteInstalacion recuperarIncidenteInstalaciones(Connection conn, int id) throws SQLException{

        String sql = "SELECT edificio, piso, numero"
                + " FROM incidente_instalaciones WHERE incidente_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    IncidenteInstalacion ii = new IncidenteInstalacion();
                    
                    ii.setEdificio(rs.getString("edificio"));
                    ii.setNumeroDeOficina(rs.getString("numero"));
                    ii.setPiso(rs.getString("piso"));
                    
                    return ii;
                } else {
                    throw new SQLException();
                }
            }
            
        }
       
    }
     
      private IncidenteSeguridad recuperarIncidenteSeguridad(Connection conn, int id) throws SQLException{

        String sql = "SELECT nivel_riesgo, lugar, causa"
                + " FROM incidente_seguridad WHERE incidente_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    IncidenteSeguridad is = new IncidenteSeguridad();
                    
                    is.setNivelDeImpacto(NivelDeImpacto.valueOf(rs.getString("nivel_riesgo")));
                    is.setLugar(rs.getString("lugar"));
                    is.setCausa(rs.getString("causa"));
                    
                    return is;
                } else {
                    throw new SQLException();
                }
            }
            
        }
       
    }
    
    public Incidente recuperarIncidenteById(int id) throws SQLException, Exception {
        
        String sql = "SELECT id, tipo, titulo, descripcion, fecha, estado, "
                + "reportado_por_dni, asignado_a_dni, prioridad, adjunto_id FROM"
                + " incidente WHERE id = ?";
        
        Incidente incidente = null;
        
        try(Connection conn = BaseDeDatos.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
           
            pstmt.setInt(1, id);
            
            try(ResultSet rs = pstmt.executeQuery()) {
                
                if(rs.next()) {
                    
                    String tipo = rs.getString("tipo");
                    
                    switch (tipo) {
                        case "tecnico":
                            incidente = recuperarIncidenteTecnico(conn, id);
                            break;
                        case "instalaciones":
                            
                            incidente = recuperarIncidenteInstalaciones(conn, id);
                            break;
                        case "seguridad":
                            incidente = recuperarIncidenteSeguridad(conn, id);
                            break;
                        default:
                            throw new AssertionError();
                    }
                    
                   
                    incidente.setId(rs.getString("id"));
                    incidente.setTitulo(rs.getString("titulo"));
                    incidente.setDescripcion(rs.getString("descripcion"));
                    EstadoIncidente estado = EstadoIncidente.valueOf(rs.getString("estado"));
                    incidente.setEstado(estado);
                    String prioridad = rs.getString("prioridad");
                    Prioridad pri = Prioridad.valueOf(prioridad);
                    incidente.setPrioridad(pri);
                    incidente.setFecha(rs.getTimestamp("fecha"));
                    
                    String psId = rs.getString("asignado_a_dni");
                    String poId = rs.getString("reportado_por_dni");
                    
                    PersonalDAO pd = new PersonalDAO();
                    
                    Personal ps = pd.obtenerPorId(psId);
                    Personal po = pd.obtenerPorId(poId);
                    
                    incidente.setAsignadoA((PersonalSeguridad) ps);
                    incidente.setReportadoPor((PersonalOperativo) po);
                    
                    int idAdjunto = rs.getInt("adjunto_id");
                    
                    incidente.setAdjunto(new AdjuntoDAO().obtenerPorId(idAdjunto));
                    
                    
                    
                    return incidente;
                    
                } else {
                    return null;
                }
                
            }
                    
        }
    }
    

    
    public boolean crearIncidente(Incidente incidente) throws SQLException {
        Connection conn = BaseDeDatos.getConexion();
        
        try {
            conn.setAutoCommit(false);

           
            Integer nuevoAdjuntoId = null; 
            
            if (incidente.getAdjunto() != null) {
               
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

           
            String sqlIncidente = "INSERT INTO incidente (tipo, titulo, descripcion, fecha,estado, reportado_por_dni, prioridad, adjunto_id) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
            int nuevoIncidenteId;

            try (PreparedStatement pstmtIncidente = conn.prepareStatement(sqlIncidente)) {
                if (incidente instanceof IncidenteTecnico) {
                    pstmtIncidente.setString(1, "tecnico");
                } else if (incidente instanceof IncidenteInstalacion) {
                    pstmtIncidente.setString(1, "instalaciones");
                } else if (incidente instanceof IncidenteSeguridad) {  
                    pstmtIncidente.setString(1, "seguridad");
                } else {
                    pstmtIncidente.setString(1, "general"); 
                }

                pstmtIncidente.setString(2, incidente.getTitulo());
                pstmtIncidente.setString(3, incidente.getDescripcion());
                pstmtIncidente.setTimestamp(4, incidente.getFecha());
                pstmtIncidente.setString(5, EstadoIncidente.ABIERTO.name());
                pstmtIncidente.setString(6, incidente.getReportadoPor().getDniPersonal());
                pstmtIncidente.setString(7, Prioridad.BAJA.name());
                
                
               
                if (nuevoAdjuntoId != null) {
                    pstmtIncidente.setInt(8, nuevoAdjuntoId);
                } else {
                    pstmtIncidente.setNull(8, Types.INTEGER);
                }


                ResultSet rs = pstmtIncidente.executeQuery();
                if (rs.next()) {
                    nuevoIncidenteId = rs.getInt(1);
                } else {
                    throw new SQLException("La creación del incidente falló no se obtuvo ID.");
                }
            }

            
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
            } else if (incidente instanceof IncidenteInstalacion ii) {
                String sqlInstalaciones = "INSERT INTO incidente_instalaciones (incidente_id, edificio, piso, numero) "
                                        + "VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmtInstalaciones = conn.prepareStatement(sqlInstalaciones)) {
                    pstmtInstalaciones.setInt(1, nuevoIncidenteId);
                    pstmtInstalaciones.setString(2, ii.getEdificio());
                    pstmtInstalaciones.setString(3, ii.getPiso());
                    pstmtInstalaciones.setString(4, ii.getNumeroDeOficina());
                    pstmtInstalaciones.executeUpdate();
                }
            } else if (incidente instanceof IncidenteSeguridad is) {
                String sqlSeguridad = "INSERT INTO incidente_seguridad (incidente_id, nivel_riesgo, lugar, causa) VALUES "
                        + "(?, ?, ?, ?)";
                
                try (PreparedStatement pstmtSeguridad = conn.prepareStatement(sqlSeguridad)) {
                    pstmtSeguridad.setInt(1, nuevoIncidenteId);
                    pstmtSeguridad.setString(2, is.getNivelDeImpacto().name());
                    pstmtSeguridad.setString(3, is.getLugar());
                    pstmtSeguridad.setString(4, is.getCausa());
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
                
                String tipoIncidente = rs.getString("tipo");
                if("tecnico".equals(tipoIncidente)) {
                    incidente = new IncidenteTecnico();
                } else if ("instalaciones".equals(tipoIncidente)) {
                    incidente = new IncidenteInstalacion();
                } else if ("seguridad".equals(tipoIncidente)) {
                    incidente = new IncidenteInstalacion();
                }
                
                
                incidente.setId(rs.getString("id"));
        
                incidente.setTitulo(rs.getString("titulo"));
                incidente.setDescripcion(rs.getString("descripcion"));
                incidente.setFecha(rs.getTimestamp("fecha"));
                
                EstadoIncidente estado = EstadoIncidente.valueOf(rs.getString("estado"));
                
                incidente.setEstado(estado);
                po.setDniPersonal(rs.getString("reportado_por_dni"));
                incidente.setReportadoPor(po);
                ps.setDniPersonal(rs.getString("asignado_a_dni"));
                incidente.setAsignadoA(ps);
                String prioridad = rs.getString("prioridad");
                Prioridad pri = Prioridad.valueOf(prioridad);
                incidente.setPrioridad(pri);
                ad.setRuta("./imagenes/001.jpg");
                
                incidente.setAdjunto(ad);
               
              
                incidentes.add(incidente);
                
            }
            
            
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return incidentes;
    } 
    
    /*
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
               
                EstadoIncidente estado = EstadoIncidente.valueOf(rs.getString("estado"));
                
                incidente.setEstado(estado);
                po.setDniPersonal(rs.getString("reportado_por_dni"));
                incidente.setReportadoPor(po);
                ps.setDniPersonal(rs.getString("asignado_a_dni"));
                incidente.setAsignadoA(ps);
                String prioridad = rs.getString("prioridad");
                Prioridad pri = Prioridad.valueOf(prioridad);
                incidente.setPrioridad(pri);
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
    
   */ 
    
    
     public List<Incidente> recuperarIncidentesPorReportante(String reportadoPorDni) {
        
         // este metodo es para recuperar todos los incidente
         // reportados por el personal operativo
         
        List<Incidente> incidentes = new ArrayList<>();
        
        
        String sql = "SELECT id, tipo, titulo, descripcion, fecha, estado, "
                   + "reportado_por_dni, asignado_a_dni, prioridad, adjunto_id"
                   + " FROM incidente WHERE reportado_por_dni = ?";
        
        
        try (Connection conn = BaseDeDatos.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            
            pstmt.setString(1, reportadoPorDni);
            
           
            try (ResultSet rs = pstmt.executeQuery()) {
            
                while(rs.next()) {
                    
                    Incidente incidente = null;
                    
                    String tipoIncidente = rs.getString("tipo");
                    
                    if("tecnico".equals(tipoIncidente)) {
                        incidente = new IncidenteTecnico();
                    } else if ("instalaciones".equals(tipoIncidente)) {
                        incidente = new IncidenteInstalacion();
                    } else if ("seguridad".equals(tipoIncidente)) {
                        incidente = new IncidenteSeguridad();
                    } 
                   
                    
                    if (incidente != null) {
                      
                        PersonalOperativo po = new PersonalOperativo();
                        PersonalSeguridad ps = new PersonalSeguridad();
                        Adjunto ad = new Adjunto();
                        ad.setRuta("/001.jpg");
                        
                        incidente.setAdjunto(ad);
                        incidente.setId(rs.getString("id"));
                        incidente.setTitulo(rs.getString("titulo"));
                        incidente.setDescripcion(rs.getString("descripcion"));
                        incidente.setFecha(rs.getTimestamp("fecha"));
                        
                        EstadoIncidente estado = EstadoIncidente.valueOf(rs.getString("estado"));
                        
                        incidente.setEstado(estado);
                        
                        po.setDniPersonal(rs.getString("reportado_por_dni"));
                        ps.setDniPersonal(rs.getString("asignado_a_dni"));
                        incidente.setReportadoPor(po);
                        incidente.setAsignadoA(ps);
                 
                        String prioridad = rs.getString("prioridad");
                        Prioridad pri = Prioridad.valueOf(prioridad);
                        
                        incidente.setPrioridad(pri);
                        
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
           
            e.printStackTrace();
        }
        
        return incidentes;
    }
    
    
    
    
    public static void main(String[] args) throws SQLException, Exception {
        
        /*
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
        
        */
//        System.out.println(new IncidenteDAO().recuperarIncidenteById(3).getAsignadoA().getNombre());
    }
}