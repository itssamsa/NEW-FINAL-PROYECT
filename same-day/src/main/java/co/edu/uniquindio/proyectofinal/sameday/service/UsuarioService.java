package co.edu.uniquindio.proyectofinal.sameday.service;

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
}