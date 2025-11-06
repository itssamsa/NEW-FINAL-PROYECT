package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Pago;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ListarPagosController {

    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private TableView<Pago> tablaPagos;
    @FXML private TableColumn<Pago, String> colIdPago;
    @FXML private TableColumn<Pago, String> colEnvio;
    @FXML private TableColumn<Pago, Double> colMonto;
    @FXML private TableColumn<Pago, String> colMetodo;
    @FXML private TableColumn<Pago, String> colFecha;

    @FXML
    public void initialize() {
        colIdPago.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getIdPago()));
        colEnvio.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEnvio().getIdEnvio()));
        colMonto.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getMonto()));
        colMetodo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMetodoPago().name()));
        colFecha.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                ));
    }

    @FXML
    private void buscarPagos() {
        LocalDate inicio = dpInicio.getValue();
        LocalDate fin = dpFin.getValue();

        if (inicio == null || fin == null) {
            mostrarAlerta("Error", "Debe seleccionar ambas fechas.");
            return;
        }

        var lista = ModelFactory.getInstance().getPagoService()
                .listarPorRangoFechas(LocalDateTime.of(inicio, LocalTime.MIN),
                        LocalDateTime.of(fin, LocalTime.MAX));

        tablaPagos.getItems().setAll(lista);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
