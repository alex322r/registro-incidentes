
package controllers;

import clases.Adjunto;
import clases.Incidente;
import luisalejos.reporteincidente.AppModel;
import vistas.VistaIncidenteTecnico;
import vistas.ReporteIncidentePanel;
import vistas.VistaInicioOperativo;
import clases.IncidenteTecnico;
import clases.Personal;
import clases.PersonalOperativo;
import java.sql.SQLException;
import java.util.HashMap;

public class IncidenteTecnicoController {
    private AppModel modelo;
    private VistaIncidenteTecnico vista;
    private ReporteIncidentePanel reporte; 
    private VistaInicioOperativo inicio;
    private AppController appController;

    public IncidenteTecnicoController(AppModel modelo, VistaIncidenteTecnico vista,  AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;

        this.vista.addbtnRegresar(e -> mostrarReporte());
        this.vista.addbtnInicio(e -> mostrarInicioOperativo());
        this.vista.addBotonEnviarListener(e -> enviarIncidente());
    } 
    
    public void enviarIncidente() {
        if(this.vista.verificarCampos()) {
            
            IncidenteTecnico it = new IncidenteTecnico();
            
            Personal p = modelo.getUsuarioLogueado();
            
            Adjunto ad = new Adjunto();
            
            ad.setNombre("adjunto.jpg");
            ad.setRuta("/imagenes/");
            
            // tipo, titulo, descripcion, estado,", " reportado_por_dni, prioridad, adjunto_id
            
            HashMap datos = this.vista.getCamposIncidenteTecnico();
            
            it.setAdjunto(ad);
            it.setReportadoPor((PersonalOperativo) p);
            it.setTitulo(datos.get("titulo").toString());
            it.setDescripcion(datos.get("descripcion").toString());
            it.setEstado("abierto");
            it.setPrioridad("baja");
            it.setDispositivoAfectado(datos.get("dispositivo").toString());
            
            // dispositivo_afectado, marca, modelo, numero_serie, ubicacion
            
            
            it.setMarca("s");
            it.setNumeroSerie("2112044");
            it.setUbicacion("aula302");
            
            
            
            boolean exito = false;
            try {
                exito = modelo.registrarIncidente(it);
            } catch (SQLException ex) {
                
                this.vista.mostrarError("Error conectandose al servidor");
            }
            if (exito) {
                this.vista.mostrarExito("Se envio el incidente correctamente");
            }
            
            
        } else {
            this.vista.mostrarError("Complete todos los campos");
        }
    }
    
    
    public void mostrarReporte(){
        appController.mostrarReporte();
    }
    public void mostrarInicioOperativo(){
        appController.mostrarInicioOperativo();
    }
}
