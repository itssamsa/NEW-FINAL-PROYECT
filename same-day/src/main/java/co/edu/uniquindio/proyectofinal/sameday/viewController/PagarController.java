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

        cbDirecciones.setOnAction(event -> {
            Direccion seleccionada = cbDirecciones.getValue();
            if (seleccionada != null) {
                txtDestino.setText(seleccionada.getCalle() + " - " + seleccionada.getCiudad());
            }
        });
    }

    // ---------------------------------------------------
    // SE SETEAN ORIGEN Y DESTINO SEGÚN EL ENVÍO REAL
    // ---------------------------------------------------
    public void setEnvio(Envio envio) {
        this.envio = envio;

        lblIdEnvio.setText("ID del Envío: " + envio.getIdEnvio());
        lblTotal.setText("Total a pagar: $" + envio.getCostoTotal());
        lblIdPago.setText("ID del Pago: " + generarIdPago());

        Usuario usuario = envio.getUsuario();

        // Mostrar ORIGEN y DESTINO del envío
        if (envio.getOrigen() != null) {
            txtOrigen.setText(envio.getOrigen().getCalle() + " - " + envio.getOrigen().getCiudad());
        } else {
            txtOrigen.setText("");
        }

        if (envio.getDestino() != null) {
            txtDestino.setText(envio.getDestino().getCalle() + " - " + envio.getDestino().getCiudad());
        } else {
            txtDestino.setText("");
        }

        // Llenar ComboBox de direcciones del usuario
        if (usuario != null && usuario.getDireccionesFrecuentes() != null && !usuario.getDireccionesFrecuentes().isEmpty()) {

            cbDirecciones.getItems().addAll(usuario.getDireccionesFrecuentes());
            cbDirecciones.setPromptText("Seleccione su dirección guardada");

            // Seleccionar automáticamente la dirección destino usada en el envío
            Direccion destino = envio.getDestino();
            if (destino != null) {
                cbDirecciones.setValue(destino);
            }

        } else {
            cbDirecciones.setPromptText("Sin direcciones registradas");
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

        // ORIGEN Y DESTINO YA VIENEN DEL ENVÍO, NO SE CAMBIAN
        Direccion destino = cbDirecciones.getValue() != null
                ? cbDirecciones.getValue()
                : envio.getDestino();

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
