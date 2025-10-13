package co.edu.uniquindio.proyectofinal.sameday.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private List<Direccion> direccionesFrecuentes;
    private List<String> metodosPago;

    public Usuario(String idUsuario, String nombreCompleto, String correo, String telefono,
                   List<Direccion> direccionesFrecuentes, List<String> metodosPago) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.direccionesFrecuentes = direccionesFrecuentes == null ? new ArrayList<>() : direccionesFrecuentes;
        this.metodosPago = metodosPago == null ? new ArrayList<>() : metodosPago;
    }

    public String getIdUsuario() { return idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<Direccion> getDireccionesFrecuentes() { return direccionesFrecuentes; }
    public void setDireccionesFrecuentes(List<Direccion> direccionesFrecuentes) { this.direccionesFrecuentes = direccionesFrecuentes; }
    public List<String> getMetodosPago() { return metodosPago; }
    public void setMetodosPago(List<String> metodosPago) { this.metodosPago = metodosPago; }

    @Override
    public String toString() {
        return nombreCompleto + " - " + correo;
    }
}