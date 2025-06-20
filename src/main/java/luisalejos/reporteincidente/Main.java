package luisalejos.reporteincidente;

import vistas.VistaInicioOperativo;
import vistas.VistaListaIncidentes;
import vistas.VistaLogin;
import controllers.LoginController;
import controllers.InicioOperativoController;
import controllers.AppController;
import controllers.ListaIncidentesController;
import java.awt.CardLayout;
import java.security.CryptoPrimitive;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexis
 */
public class Main {
    public static void main(String[] args) {
        
       AppModel modelo = new AppModel();
       
       VistaLogin vistaLogin = new VistaLogin();
       VistaInicioOperativo vistaInicioOperativo = new VistaInicioOperativo();
       VistaListaIncidentes vistaListaIncidentes = new VistaListaIncidentes();
       
        CardLayout cardLayout = new CardLayout();
        JPanel contenedorVistas = new JPanel(cardLayout);
        contenedorVistas.add(vistaLogin, "login");
        contenedorVistas.add(vistaInicioOperativo, "inicioOperativo");
        contenedorVistas.add(vistaListaIncidentes, "listaIncidentes");
       
        AppController appController = new AppController(contenedorVistas, cardLayout);
       
        new LoginController(modelo, vistaLogin, appController);
       
        appController.setInicioOperativoController(new InicioOperativoController(modelo, vistaInicioOperativo, appController));
        appController.setListaIncidentesController(new ListaIncidentesController(modelo, vistaListaIncidentes, appController) );
    
    
        JFrame ventana = new JFrame("Registro de incidentes");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(contenedorVistas);
        ventana.pack(); // Ajusta el tamaño al contenido
        ventana.setLocationRelativeTo(null); // Centra la ventana

        // 7. Iniciar en la vista de login y hacer visible
        appController.mostrarLogin();
        ventana.setVisible(true);
        
    }
    
}
