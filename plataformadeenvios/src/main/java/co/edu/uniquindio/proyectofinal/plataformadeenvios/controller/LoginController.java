package co.edu.uniquindio.proyectofinal.plataformadeenvios.controller;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.EmpresaLogistica;
import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Usuario;
import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.Repartidor;

public class LoginController {

    private EmpresaLogistica empresaLogistica;

    public LoginController() {
        this.empresaLogistica = EmpresaLogistica.getInstance("Plataforma de Envíos");
    }

    public boolean verificarLogin(String idUsuario, String tipoUsuario) {

        // Validar entradas o nulas :)
        if (idUsuario == null || idUsuario.isEmpty() || tipoUsuario == null) {
            return false;
        }

        switch (tipoUsuario) {
            case "Usuario":
                return verificarUsuario(idUsuario);

            case "Repartidor":
                return verificarRepartidor(idUsuario);

            case "Admin":
                return idUsuario.equals("admin");
            default:
                return false;
        }
    }


    private boolean verificarUsuario(String idUsuario) {
        for (Usuario usuario : empresaLogistica.getUsuarios()) {
            if (usuario.getIdUsuario().equals(idUsuario)) {
                return true;
            }
        }
        return false;
    }


    private boolean verificarRepartidor(String idUsuario) {
        for (Repartidor repartidor : empresaLogistica.getRepartidors()) {
            if (repartidor.getIdRepartidor().equals(idUsuario)) {
                return true;
            }
        }
        return false;
    }
}