/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import clases.Incidente;
import enums.NivelDeImpacto;

/**
 *
 * @author alexis
 */
public class IncidenteSeguridad extends Incidente {
    
    private String tipoDeFalla;
    private NivelDeImpacto nivelDeImpacto;
    private String lugar;
    private String causa;

    public String getTipoDeFalla() {
        return tipoDeFalla;
    }

    public void setTipoDeFalla(String tipoDeFalla) {
        this.tipoDeFalla = tipoDeFalla;
    }

    public NivelDeImpacto getNivelDeImpacto() {
        return nivelDeImpacto;
    }

    public void setNivelDeImpacto(NivelDeImpacto nivelDeImpacto) {
        this.nivelDeImpacto = nivelDeImpacto;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
    
    
    public void aislarAreaAfectada() {
        
    }
    
    public void notificarEquipoDeRespuesta() {
        
    }
}
