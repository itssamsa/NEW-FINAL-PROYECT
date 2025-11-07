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
    @FXML private ComboBox<Direccion> cbDirecciones;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;

    private Envio envio;

    @FXML
    public void initialize() {
        cbMetodoPago.getItems().addAll(MetodoPago.values());

        // Cambiar automáticamente el destino según la dirección seleccionada
        cbDirecciones.setOnAction(event -> {
            Direccion seleccionada = cbDirecciones.getValue();
            if (seleccionada != null) {
                txtDestino.setText(seleccionada.getCalle() + " - " + seleccionada.getCiudad());
            }
        });
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
        lblIdEnvio.setText("ID del Envío: " + envio.getIdEnvio());
        lblTotal.setText("Total a pagar: $" + envio.getCostoTotal());
        lblIdPago.setText("ID del Pago: " + generarIdPago());

        Usuario usuario = envio.getUsuario();

        if (usuario != null && usuario.getDireccionesFrecuentes() != null && !usuario.getDireccionesFrecuentes().isEmpty()) {
            cbDirecciones.getItems().addAll(usuario.getDireccionesFrecuentes());
            cbDirecciones.setPromptText("Seleccione su dirección de entrega");

            // Mostrar la primera como predeterminada
            Direccion primera = usuario.getDireccionesFrecuentes().get(0);
            cbDirecciones.setValue(primera);
            txtOrigen.setText("");
            txtDestino.setText(primera.getCalle() + " - " + primera.getCiudad());
        } else {
            cbDirecciones.setPromptText("Sin direcciones registradas");
            txtOrigen.setText("");
            txtDestino.setText("");
        }
    }

    @FXML
    private void confirmarPago() {
        if (cbMetodoPago.getValue() == null || txtDestino.getText().isEmpty()) {
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
        Direccion destino = cbDirecciones.getValue() != null
                ? cbDirecciones.getValue()
                : new Direccion("D-DESTINO", "Destino", txtDestino.getText(), "Ciudad registrada", "0,0");

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
                        "\nDirección de entrega: " + destino.getCalle() + " - " + destino.getCiudad() +
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
