/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;

import clases.Incidente;
import clases.Personal;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alexis
 */

public class AppModel {
    
    
    private Personal personalLogueado;
    private List<Incidente> incidentes;
    private Incidente incidenteSeleccionado;
    
    
    

    public Personal getUsuarioLogueado() {
        return personalLogueado;
    }

    public void setUsuarioLogueado(Personal usuarioLogueado) {
        this.personalLogueado = usuarioLogueado;
    }
    
    public List<Incidente> getIncidentes() {
        return incidentes;
    }
    
    public void setIncidentes(List<Incidente> incidentes) {
        this.incidentes = incidentes;
    }
    
    public void setIncidenteSeleccionado(Incidente incidente) {
        this.incidenteSeleccionado = incidente;
    }
    
    public Incidente getIncidenteSeleccionado() {
        return incidenteSeleccionado;
    }

  
    public boolean autenticar(String user, String pass) {
        AuthService authService = new AuthService();
        if (authService.verificarCredenciales(user, pass)) {
            
            
            Personal usuario = new AuthService().obtenerDatosPersonalPorDni(user);
            
            if(usuario != null) {
                this.setUsuarioLogueado(usuario);
                return true;
            }
           
            
        }
        this.personalLogueado = null;
        return false;
    }
    
    
    
    public boolean recuperarIncidenteDetalle(int id) throws Exception {
        
        Incidente incidente = new IncidenteDAO().recuperarIncidenteById(id);
        
        if (incidente != null) {
           this.setIncidenteSeleccionado(incidente);
           return true;
        }
       
        return false;
    }
    
    
    /*
    public boolean recuperarIncidentes() {
        
        List<Incidente> inc = new IncidenteDAO().recuperarIncidentes();
        
        if (!inc.isEmpty()) {
            this.setIncidentes(inc);
            return true;
        }
       
        this.incidentes = null;
        return false;
    }
    
    */
    
    /*
    public boolean recuperarIncidentesByDni(String dni) {
        
        List<Incidente> inc = new IncidenteDAO().recuperarIncidentesPorReportante(dni);
        
        if (!inc.isEmpty()) {
            this.setIncidentes(inc);
            return true;
        }
       
        this.incidentes = null;
        return false;
    }
    */
    
    
  
    

    public void cerrarSesion() {
        this.personalLogueado = null;
        
    }
    
    
    
}