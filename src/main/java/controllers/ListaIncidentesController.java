/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import clases.Incidente;
import clases.Personal;
import controllers.AppController;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import luisalejos.reporteincidente.AppModel;
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

        
        this.cargarDatos();
        
        this.vista.AddVolverListener(e-> verInicioOperativo());
        
        
    }

    public void cargarDatos() {
        
        List<Incidente> incidentes = modelo.getIncidentes();
        if (incidentes != null) {
            System.out.println("se encontraron incidentes");
            List<List <String>> incidentesDatos = new ArrayList<>();
            for(Incidente i : incidentes) {
                List <String> in = new ArrayList<>();
                
                in.add(i.getId());
                
                in.add("tecnico");
                in.add(i.getFecha().toString());
                
                
                in.add(i.getEstado());
                in.add(i.getReportadoPor().getDniPersonal());
                in.add(i.getAsignadoA().getDniPersonal());
                
                in.add(i.getPrioridad());
                       
                
                incidentesDatos.add(in);
            }
            vista.crearListaIncidentes(incidentesDatos);
        }
        
    }
    
    public void verInicioOperativo() {
        appController.mostrarInicioOperativo();
    }
    
    
    
    
}
