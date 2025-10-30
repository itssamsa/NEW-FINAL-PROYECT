package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.MetodoPago;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PagarController {

    @FXML private Label lblIdEnvio;
    @FXML private Label lblTotal;
    @FXML private ComboBox<MetodoPago> cbMetodoPago;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;

    private Envio envio;

    public void setEnvio(Envio envio) {
        this.envio = envio;
        lblIdEnvio.setText("ID del Envío: " + envio.getIdEnvio());
        lblTotal.setText("Total a pagar: $" + envio.getCostoTotal());
        cbMetodoPago.getItems().addAll(MetodoPago.values());
    }

    @FXML
    private void confirmarPago() {
        if (cbMetodoPago.getValue() == null || txtOrigen.getText().isEmpty() || txtDestino.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe completar todos los campos para continuar.");
            return;
        }

        // 🔸 Verificar si ya fue pagado
        if (envio.isPagado()) {
            mostrarAlerta("Advertencia", "Este envío ya fue pagado anteriormente.");
            return;
        }

        // 🔹 Cambiar el estado del envío a SOLICITADO y marcar como pagado
        envio.setEstado(EstadoEnvio.SOLICITADO);
        envio.setPagado(true);
        ModelFactory.getInstance().getEnvioService().actualizar(envio);

        mostrarAlerta("Pago Exitoso",
                "El envío con ID " + envio.getIdEnvio() +
                        " ha sido pagado exitosamente.\nMétodo: " + cbMetodoPago.getValue() +
                        "\nNuevo estado: SOLICITADO.");

        cerrarVentana();
    }

    @FXML
    private void cancelarPago() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblIdEnvio.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

