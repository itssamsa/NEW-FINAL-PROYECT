package co.edu.uniquindio.proyectofinal.sameday.service;

import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;

import java.util.*;

public class UsuarioService {
    private final Map<String, Usuario> repo = new HashMap<>();

    public UsuarioService() {}

    public Usuario crearUsuario(Usuario u) {
        repo.put(u.getIdUsuario(), u);
        return u;
    }

    public Optional<Usuario> obtenerPorId(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(repo.values());
    }

    public Usuario actualizarUsuario(Usuario u) {
        repo.put(u.getIdUsuario(), u);
        return u;
    }

    public boolean eliminarUsuario(String id) {
        return repo.remove(id) != null;
    }

    // direcciones
    public void agregarDireccionAUsuario(String idUsuario, Direccion nuevaDireccion) {
        Optional<Usuario> optUsuario = obtenerPorId(idUsuario);
        optUsuario.ifPresent(usuario -> usuario.getDireccionesFrecuentes().add(nuevaDireccion));
    }

    public void actualizarDireccionDeUsuario(String idUsuario, Direccion direccionActualizada) {
        Optional<Usuario> optUsuario = obtenerPorId(idUsuario);
        optUsuario.ifPresent(usuario -> {
            List<Direccion> direcciones = usuario.getDireccionesFrecuentes();
            for (int i = 0; i < direcciones.size(); i++) {
                if (direcciones.get(i).getIdDireccion().equals(direccionActualizada.getIdDireccion())) {
                    direcciones.set(i, direccionActualizada);
                    break;
                }
            }
        });
    }

    public void eliminarDireccionDeUsuario(String idUsuario, String idDireccion) {
        Optional<Usuario> optUsuario = obtenerPorId(idUsuario);
        optUsuario.ifPresent(usuario ->
                usuario.getDireccionesFrecuentes().removeIf(d -> d.getIdDireccion().equals(idDireccion))
        );
    }

    public List<Direccion> listarDireccionesDeUsuario(String idUsuario) {
        return obtenerPorId(idUsuario)
                .map(Usuario::getDireccionesFrecuentes)
                .orElse(Collections.emptyList());
    }

}