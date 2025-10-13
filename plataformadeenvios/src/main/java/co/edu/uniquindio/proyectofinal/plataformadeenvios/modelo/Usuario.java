package co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.builder.UsuarioBuilder;

import java.util.ArrayList;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private Direccion direccion;
    private ArrayList<Envio> envios=new ArrayList<>();

    public Usuario(String idUsuario, String nombre, String correo, String telefono, Direccion direccion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    public static UsuarioBuilder builder(){return new UsuarioBuilder();}//2.se implementa el patron builder en la creacion de usuario para que sea más legible

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(ArrayList<Envio> envios) {
        this.envios = envios;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "IdUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion=" + direccion +
                ", envios=" + envios +
                '}';
    }
}