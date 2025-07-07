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
public class IncidenteTecnico extends Incidente {
    
    private String dispositivoAfectado;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String ubicacion;

    public String getDispositivoAfectado() {
        return dispositivoAfectado;
    }

    public void setDispositivoAfectado(String dispositivoAfectado) {
        this.dispositivoAfectado = dispositivoAfectado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public void verificarGarantia() {
        
    }
     
    public void solicitarPiezaDeReemplazo() {
        
    }
    
    
    
}
