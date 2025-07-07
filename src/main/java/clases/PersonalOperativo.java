/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import clases.Personal;
import java.sql.SQLException;
import java.util.List;
import luisalejos.reporteincidente.IncidenteDAO;

/**
 *
 * @author alexis
 */
public class PersonalOperativo extends Personal {
    
    
    public List<Incidente> getIncidenteReportados() {
        return new IncidenteDAO().recuperarIncidentesPorReportante(this.dniPersonal);
    }
    
    public boolean reportarIncidente(Incidente incidente) throws SQLException {
        
        return new IncidenteDAO().crearIncidente(incidente);
            
    }   
    
}
