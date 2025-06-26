/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import clases.Incidente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexis
 */
public class GenerarEtiquetas {
    
    public static List<List<String>> generarIncidentes(List<Incidente> incidentes) {
        
        List<List<String>> etiquetas = new ArrayList<>();
        
            for(Incidente i : incidentes) {
                List <String> in = new ArrayList<>();
                
                in.add(i.getId());
                
                in.add("tecnico");
                in.add(i.getFecha().toString());
                
                
                in.add(i.getEstado());
                in.add(i.getReportadoPor().getDniPersonal());
               
                
                in.add(i.getPrioridad());
                       
                
                etiquetas.add(in);
            }
        
        
        
        return etiquetas;
    }
    
}
