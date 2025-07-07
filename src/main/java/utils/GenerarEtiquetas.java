/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import clases.Incidente;
import clases.IncidenteInstalacion;
import clases.IncidenteSeguridad;
import clases.IncidenteTecnico;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexis
 */
public class GenerarEtiquetas {
    
    public static List<List<String>> generarIncidentes(List<Incidente> incidentes) {
        
        List<List<String>> etiquetas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
            for(Incidente incidente : incidentes) {
                List <String> in = new ArrayList<>();
                
                in.add(incidente.getId());
                if (incidente instanceof IncidenteTecnico) {
                    in.add("tecnico");
                } else if (incidente instanceof IncidenteInstalacion) {
                    in.add("instalaciones");
                } else if (incidente instanceof IncidenteSeguridad) {
                    in.add("seguridad");
                } else {
                    
                }
                
                LocalDateTime dateTime = incidente.getFecha().toLocalDateTime();    
                String fechaFormateada = dateTime.format(formatter);
                
                in.add(fechaFormateada);
                
                
                in.add(incidente.getEstado().name());
                in.add(incidente.getAsignadoA().getDniPersonal());
               
                
                in.add(incidente.getPrioridad().name());
                       
                
                etiquetas.add(in);
            }
        
        
        
        return etiquetas;
    }
    
}
