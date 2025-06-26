
package controllers;

import luisalejos.reporteincidente.AppModel;
import vistas.IncidenteInstalacion;
import vistas.ReporteIncidentePanel;
import vistas.VistaInicioOperativo;

public class IncidenteInstalacionController {
    private AppModel modelo;
    private IncidenteInstalacion vista ;
    private ReporteIncidentePanel reporte;
    private VistaInicioOperativo inicio;
    private AppController appController;

    public IncidenteInstalacionController(AppModel modelo, IncidenteInstalacion vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.reporte = reporte;
        this.inicio = inicio;
        this.appController = appController;
        
        this.vista.addbtnRegresar(e -> mostrarReporte());
        this.vista.addbtnInicio(e ->mostrarInicioOperativo());
    }
       public void mostrarReporte(){
           appController.mostrarReporte();
    }
       public void mostrarInicioOperativo(){
           appController.mostrarInicioOperativo();
       }


}
