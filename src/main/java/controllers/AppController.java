/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.CardLayout;
import javax.swing.JPanel;
import vistas.ReporteIncidentePanel;


public class AppController {
    
    private JPanel contenedorVistas;
    private CardLayout cardLayout;
    private InicioOperativoController inicioOperativoController;
    private ListaIncidentesController listaIncidentesController;
    private ReporteIncidenteController reporteIncidenteController;
    private IncidenteInstalacionController incidenteInstalacionController;
    private IncidenteSeguridadController incidenteSeguridadController;
    private IncidenteTecnicoController incidenteTecnicoController;
    
    public void setInicioOperativoController(InicioOperativoController controller) {
        this.inicioOperativoController = controller;
    }
    
    public void setListaIncidentesController(ListaIncidentesController controller) {
        this.listaIncidentesController = controller;
    }
    
    public void setReporteIncidenteController(ReporteIncidenteController controller){
        this.reporteIncidenteController = controller;
    }
    
    public void setIncidenteInstalacionController(IncidenteInstalacionController controller){
        this.incidenteInstalacionController = controller;
    }
    
    public void setIncidenteSeguridadController(IncidenteSeguridadController controller){
        this.incidenteSeguridadController = controller;
    }
    
    public void setIncidenteTecnicoController (IncidenteTecnicoController controller){
        this.incidenteTecnicoController = controller;
    
    }
    
    public AppController(JPanel contenedor, CardLayout layout) {
        this.contenedorVistas = contenedor;
        this.cardLayout = layout;
       
    }

    public void mostrarLogin() {
        cardLayout.show(contenedorVistas, "login");
    }
    
    public void mostrarReporte(){
        cardLayout.show(contenedorVistas, "reporte");
    }
    
    public void mostrarInstalacion(){
        cardLayout.show(contenedorVistas, "instalacion");
    }
    
    public void mostrarSeguridad(){
        cardLayout.show(contenedorVistas,"seguridad");
    }
    
    public void mostrarTecnico(){
        cardLayout.show(contenedorVistas, "tecnico");
    }
    
    
    public void mostrarInicioOperativo() {
        
        if (inicioOperativoController != null) {
            inicioOperativoController.cargarDatos();
        }
        cardLayout.show(contenedorVistas, "inicioOperativo");
    }

    void mostrarListaIncidentes() {
        if (listaIncidentesController != null) {
            listaIncidentesController.cargarDatos();
        }
        cardLayout.show(contenedorVistas, "listaIncidentes");
    }
    
    
}
