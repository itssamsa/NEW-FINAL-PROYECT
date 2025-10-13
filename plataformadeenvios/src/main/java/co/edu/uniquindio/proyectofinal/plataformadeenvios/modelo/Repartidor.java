package co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.enums.Disponibilidad;

import java.util.ArrayList;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String telefono;
    private Disponibilidad disponibilidada;
    private ArrayList<String> zonas = new ArrayList<>();
    private ArrayList<Envio> enviosAsignados = new ArrayList<>();

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Disponibilidad getDisponibilidada() {
        return disponibilidada;
    }

    public void setDisponibilidada(Disponibilidad disponibilidada) {
        this.disponibilidada = disponibilidada;
    }

    public ArrayList<String> getZonas() {
        return zonas;
    }

    public void setZonas(ArrayList<String> zonas) {
        this.zonas = zonas;
    }

    public ArrayList<Envio> getEnviosAsignados() {
        return enviosAsignados;
    }

    public void setEnviosAsignados(ArrayList<Envio> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
    }
}