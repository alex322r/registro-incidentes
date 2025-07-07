
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
import enums.EstadoIncidente;
import enums.Prioridad;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import luisalejos.reporteincidente.IncidenteDAO;

public class IncidenteTecnicoController {
    private AppModel modelo;
    private VistaIncidenteTecnico vista;
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
        if(this.vista.verificarCampos().isEmpty()) {
            
            IncidenteTecnico it = new IncidenteTecnico();
            
            Personal p = modelo.getUsuarioLogueado();
            
            Adjunto ad = new Adjunto();
            
            ad.setNombre("adjunto.jpg");
            ad.setRuta("/imagenes/");
             
            HashMap datos = this.vista.getCamposIncidenteTecnico();
            
            it.setAdjunto(ad);
            it.setReportadoPor((PersonalOperativo) p);
            it.setTitulo(datos.get("titulo").toString());
            it.setDescripcion(datos.get("descripcion").toString());
         
            it.setDispositivoAfectado(datos.get("dispositivo").toString());
            
            Date fecha = (Date) datos.get("fecha");
            
            Date hora = (Date) datos.get("hora");
           
            Calendar calFecha = Calendar.getInstance();
            calFecha.setTime(fecha);

            Calendar calHora = Calendar.getInstance();
            calHora.setTime(hora);
            
            calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
            calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
            calFecha.set(Calendar.SECOND, 0);
            calFecha.set(Calendar.MILLISECOND, 0);

            
            Timestamp timestamp = new Timestamp(calFecha.getTimeInMillis());
            
            
            it.setFecha(timestamp);
            
            
            it.setMarca(datos.get("marca").toString());
            it.setNumeroSerie(datos.get("serie").toString());
            it.setUbicacion(datos.get("ubicacion").toString());
            it.setModelo(datos.get("modelo").toString());
            
            
            
            boolean exito = false;
            try {
                
                PersonalOperativo po = (PersonalOperativo) modelo.getUsuarioLogueado();
                
                exito = po.reportarIncidente(it);
                
            } catch (SQLException ex) {
                
                this.vista.mostrarErrorBaseDeDatos();
            }
            if (exito) {
                this.vista.mostrarExito("Se envio el incidente correctamente");
                this.vista.resetCampos();
            }
            
            
        } else {
            this.vista.mostrarError();
        }
    }
    
    
    public void mostrarReporte(){
        appController.mostrarReporte();
    }
    public void mostrarInicioOperativo(){
        appController.mostrarInicioOperativo();
    }
}
