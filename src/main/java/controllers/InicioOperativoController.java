/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import clases.Incidente;
import controllers.AppController;
import luisalejos.reporteincidente.AppModel;
import clases.Personal;
import clases.PersonalOperativo;
import java.util.List;
import vistas.ReporteIncidentePanel;
import vistas.VistaInicioOperativo;
import vistas.VistaLogin;


public class InicioOperativoController {
    
    private AppModel modelo;
    private VistaInicioOperativo vista;
    private ReporteIncidentePanel reporte;
    private AppController appController;

    public InicioOperativoController(AppModel modelo, VistaInicioOperativo vista,ReporteIncidentePanel reporte, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.reporte = reporte;
        this.appController = appController;
        
        this.cargarDatos();

        this.vista.addLogoutListener(e -> cerrarSesion());
        this.vista.addVerIncidentesListener(e -> verIncidentes());
        this.vista.addReportarIncidenteListener(e -> mostrarReporte());
       
    }

    public void cargarDatos() {
        
        Personal usuario = modelo.getUsuarioLogueado();
        if (usuario != null) {
            vista.setSaludo(usuario.getNombre());
        }
    }
    
   public void mostrarReporte(){
        appController.mostrarReporte();
    }
   
   
    
    private void verIncidentes() {
      
        PersonalOperativo po = (PersonalOperativo) modelo.getUsuarioLogueado();
        
        List<Incidente> incidentes = po.getIncidenteReportados();
        
        for (Incidente in : incidentes) {
            System.out.println(in.getClass());
        }
        
        if (incidentes != null) {
            
            appController.mostrarListaIncidentes();
            
        } else {
           
            System.out.println("error al consultar incidentes");
        }
        
    }

      
    private void cerrarSesion() {
        modelo.cerrarSesion();
        appController.mostrarLogin();
        
    }
    
    
}
