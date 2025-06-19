/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import clases.Incidente;
import clases.Personal;
import controllers.AppController;
import java.util.List;
import luisalejos.reporteincidente.AppModel;
import vistas.VistaLogin;

/**
 *
 * @author alexis
 */
public class ListaIncidentesController {
    
    private AppModel modelo;
    private VistaLogin vista;
    private AppController appController; // Para poder navegar

    public ListaIncidentesController(AppModel modelo, VistaLogin vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;

        
        
    }

    public void cargarDatos() {
        
        List<Incidente> incidentes = modelo.getIncidentes();
        if (incidentes != null) {
            
        }
    }
    
    
    
    
}
