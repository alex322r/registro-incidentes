
package controllers;

import clases.Adjunto;
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
import luisalejos.reporteincidente.AppModel;
import clases.IncidenteInstalacion;
import vistas.VistaIncidenteInstalacion;


public class IncidenteInstalacionController {
    private AppModel modelo;
    private VistaIncidenteInstalacion vista ;
    private AppController appController;

    public IncidenteInstalacionController(AppModel modelo, VistaIncidenteInstalacion vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;
        
        this.vista.addbtnRegresar(e -> mostrarReporte());
        this.vista.addbtnInicio(e ->mostrarInicioOperativo());
        this.vista.addEnviarListener(e -> enviarIncidente());
    }
    
    public void mostrarReporte(){
           appController.mostrarReporte();
    }
    public void mostrarInicioOperativo(){
           appController.mostrarInicioOperativo();
    }
    
    public void enviarIncidente() {
        
        if(!this.vista.verificarCampos()) {
            
            IncidenteInstalacion ii = new IncidenteInstalacion();
            
            Personal p = modelo.getUsuarioLogueado();
            
            Adjunto ad = new Adjunto();
            
            ad.setNombre("adjunto.jpg");
            ad.setRuta("/imagenes/");
            
            // tipo, titulo, descripcion, estado,", " reportado_por_dni, prioridad, adjunto_id
            
            HashMap datos = this.vista.getCamposIncidenteInstalacion();
            
            ii.setAdjunto(ad);
            ii.setReportadoPor((PersonalOperativo) p);
            ii.setTitulo(datos.get("titulo").toString());
            ii.setDescripcion(datos.get("descripcion").toString());
           
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
            
            
            ii.setFecha(timestamp);
            
            ii.setEdificio(datos.get("edificio").toString());
            ii.setNumeroDeOficina(datos.get("numero").toString());
            ii.setPiso(datos.get("piso").toString());
            
            
            
            boolean exito = false;
            try {
                
                PersonalOperativo po = (PersonalOperativo) modelo.getUsuarioLogueado();
                
                exito = po.reportarIncidente(ii);
                
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


}
