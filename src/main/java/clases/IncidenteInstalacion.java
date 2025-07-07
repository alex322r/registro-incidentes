/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import clases.Incidente;

/**
 *
 * @author alexis
 */
public class IncidenteInstalacion extends Incidente {
    
    
    private String tipoDeProblema;
    private String edificio;
    private String piso;
    private String numeroDeOficina;
    

    public String getNumeroDeOficina() {
        return numeroDeOficina;
    }

    public void setNumeroDeOficina(String numeroDeOficina) {
        this.numeroDeOficina = numeroDeOficina;
    }

    public String getTipoDeProblema() {
        return tipoDeProblema;
    }

    public void setTipoDeProblema(String tipoDeProblema) {
        this.tipoDeProblema = tipoDeProblema;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

  
    
}
