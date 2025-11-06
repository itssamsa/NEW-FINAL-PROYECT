package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListarEnviosController {

    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private ComboBox<EstadoEnvio> cbEstado;
    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, String> colIdEnvio;
    @FXML private TableColumn<Envio, String> colUsuario;
    @FXML private TableColumn<Envio, String> colEstado;
    @FXML private TableColumn<Envio, String> colFecha;
    @FXML private TableColumn<Envio, Number> colCosto;

    private ObservableList<Envio> listaObservable;

    @FXML
    public void initialize() {
        cbEstado.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));

        colIdEnvio.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdEnvio()));
        colUsuario.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsuario().getNombreCompleto()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEstado() != null ? data.getValue().getEstado().name() : "SIN ESTADO"));
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getFechaCreacion() != null ? data.getValue().getFechaCreacion().toString() : "N/A"));
        colCosto.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getCostoTotal()));

        cargarEnvios();
    }

    @FXML
    private void filtrarEnvios() {
        List<Envio> envios = ModelFactory.getInstance().getEnvioService().listar();

        if (cbEstado.getValue() != null) {
            envios = envios.stream()
                    .filter(e -> e.getEstado() == cbEstado.getValue())
                    .collect(Collectors.toList());
        }

        if (dpInicio.getValue() != null && dpFin.getValue() != null) {
            LocalDate inicio = dpInicio.getValue();
            LocalDate fin = dpFin.getValue();
            envios = envios.stream()
                    .filter(e -> e.getFechaCreacion() != null &&
                            !e.getFechaCreacion().toLocalDate().isBefore(inicio) &&
                            !e.getFechaCreacion().toLocalDate().isAfter(fin))
                    .collect(Collectors.toList());
        }

        listaObservable.setAll(envios);
    }

    private void cargarEnvios() {
        List<Envio> envios = ModelFactory.getInstance().getEnvioService().listar();
        listaObservable = FXCollections.observableArrayList(envios);
        tablaEnvios.setItems(listaObservable);
    }
}
