
package controllers;

import luisalejos.reporteincidente.AppModel;
import vistas.IncidenteInstalacion;
import vistas.IncidenteSeguridad;
import vistas.VistaIncidenteTecnico;
import vistas.ReporteIncidentePanel;

public class ReporteIncidenteController {
    private AppModel modelo;
    private ReporteIncidentePanel vista;
    private IncidenteInstalacion instalacion;
    private IncidenteSeguridad seguridad;
    private VistaIncidenteTecnico tecnico;
    private AppController appController;

    public ReporteIncidenteController(AppModel modelo, ReporteIncidentePanel vista,IncidenteInstalacion instalacion, IncidenteSeguridad Seguridad, VistaIncidenteTecnico tecnico, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.instalacion = instalacion;
        this.seguridad = seguridad;
        this.tecnico = tecnico;
        this.appController = appController;
        
        
        this.vista.addbtnInstalacion(e -> mostrarInstalacion());
        this.vista.addbtnSeguridad(e -> mostrarSeguridad());
        this.vista.addbtnTecnico(e -> mostrarTecnico());
    }

    public void mostrarInstalacion(){
        appController.mostrarInstalacion();
    }
    
    public void mostrarSeguridad(){
        appController.mostrarSeguridad();
    }
    
    public void mostrarTecnico(){
        appController.mostrarTecnico();
    }
}

