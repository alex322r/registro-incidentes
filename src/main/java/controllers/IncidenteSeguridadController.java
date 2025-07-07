
package controllers;

import clases.Adjunto;
import clases.IncidenteSeguridad;
import clases.Personal;
import clases.PersonalOperativo;
import enums.NivelDeImpacto;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import luisalejos.reporteincidente.AppModel;
import vistas.VistaIncidenteSeguridad;

public class IncidenteSeguridadController {
    private AppModel modelo;
    private VistaIncidenteSeguridad vista ;
    private AppController appController;

    public IncidenteSeguridadController(AppModel modelo, VistaIncidenteSeguridad vista,  AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;
      
        this.vista.addbtnRegresar(e -> mostrarReporte());
        this.vista.addbtnInicio(e -> mostrarInicioOperativo());
        this.vista.addEnviar(e -> enviarIncidente());
    }
    
    
    public void enviarIncidente() {
        
        if(!this.vista.verificarCampos()) {
            HashMap campos = this.vista.getCampos();
            String causa = campos.get("causa").toString();
            String descripcion = campos.get("descripcion").toString();
            String tipo = campos.get("tipoIncidente").toString();
            String lugar = campos.get("lugar").toString();
            String nivelImpacto = campos.get("nivelImpacto").toString();
            
            IncidenteSeguridad is = new IncidenteSeguridad();
            is.setCausa(causa);
            is.setLugar(lugar);
            is.setNivelDeImpacto(NivelDeImpacto.valueOf(nivelImpacto));     
            is.setTitulo(tipo);
            is.setDescripcion(descripcion);
            PersonalOperativo p = (PersonalOperativo) modelo.getUsuarioLogueado();
            
            Adjunto ad = new Adjunto();
            
            ad.setNombre("adjunto.jpg");
            ad.setRuta("/imagenes/");
            
            is.setReportadoPor(p);
            is.setAdjunto(ad);
            
            
            
            Date fecha = (Date) campos.get("fecha");
            
            Date hora = (Date) campos.get("hora");
           
            Calendar calFecha = Calendar.getInstance();
            calFecha.setTime(fecha);

            Calendar calHora = Calendar.getInstance();
            calHora.setTime(hora);
            
            calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
            calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
            calFecha.set(Calendar.SECOND, 0);
            calFecha.set(Calendar.MILLISECOND, 0);

            
            Timestamp timestamp = new Timestamp(calFecha.getTimeInMillis());
            
            
            is.setFecha(timestamp);
            
            boolean exito = false;
            try {
                
                PersonalOperativo po = (PersonalOperativo) modelo.getUsuarioLogueado();
                
                exito = po.reportarIncidente(is);
                
            } catch (SQLException ex) {
                
                this.vista.mostrarErrorBaseDeDatos();
            }
            if (exito) {
                this.vista.mostrarExito();
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
