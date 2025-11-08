package co.edu.uniquindio.proyectofinal.sameday.model;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.observer.ObservadorRepartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.observer.SujetoRepartidor;


import java.util.ArrayList;
import java.util.List;

public class Repartidor implements SujetoRepartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoRepartidor estado;
    private String zonaCobertura;
    private List<Envio> enviosAsignados = new ArrayList<>();
    private final List<ObservadorRepartidor> observadores = new ArrayList<>();

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono,
                      EstadoRepartidor estado, String zonaCobertura) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.estado = estado;
        this.zonaCobertura = zonaCobertura;
    }

    public void setEstado(EstadoRepartidor nuevoEstado) {
        this.estado = nuevoEstado;
        notificarObservadores();
    }

    public String getIdRepartidor() { return idRepartidor; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public String getTelefono() { return telefono; }
    public EstadoRepartidor getEstado() { return estado; }
    public String getZonaCobertura() { return zonaCobertura; }
    public List<Envio> getEnviosAsignados() { return enviosAsignados; }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }

    public void setEnviosAsignados(List<Envio> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
    }

    @Override
    public void agregarObservador(ObservadorRepartidor observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(ObservadorRepartidor observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {
        for (ObservadorRepartidor o : observadores) {
            o.actualizarEstado(this);
        }
    }

    @Override
    public String toString() {
        return "Repartidor{" + idRepartidor + ", " + nombre + ", estado=" + estado + "}";
    }


}
