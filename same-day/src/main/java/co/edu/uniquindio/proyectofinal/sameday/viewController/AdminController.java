package co.edu.uniquindio.proyectofinal.sameday.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AdminController {

    @FXML
    public void handleVerRepartidores() {
        mostrarInfo("Ver Repartidores", "Aquí se listarían los repartidores (placeholder).");
    }

    @FXML
    public void handleVerEnvios() {
        mostrarInfo("Ver Envíos", "Aquí se mostrarían los envíos (placeholder).");
    }

    @FXML
    public void handleCerrarSesion() {
        mostrarInfo("Cerrar Sesión", "Sesión cerrada correctamente (placeholder).");
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}