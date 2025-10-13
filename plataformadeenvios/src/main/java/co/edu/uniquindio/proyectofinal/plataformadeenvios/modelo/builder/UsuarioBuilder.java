package co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.builder;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Direccion;
import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Envio;
import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
//1 clase builder que se usa en Usuario
public class UsuarioBuilder {
    protected String idUsuario;
    protected String nombre;
    protected String correo;
    protected String telefono;
    protected Direccion direccion;
    protected ArrayList<Envio> envios=new ArrayList<>();

    public UsuarioBuilder idUsuario(String IdUsuario) {
        this.idUsuario=IdUsuario;
        return this;
    }

    public UsuarioBuilder nombre(String nombre) {
        this.nombre=nombre;
        return this;
    }

    public UsuarioBuilder correo(String correo) {
        this.correo= correo;
        return this;
    }

    public UsuarioBuilder telefono(String telefono) {
        this.telefono =telefono;
        return this;
    }

    public UsuarioBuilder direccion(Direccion direccion) {
        this.direccion= direccion;
        return this;
    }

    public UsuarioBuilder envios( ArrayList<Envio> envios) {
        this.envios= envios;
        return this;
    }
    public Usuario build(){
        return new Usuario(idUsuario,nombre,correo,telefono,direccion);
    }

}