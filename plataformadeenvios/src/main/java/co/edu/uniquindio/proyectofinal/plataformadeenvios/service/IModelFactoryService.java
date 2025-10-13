package co.edu.uniquindio.proyectofinal.plataformadeenvios.service;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Usuario;

import java.util.List;

public interface IModelFactoryService {
    List<Usuario> obtenerUsuario();
    boolean agregarUsuario(Usuario usuario);

    boolean eliminarUsuario(String idUsuario);

}