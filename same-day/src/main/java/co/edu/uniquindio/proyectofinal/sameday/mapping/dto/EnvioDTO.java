package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

import java.time.LocalDateTime;
import java.util.List;


public class EnvioDTO {
    private String idEnvio;
    private DireccionDTO origen;
    private DireccionDTO destino;
    private double peso;
    private double volumen;
    private double costo;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private String idUsuario;
    private String idRepartidor;
    private List<ServicioAdicional> serviciosAdicionales;

    public EnvioDTO() {}

    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public DireccionDTO getOrigen() { return origen; }
    public void setOrigen(DireccionDTO origen) { this.origen = origen; }
    public DireccionDTO getDestino() { return destino; }
    public void setDestino(DireccionDTO destino) { this.destino = destino; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getVolumen() { return volumen; }
    public void setVolumen(double volumen) { this.volumen = volumen; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }
    public void setServiciosAdicionales(List<ServicioAdicional> serviciosAdicionales) { this.serviciosAdicionales = serviciosAdicionales; }
}