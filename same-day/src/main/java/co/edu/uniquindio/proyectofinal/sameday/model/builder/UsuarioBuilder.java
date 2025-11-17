package co.edu.uniquindio.proyectofinal.sameday.model.builder;

import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class UsuarioBuilder {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private List<Direccion> direccionesFrecuentes = new ArrayList<>();
    private List<String> metodosPago = new ArrayList<>();

    public UsuarioBuilder withId(String id) {
        this.idUsuario = id;
        return this;
    }

    public UsuarioBuilder withNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public UsuarioBuilder withCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public UsuarioBuilder withTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public UsuarioBuilder withDireccionesFrecuentes(List<Direccion> direcciones) {
        this.direccionesFrecuentes = direcciones;
        return this;
    }

    public UsuarioBuilder addDireccion(Direccion d) {
        this.direccionesFrecuentes.add(d);
        return this;
    }

    public UsuarioBuilder withMetodosPago(List<String> metodos) {
        this.metodosPago = metodos;
        return this;
    }

    public UsuarioBuilder addMetodoPago(String metodo) {
        this.metodosPago.add(metodo);
        return this;
    }

    public Usuario build() {
        return new Usuario(idUsuario, nombreCompleto, correo, telefono, direccionesFrecuentes, metodosPago);
    }
}
