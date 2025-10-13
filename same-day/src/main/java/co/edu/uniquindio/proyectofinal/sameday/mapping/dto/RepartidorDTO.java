package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;

import java.util.List;

public class RepartidorDTO {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoRepartidor estado;
    private String zonaCobertura;
    private List<String> enviosAsignadosIds;

    public RepartidorDTO() {}

    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public EstadoRepartidor getEstado() { return estado; }
    public void setEstado(EstadoRepartidor estado) { this.estado = estado; }
    public String getZonaCobertura() { return zonaCobertura; }
    public void setZonaCobertura(String zonaCobertura) { this.zonaCobertura = zonaCobertura; }
    public List<String> getEnviosAsignadosIds() { return enviosAsignadosIds; }
    public void setEnviosAsignadosIds(List<String> enviosAsignadosIds) { this.enviosAsignadosIds = enviosAsignadosIds; }
}