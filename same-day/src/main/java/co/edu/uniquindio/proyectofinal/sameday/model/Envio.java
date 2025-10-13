package co.edu.uniquindio.proyectofinal.sameday.model;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double volumen;
    private double costo;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private Usuario usuario;
    private Repartidor repartidor;
    private List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

    public Envio(String idEnvio, Direccion origen, Direccion destino, double peso, double volumen,
                 Usuario usuario) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.volumen = volumen;
        this.usuario = usuario;
        this.estado = EstadoEnvio.SOLICITADO;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getIdEnvio() { return idEnvio; }
    public Direccion getOrigen() { return origen; }
    public Direccion getDestino() { return destino; }
    public double getPeso() { return peso; }
    public double getVolumen() { return volumen; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }
    public Usuario getUsuario() { return usuario; }
    public Repartidor getRepartidor() { return repartidor; }
    public void setRepartidor(Repartidor repartidor) { this.repartidor = repartidor; }
    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }
    public void addServicioAdicional(ServicioAdicional s) { this.serviciosAdicionales.add(s); }

    @Override
    public String toString() {
        return "Envio{" + idEnvio + ", estado=" + estado + "}";
    }
}
