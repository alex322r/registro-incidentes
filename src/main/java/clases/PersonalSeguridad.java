/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import clases.Personal;
import enums.NivelEscalamiento;

/**
 *
 * @author alexis
 */
public class PersonalSeguridad extends Personal {
    
    private NivelEscalamiento nivelDeSoporte;

    public NivelEscalamiento getNivelDeSoporte() {
        return nivelDeSoporte;
    }

    public void setNivelDeSoporte(NivelEscalamiento nivelDeSoporte) {
        this.nivelDeSoporte = nivelDeSoporte;
    }
    
    
}
