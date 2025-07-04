/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import clases.Incidente;
import clases.Personal;
import controllers.AppController;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import luisalejos.reporteincidente.AppModel;
import utils.GenerarEtiquetas;
import vistas.VistaListaIncidentes;


/**
 *
 * @author alexis
 */
public class ListaIncidentesController {
    
    private AppModel modelo;
    private VistaListaIncidentes vista;
    private AppController appController; // Para poder navegar

    public ListaIncidentesController(AppModel modelo, VistaListaIncidentes vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;

        
        //this.cargarDatos();
        
        this.vista.AddVolverListener(e-> verInicioOperativo());
        this.vista.addFiltrosListener(e->filtrarIncidentes());
        
    }

    public void cargarDatos() {
        
        List<Incidente> incidentes = modelo.getIncidentes();
        if (incidentes != null) {
            
            List<List<String>> incidentesEtiquetas = GenerarEtiquetas
                    .generarIncidentes(incidentes);
            vista.crearListaIncidentes(incidentesEtiquetas, e -> {
                try {
                    verDetalleIncidente(e);
                } catch (Exception ex) {
                    System.getLogger(ListaIncidentesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            } );
        }
        
    }
    
    
    
    public void filtrarIncidentes() {
        List<String> filtros = vista.getFiltros();
        
        String estado = filtros.get(0);
        String prioridad = filtros.get(1);

        List<Incidente> todosLosIncidentes = modelo.getIncidentes();
        
        if (todosLosIncidentes == null) return;
        
        List<Incidente> incidentesFiltrados = todosLosIncidentes.stream()
                .filter(incidente -> {
                   
                    boolean estadoCoincidencia = estado.equalsIgnoreCase("todos") ||
                            incidente.getEstado().equalsIgnoreCase(estado);
                    
                    boolean  prioridadCoincidencia = prioridad.equalsIgnoreCase("todos") ||
                            incidente.getPrioridad().name().equalsIgnoreCase(prioridad);
                    
                    return estadoCoincidencia && prioridadCoincidencia;
                }).collect(Collectors.toList());
        
        if (incidentesFiltrados != null) {
            List <List<String>> etiquetas = GenerarEtiquetas.generarIncidentes(incidentesFiltrados);
            vista.crearListaIncidentes(etiquetas, e -> {
                try {
                    verDetalleIncidente(e);
                } catch (Exception ex) {
                    System.getLogger(ListaIncidentesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            });
            System.out.println("Filtrados: " + incidentesFiltrados.size());

        }
        
        
        
    }
    
    
    public void verInicioOperativo() {
        appController.mostrarInicioOperativo();
    }
    
    public void verDetalleIncidente(PropertyChangeEvent e) throws Exception {
        
        if(modelo.recuperarIncidenteDetalle(Integer.parseInt(e.getNewValue().toString()))) {
            appController.mostrarDetalleIncidente();
        }
        
        
    }
    
    
    
    
}
