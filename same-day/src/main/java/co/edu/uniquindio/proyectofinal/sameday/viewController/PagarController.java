package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Pago;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
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

    public void setEnvio(Envio envio) {
        this.envio = envio;
        lblIdEnvio.setText("ID del Envío: " + envio.getIdEnvio());
        lblTotal.setText("Total a pagar: $" + envio.getCostoTotal());
        cbMetodoPago.getItems().addAll(MetodoPago.values());

        Usuario usuario = envio.getUsuario();
        if (usuario != null && usuario.getDireccionesFrecuentes() != null && !usuario.getDireccionesFrecuentes().isEmpty()) {
            Direccion direccion = usuario.getDireccionesFrecuentes().get(0);
            txtOrigen.setText(direccion.getCalle());
            txtDestino.setText(direccion.getCalle());
        } else {
            txtOrigen.setText("");
            txtDestino.setText("");
        }
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

        envio.setEstado(EstadoEnvio.SOLICITADO);
        envio.setPagado(true);


        Direccion origen = new Direccion("D-ORIGEN", "Origen", txtOrigen.getText(), "Ciudad registrada", "0,0");
        Direccion destino = new Direccion("D-DESTINO", "Destino", txtDestino.getText(), "Ciudad registrada", "0,0");
        envio.setOrigen(origen);
        envio.setDestino(destino);

        ModelFactory.getInstance().getEnvioService().actualizar(envio);

        MetodoPago metodo = cbMetodoPago.getValue();
        String idPago = lblIdPago.getText().replace("ID del Pago: ", "");

        Pago pago = new Pago(idPago, envio.getCostoTotal(), metodo, envio);
        pago.setResultado("Aprobado");
        ModelFactory.getInstance().getPagoService().registrar(pago);

        mostrarAlerta("Pago Exitoso",
                "✅ Pago registrado correctamente.\n\n" +
                        "📦 ID Envío: " + envio.getIdEnvio() +
                        "\n💳 ID Pago: " + idPago +
                        "\nMétodo: " + metodo +
                        "\nMonto: $" + envio.getCostoTotal() +
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

    private String generarIdPago() {
        int numero = (int) (Math.random() * 9000) + 1000;
        return "P-" + numero;
    }
}

