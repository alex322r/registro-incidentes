/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import clases.Incidente;
import clases.IncidenteInstalacion;
import clases.IncidenteSeguridad;
import clases.IncidenteTecnico;
import clases.Personal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import luisalejos.reporteincidente.AppModel;
import vistas.VistaDetalleIncidente;

/**
 *
 * @author alexis
 */
public class DetalleIncidenteController {
    
    private AppModel modelo;
    private VistaDetalleIncidente vista;
    private AppController appcontroller;
    
    public DetalleIncidenteController(AppModel modelo, VistaDetalleIncidente vista, AppController appController) {
        
        this.modelo = modelo;
        this.vista = vista;
        this.appcontroller = appController;
        
        
        this.vista.addVolverListener(e-> mostrarLista());
    }
    
    public void cargarDatos() {
        
       if (modelo.getIncidenteSeleccionado() != null) {
           
           Incidente incidente = modelo.getIncidenteSeleccionado();
           if (incidente.getAsignadoA() != null) {
               
               vista.setEtiquetaAsignadoA(incidente.getAsignadoA().getNombre()+ " " +
                   incidente.getAsignadoA().getApellido());
               vista.setEtiquetaCorreo(incidente.getAsignadoA().getCorreo());
           }
           
           vista.setEtiquetaId(incidente.getId());
           vista.setTextDescripcion(incidente.getDescripcion());
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, hh:mm a", new Locale("es", "ES"));

           vista.setEtiquetaFecha(incidente.getFecha().toLocalDateTime().format(formatter));
           
           if (modelo.getIncidenteSeleccionado() instanceof IncidenteTecnico) {
               
               IncidenteTecnico it = (IncidenteTecnico) modelo.getIncidenteSeleccionado();
              
               vista.setEtiquetaTipo("Tecnico");
               
               String dispositivo = it.getDispositivoAfectado();
               String marca = it.getMarca();
               String modeloDispositivo = it.getModelo();
               String numero = it.getNumeroSerie();
               String ubicacion = it.getUbicacion();
               
               vista.setContenedorTipoTecnico(dispositivo, marca, modeloDispositivo, numero, ubicacion);
               
           } else if (modelo.getIncidenteSeleccionado() instanceof IncidenteInstalacion) {
               IncidenteInstalacion ii = (IncidenteInstalacion) modelo.getIncidenteSeleccionado();
               
               vista.setEtiquetaTipo("Instalaciones");
               
               String edifico = ii.getEdificio();
               String piso = ii.getPiso();
               String numero = ii.getNumeroDeOficina();
               String tipo = ii.getTitulo();
               
               vista.setContenedorTipoInstalaciones(edifico, numero, piso, tipo);
               
           } else if (modelo.getIncidenteSeleccionado() instanceof IncidenteSeguridad is) {
               
               vista.setEtiquetaTipo("Seguridad");
               
               String tipo = is.getTitulo();
               String nivel = is.getNivelDeImpacto().name();
               String lugar = is.getLugar();
               String causa = is.getCausa();
               vista.setContenedorTipoSeguridad(causa, nivel, tipo, lugar);
               
               
           }
           
       }
        
   
    }
    
    public void mostrarInicio() {
        this.appcontroller.mostrarInicioOperativo();
    }
    
    public void mostrarLista() {
        this.vista.limpiarTipoDeIncidente();
        this.appcontroller.mostrarListaIncidentes();
    }
    
    
    
    
}
