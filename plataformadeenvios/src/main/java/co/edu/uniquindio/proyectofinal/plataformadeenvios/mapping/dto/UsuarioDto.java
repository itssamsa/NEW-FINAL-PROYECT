package co.edu.uniquindio.proyectofinal.plataformadeenvios.mapping.dto;


import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Direccion;
import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Envio;

import java.util.ArrayList;

public record UsuarioDto(
        String IdUsuario,
        String nombre,
        String correo,
        String telefono,
        Direccion direccion,
        ArrayList<Envio>envios) {
}