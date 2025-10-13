package co.edu.uniquindio.proyectofinal.sameday.model;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;

import java.util.ArrayList;
import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoRepartidor estado;
    private String zonaCobertura;
    private List<Envio> enviosAsignados = new ArrayList<>();

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono,
                      EstadoRepartidor estado, String zonaCobertura) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.estado = estado;
        this.zonaCobertura = zonaCobertura;
    }

    public String getIdRepartidor() { return idRepartidor; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public String getTelefono() { return telefono; }
    public EstadoRepartidor getEstado() { return estado; }
    public void setEstado(EstadoRepartidor estado) { this.estado = estado; }
    public String getZonaCobertura() { return zonaCobertura; }
    public List<Envio> getEnviosAsignados() { return enviosAsignados; }

    @Override
    public String toString() {
        return "Repartidor{" + idRepartidor + ", " + nombre + "}";
    }
}