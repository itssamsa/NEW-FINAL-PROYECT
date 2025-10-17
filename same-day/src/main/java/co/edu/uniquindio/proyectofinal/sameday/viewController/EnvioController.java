package co.edu.uniquindio.proyectofinal.sameday.viewController;


import co.edu.uniquindio.proyectofinal.sameday.model.*;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.EnvioConEntregaNocturna;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.EnvioConRastreoPremium;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.EnvioConSeguro;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.FirmaRequeridaFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.ServicioAdicionalFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EnvioController {

    @FXML private TextField txtIdEnvio;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtCosto;

    @FXML private ComboBox<EstadoEnvio> cbEstado;

    // ORIGEN
    @FXML private TextField txtAliasOrigen;
    @FXML private TextField txtCalleOrigen;
    @FXML private TextField txtCiudadOrigen;
    @FXML private TextField txtCoordOrigen;

    // DESTINO
    @FXML private TextField txtAliasDestino;
    @FXML private TextField txtCalleDestino;
    @FXML private TextField txtCiudadDestino;
    @FXML private TextField txtCoordDestino;

    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, String> colIdEnvio;
    @FXML private TableColumn<Envio, String> colOrigen;
    @FXML private TableColumn<Envio, String> colDestino;
    @FXML private TableColumn<Envio, Double> colPeso;
    @FXML private TableColumn<Envio, Double> colVolumen;
    @FXML private TableColumn<Envio, Double> colCosto;
    @FXML private TableColumn<Envio, EstadoEnvio> colEstado;

    @FXML
    public void initialize() {
        cbEstado.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));

        colIdEnvio.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdEnvio()));
        colOrigen.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getOrigen().toString()));
        colDestino.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDestino().toString()));
        colPeso.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPeso()));
        colVolumen.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getVolumen()));
        colCosto.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCosto()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getEstado()));
    }

    @FXML
    private void crearEnvio() {
        try {
            Direccion origen = new Direccion(
                    "O-" + txtAliasOrigen.getText(),
                    txtAliasOrigen.getText(),
                    txtCalleOrigen.getText(),
                    txtCiudadOrigen.getText(),
                    txtCoordOrigen.getText()
            );

            Direccion destino = new Direccion(
                    "D-" + txtAliasDestino.getText(),
                    txtAliasDestino.getText(),
                    txtCalleDestino.getText(),
                    txtCiudadDestino.getText(),
                    txtCoordDestino.getText()
            );

            Envio nuevo = new Envio(
                    txtIdEnvio.getText(),
                    origen,
                    destino,
                    Double.parseDouble(txtPeso.getText()),
                    Double.parseDouble(txtVolumen.getText()),
                    null
            );

            nuevo.setCosto(Double.parseDouble(txtCosto.getText()));
            nuevo.setEstado(cbEstado.getValue());

            tablaEnvios.getItems().add(nuevo);
            limpiarCampos();

        } catch (Exception e) {
            mostrarAlerta("Error", "Verifica los campos. Todos son obligatorios.");
        }
    }

    @FXML
    private void limpiarCampos() {
        txtIdEnvio.clear();
        txtPeso.clear();
        txtVolumen.clear();
        txtCosto.clear();
        cbEstado.getSelectionModel().clearSelection();

        txtAliasOrigen.clear();
        txtCalleOrigen.clear();
        txtCiudadOrigen.clear();
        txtCoordOrigen.clear();

        txtAliasDestino.clear();
        txtCalleDestino.clear();
        txtCiudadDestino.clear();
        txtCoordDestino.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
