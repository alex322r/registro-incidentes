/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author alexis
 */
public class ValidadorFecha {
    
    public static boolean esFechaValida(String fecha) {
        // Define el formato esperado
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        // Evita que el formateador ajuste automáticamente fechas inválidas
        formato.setLenient(false);
        
        try {
            // Intenta parsear la fecha
            formato.parse(fecha);
            return true; // Es una fecha válida
        } catch (ParseException e) {
            return false; // No es una fecha válida
        }
    }
    
}
