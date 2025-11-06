package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Pago;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.MetodoPago;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PagarController {

    @FXML private Label lblIdEnvio;
    @FXML private Label lblIdPago;
    @FXML private Label lblTotal;
    @FXML private ComboBox<MetodoPago> cbMetodoPago;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;

    private Envio envio;
    private String idPagoGenerado;

    public void setEnvio(Envio envio) {
        this.envio = envio;
        this.idPagoGenerado = generarIdPago();

        lblIdEnvio.setText("ID del Envío: " + envio.getIdEnvio());
        lblIdPago.setText("ID del Pago: " + idPagoGenerado);
        lblTotal.setText("Total a pagar: $" + envio.getCostoTotal());
        cbMetodoPago.getItems().addAll(MetodoPago.values());
    }

    @FXML
    private void confirmarPago() {
        if (cbMetodoPago.getValue() == null || txtOrigen.getText().isEmpty() || txtDestino.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe completar todos los campos para continuar.");
            return;
        }

        if (envio.isPagado()) {
            mostrarAlerta("Advertencia", "Este envío ya fue pagado anteriormente.");
            return;
        }

        // 🔹 Actualizar estado del envío
        envio.setEstado(EstadoEnvio.SOLICITADO);
        envio.setPagado(true);
        ModelFactory.getInstance().getEnvioService().actualizar(envio);

        // 🔸 Crear y registrar el pago
        MetodoPago metodo = cbMetodoPago.getValue();
        Pago pago = new Pago(idPagoGenerado, envio.getCostoTotal(), metodo, envio);
        pago.setResultado("Aprobado");

        ModelFactory.getInstance().getPagoService().registrar(pago);

        mostrarAlerta("Pago Exitoso",
                "✅ Pago registrado correctamente.\n\n" +
                        "📦 ID Envío: " + envio.getIdEnvio() +
                        "\n💳 ID Pago: " + pago.getIdPago() +
                        "\nMétodo: " + metodo +
                        "\nMonto: $" + envio.getCostoTotal() +
                        "\nFecha: " + pago.getFecha().toLocalDate() +
                        "\nEstado del envío: SOLICITADO.");

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

    // 🔸 Genera ID de pago aleatorio tipo P-1234
    private String generarIdPago() {
        int numero = (int) (Math.random() * 9000) + 1000;
        return "P-" + numero;
    }
}

