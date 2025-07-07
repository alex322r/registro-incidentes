package luisalejos.reporteincidente;

import vistas.VistaInicioOperativo;
import vistas.VistaListaIncidentes;
import vistas.VistaLogin;
import controllers.LoginController;
import controllers.InicioOperativoController;
import controllers.AppController;
import controllers.DetalleIncidenteController;
import controllers.IncidenteInstalacionController;
import controllers.IncidenteSeguridadController;
import controllers.IncidenteTecnicoController;
import controllers.ListaIncidentesController;
import controllers.ReporteIncidenteController;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vistas.ReporteIncidentePanel;
import vistas.VistaIncidenteInstalacion;
import vistas.VistaIncidenteSeguridad;
import vistas.VistaDetalleIncidente;
import vistas.VistaIncidenteTecnico;

public class Main {
    public static void main(String[] args) {
        
       AppModel modelo = new AppModel();
       
       VistaLogin vistaLogin = new VistaLogin();
       VistaInicioOperativo vistaInicioOperativo = new VistaInicioOperativo();
       VistaListaIncidentes vistaListaIncidentes = new VistaListaIncidentes();
       ReporteIncidentePanel Reporte = new ReporteIncidentePanel();
       VistaIncidenteInstalacion vistaIncidenteInstalacion = new VistaIncidenteInstalacion();
       VistaIncidenteSeguridad Seguridad = new VistaIncidenteSeguridad ();
       VistaIncidenteTecnico vistaIncidenteTecnico = new VistaIncidenteTecnico();
       VistaDetalleIncidente vistaDetalleIncidente = new VistaDetalleIncidente();
       
        CardLayout cardLayout = new CardLayout();
        JPanel contenedorVistas = new JPanel(cardLayout);
        contenedorVistas.add(vistaLogin, "login");
        contenedorVistas.add(vistaInicioOperativo, "inicioOperativo");
        contenedorVistas.add(vistaListaIncidentes, "listaIncidentes");
        contenedorVistas.add(Reporte,"reporte" );
        contenedorVistas.add(vistaDetalleIncidente, "detalleIncidente");
        
        
        contenedorVistas.add(vistaIncidenteInstalacion, "instalacion");
        contenedorVistas.add(Seguridad, "seguridad");
        contenedorVistas.add(vistaIncidenteTecnico, "tecnico");
       
       
        AppController appController = new AppController(contenedorVistas, cardLayout);
       
        new LoginController(modelo, vistaLogin, appController);
       
        appController.setInicioOperativoController(new InicioOperativoController(modelo, vistaInicioOperativo,Reporte, appController));
        appController.setListaIncidentesController(new ListaIncidentesController(modelo, vistaListaIncidentes, appController) );
        appController.setReporteIncidenteController(new ReporteIncidenteController(modelo,Reporte, appController));
        appController.setIncidenteInstalacionController(new IncidenteInstalacionController(modelo, vistaIncidenteInstalacion,appController));
        appController.setIncidenteSeguridadController(new IncidenteSeguridadController(modelo,Seguridad,appController));
        appController.setIncidenteTecnicoController(new IncidenteTecnicoController(modelo, vistaIncidenteTecnico,appController));

        appController.setDetalleIncidenteController(new DetalleIncidenteController(modelo, vistaDetalleIncidente, appController));
        
        
        
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
