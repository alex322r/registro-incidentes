/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author alexis
 */
public class AppController {
    
    private JPanel contenedorVistas;
    private CardLayout cardLayout;
    private InicioOperativoController inicioOperativoController;
    private ListaIncidentesController listaIncidentesController;
    
    
    public void setInicioOperativoController(InicioOperativoController controller) {
        this.inicioOperativoController = controller;
    }
    
    public void setListaIncidentesController(ListaIncidentesController controller) {
        this.listaIncidentesController = controller;
    }
    
    
    public AppController(JPanel contenedor, CardLayout layout) {
        this.contenedorVistas = contenedor;
        this.cardLayout = layout;
    }

    public void mostrarLogin() {
        cardLayout.show(contenedorVistas, "login");
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
