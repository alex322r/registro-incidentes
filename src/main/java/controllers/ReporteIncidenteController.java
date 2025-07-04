
package controllers;

import luisalejos.reporteincidente.AppModel;
import vistas.IncidenteInstalacion;
import vistas.IncidenteSeguridad;
import vistas.VistaIncidenteTecnico;
import vistas.ReporteIncidentePanel;

public class ReporteIncidenteController {
    private AppModel modelo;
    private ReporteIncidentePanel vista;
    private AppController appController;

    public ReporteIncidenteController(AppModel modelo, ReporteIncidentePanel vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;
        
        
        this.vista.addbtnInstalacion(e -> mostrarInstalacion());
        this.vista.addbtnSeguridad(e -> mostrarSeguridad());
        this.vista.addbtnTecnico(e -> mostrarTecnico());
        this.vista.addVolverListener(e -> mostrarInicioOperativo());
    }

    public void mostrarInicioOperativo() {
        appController.mostrarInicioOperativo();
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

