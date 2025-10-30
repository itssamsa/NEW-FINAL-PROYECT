package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MiEnvioController {

    @FXML private TextField txtIdEnvio;
    @FXML private TextArea txtInfoEnvio;

    private Envio envioActual;

    @FXML
    private void buscarEnvio() {
        String id = txtIdEnvio.getText().trim();
        if (id.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un ID de envío para buscar.");
            return;
        }

        Optional<Envio> envioOpt = ModelFactory.getInstance().getEnvioService().obtener(id);
        if (envioOpt.isEmpty()) {
            mostrarAlerta("Error", "No se encontró ningún envío con el ID ingresado.");
            txtInfoEnvio.setText("Sin resultados.");
            return;
        }

        envioActual = envioOpt.get();

        txtInfoEnvio.setText(
                "📦 Envío encontrado:\n" +
                        "ID: " + envioActual.getIdEnvio() +
                        "\nUsuario: " + envioActual.getUsuario().getNombreCompleto() +
                        "\nServicios adicionales: " + envioActual.getServiciosAdicionales() +
                        "\nCosto total: $" + envioActual.getCostoTotal() +
                        "\nEstado actual: " + (envioActual.getEstado() != null ? envioActual.getEstado() : "Sin estado") +
                        "\nPagado: " + (envioActual.isPagado() ? "Sí" : "No")
        );
    }

    @FXML
    private void abrirVentanaPago() {
        if (envioActual == null) {
            mostrarAlerta("Error", "Debe buscar un envío antes de pagar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinal/sameday/Pagar.fxml"));
            Stage ventanaPago = new Stage();
            ventanaPago.setScene(new Scene(loader.load()));
            ventanaPago.setTitle("Pago del Envío");

            PagarController pagarController = loader.getController();
            pagarController.setEnvio(envioActual);

            ventanaPago.initModality(Modality.APPLICATION_MODAL);
            ventanaPago.showAndWait();

            // 🔄 Refrescar info después del pago
            buscarEnvio();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de pago.");
        }
    }

    @FXML
    private void cancelarEnvio() {
        if (envioActual == null) {
            mostrarAlerta("Error", "Debe buscar un envío antes de cancelarlo.");
            return;
        }

        EstadoEnvio estado = envioActual.getEstado();

        if ((estado == null || estado == EstadoEnvio.SOLICITADO) && !envioActual.isPagado()) {
            ModelFactory.getInstance().getEnvioService().eliminar(envioActual.getIdEnvio());
            txtInfoEnvio.clear();
            mostrarAlerta("Cancelado", "El envío fue cancelado correctamente.");
        } else if (estado == EstadoEnvio.SOLICITADO && envioActual.isPagado()) {
            mostrarAlerta("No permitido", "El envío ya fue pagado y no puede cancelarse.");
        } else {
            mostrarAlerta("No permitido", "El envío no puede cancelarse porque su estado es: " + estado);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
