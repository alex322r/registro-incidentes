/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import clases.Incidente;
import clases.IncidenteTecnico;
import clases.Personal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import luisalejos.reporteincidente.AppModel;
import vistas.VistaDetalleIncidente;

/**
 *
 * @author alexis
 */
public class DetalleIncidenteController {
    
    private AppModel modelo;
    private VistaDetalleIncidente vista;
    private AppController appcontroller;
    
    public DetalleIncidenteController(AppModel modelo, VistaDetalleIncidente vista, AppController appController) {
        
        this.modelo = modelo;
        this.vista = vista;
        this.appcontroller = appController;
        
    }
    
    public void cargarDatos() {
        
       if (modelo.getIncidenteSeleccionado() != null) {
           
           Incidente incidente = modelo.getIncidenteSeleccionado();
           if (modelo.getIncidenteSeleccionado().getAsignadoA() != null) {
               vista.setEtiquetaAsignadoA(incidente.getAsignadoA().getNombre()+ " " +
                   incidente.getAsignadoA().getApellido());
               vista.setEtiquetaCorreo(incidente.getAsignadoA().getCorreo());
           }
           
           vista.setEtiquetaId(incidente.getId());
           vista.setTextDescripcion(incidente.getDescripcion());
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, hh:mm a", new Locale("es", "ES"));

           vista.setEtiquetaFecha(incidente.getFecha().toLocalDateTime().format(formatter));
           
           if (modelo.getIncidenteSeleccionado() instanceof IncidenteTecnico) {
               vista.setContenedorTipoTecnico();
           } 
           
       }
        
   
    }
    
    
    
    
    
    
}
