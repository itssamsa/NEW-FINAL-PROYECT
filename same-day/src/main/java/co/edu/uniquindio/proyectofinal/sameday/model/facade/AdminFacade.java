package co.edu.uniquindio.proyectofinal.sameday.model.facade;

import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;
import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.builder.UsuarioBuilder;

import java.util.List;

public class AdminFacade {

    private final ModelFactory modelFactory;

    public AdminFacade() {
        this.modelFactory = ModelFactory.getInstance();
    }

    // --- Usuarios ---
    public void crearUsuario(String nombre, String correo, String telefono, String cedula, String direccion) {
        Direccion dir = new Direccion("D-" + cedula, "Casa", direccion, "Ciudad", "0,0");
        Usuario usuario = new UsuarioBuilder()
                .withId(cedula)
                .withNombreCompleto(nombre)
                .withCorreo(correo)
                .withTelefono(telefono)
                .build();
        usuario.getMetodosPago().add(cedula);
        usuario.getDireccionesFrecuentes().add(dir);
        modelFactory.getUsuarioService().crearUsuario(usuario);
    }

    public void actualizarUsuario(Usuario usuario, String nombre, String correo, String telefono, String cedula, String direccion) {
        usuario.setNombreCompleto(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.getMetodosPago().clear();
        usuario.getMetodosPago().add(cedula);
        usuario.getDireccionesFrecuentes().clear();
        usuario.getDireccionesFrecuentes().add(new Direccion("D-" + cedula, "Casa", direccion, "Ciudad", "0,0"));
        modelFactory.getUsuarioService().actualizarUsuario(usuario);
    }

    public void eliminarUsuario(String idUsuario) {
        modelFactory.getUsuarioService().eliminarUsuario(idUsuario);
    }

    public List<Usuario> listarUsuarios() {
        return modelFactory.getUsuarioService().listarTodos();
    }

    // --- Repartidores ---
    public void crearRepartidor(String id, String nombre, String documento, String telefono, EstadoRepartidor estado, String zona) {
        Repartidor r = new Repartidor(id, nombre, documento, telefono, estado, zona);
        modelFactory.getRepartidorService().crear(r);
    }

    public void actualizarRepartidor(Repartidor r, String nombre, String documento, String telefono, EstadoRepartidor estado, String zona) {
        r.setNombre(nombre);
        r.setDocumento(documento);
        r.setTelefono(telefono);
        r.setEstado(estado);
        r.setZonaCobertura(zona);
        modelFactory.getRepartidorService().actualizar(r);
    }

    public void eliminarRepartidor(String id) {
        modelFactory.getRepartidorService().eliminar(id);
    }

    public List<Repartidor> listarRepartidores() {
        return modelFactory.getRepartidorService().listar();
    }

    public void cambiarEstado(Repartidor r, EstadoRepartidor estado) {
        r.setEstado(estado);
        modelFactory.getRepartidorService().actualizar(r);
    }
}
