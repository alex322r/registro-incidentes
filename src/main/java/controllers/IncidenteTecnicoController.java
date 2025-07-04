
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
import enums.Prioridad;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
            
            // tipo, titulo, descripcion, estado,", " reportado_por_dni, prioridad, adjunto_id
            
            HashMap datos = this.vista.getCamposIncidenteTecnico();
            
            it.setAdjunto(ad);
            it.setReportadoPor((PersonalOperativo) p);
            it.setTitulo(datos.get("titulo").toString());
            it.setDescripcion(datos.get("descripcion").toString());
            it.setEstado("abierto");
            it.setPrioridad(Prioridad.BAJA);
            it.setDispositivoAfectado(datos.get("dispositivo").toString());
            
            Date fecha = (Date) datos.get("fecha");
            
            
          
            Date hora = (Date) datos.get("hora");

            // Usar Calendar para combinar fecha + hora
            Calendar calFecha = Calendar.getInstance();
            calFecha.setTime(fecha);

            Calendar calHora = Calendar.getInstance();
            calHora.setTime(hora);

            // Establecer hora en la fecha
            calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
            calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
            calFecha.set(Calendar.SECOND, 0);
            calFecha.set(Calendar.MILLISECOND, 0);

            // Convertir a Timestamp
            Timestamp timestamp = new Timestamp(calFecha.getTimeInMillis());
            
            
            it.setFecha(timestamp);
            
            
            //it.setFecha(fecha);
            
            // dispositivo_afectado, marca, modelo, numero_serie, ubicacion
            
            
            it.setMarca("s");
            it.setNumeroSerie("2112044");
            it.setUbicacion("aula302");
            
            
            
            boolean exito = false;
            try {
                exito = modelo.registrarIncidente(it);
            } catch (SQLException ex) {
                
                this.vista.mostrarErrorBaseDeDatos();
            }
            if (exito) {
                this.vista.mostrarExito("Se envio el incidente correctamente");
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
