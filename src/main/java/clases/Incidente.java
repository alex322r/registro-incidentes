/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import clases.Adjunto;
import enums.EstadoIncidente;
import enums.Prioridad;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexis
 */
public abstract class Incidente {
    
    private String id;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    private String descripcion;
    private Timestamp fecha;
    private EstadoIncidente estado;
    private PersonalOperativo reportadoPor; 
    private PersonalSeguridad asignadoA;   
    private Prioridad prioridad;
    private Adjunto adjunto;
    private ArrayList<HistorialEscalamiento> historialDeEscalamientos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    public ArrayList<HistorialEscalamiento> getHistorialDeEscalamientos() {
        return historialDeEscalamientos;
    }

    public void setHistorialDeEscalamientos(ArrayList<HistorialEscalamiento> historialDeEscalamientos) {
        this.historialDeEscalamientos = historialDeEscalamientos;
    }
    
   

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public EstadoIncidente getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidente estado) {
        this.estado = estado;
    }

    public PersonalOperativo getReportadoPor() {
        return reportadoPor;
    }

    public void setReportadoPor(PersonalOperativo reportadoPor) {
        this.reportadoPor = reportadoPor;
    }

    public PersonalSeguridad getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(PersonalSeguridad asignadoA) {
        this.asignadoA = asignadoA;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Incidente{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", fecha=" + fecha.toLocalDateTime() + ", estado=" + estado + ", reportadoPor=" + reportadoPor.getDniPersonal() + ", asignadoA="  + ", prioridad=" + prioridad + ", adjunto=" + adjunto.getRuta()  + '}';
    }
    
    
    
    
}
