/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.AppController;
import luisalejos.reporteincidente.AppModel;
import clases.Personal;
import vistas.VistaInicioOperativo;

/**
 *
 * @author alexis
 */
public class InicioOperativoController {
    
    private AppModel modelo;
    private VistaInicioOperativo vista;
    private AppController appController;

    public InicioOperativoController(AppModel modelo, VistaInicioOperativo vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;
        
        this.cargarDatos();

        this.vista.addLogoutListener(e -> cerrarSesion());
        this.vista.addVerIncidentesListener(e -> verIncidentes());
    }

    public void cargarDatos() {
        
        Personal usuario = modelo.getUsuarioLogueado();
        if (usuario != null) {
            vista.setSaludo(usuario.getNombre());
        }
    }
    
    private void verIncidentes() {
      
        if (modelo.recuperarIncidentes()) {
            
            appController.mostrarListaIncidentes();
            
        } else {
            // Manejar error (en una app real)
            System.out.println("error al consultar incidentes");
        }
        
    }
    
    
    
    private void cerrarSesion() {
        modelo.cerrarSesion();
        appController.mostrarLogin();
    }
    
    
}
