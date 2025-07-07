/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alexis
 */
public class ValidadorCamposIncidente {
    
    public static boolean validarMarca (String marca) {
        Pattern p = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\\\s]+$");
        Matcher m = p.matcher(marca);
        return m.matches();
    }
    
    
    
    public static boolean validarUbicacion (String marca) {
        Pattern p = Pattern.compile("^[A-Z]+-[0-9]+[A-Z]$");
        Matcher m = p.matcher(marca);
        return m.matches();
    }
    
    public static boolean validarPiso(String piso) {   
        Pattern p = Pattern.compile("^[1-9][0-9]?$");
        Matcher m = p.matcher(piso);
        return m.matches();        
    }
    
    public static boolean validarNumeroOficina(String numeroOficina) {
        Pattern p = Pattern.compile("^[1-9][0-9]?[0-9]?$");
        Matcher m = p.matcher(numeroOficina);
        return m.matches();
    }
    
}
