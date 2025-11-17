package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RepartidorController {


    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtZona;
    @FXML private ComboBox<EstadoRepartidor> cbEstado;
    @FXML private TableView<Repartidor> tablaRepartidores;
    @FXML private TableColumn<Repartidor, String> colId;
    @FXML private TableColumn<Repartidor, String> colNombre;
    @FXML private TableColumn<Repartidor, String> colDocumento;
    @FXML private TableColumn<Repartidor, String> colTelefono;
    @FXML private TableColumn<Repartidor, String> colZona;
    @FXML private TableColumn<Repartidor, String> colEstado;
    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, String> colEnvioId;
    @FXML private TableColumn<Envio, String> colEnvioEstado;

    @FXML private ComboBox<EstadoEnvio> cbEstadoEnvio;

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ObservableList<Repartidor> listaObservable = FXCollections.observableArrayList();

    private Repartidor repartidorSeleccionado;
    private Envio envioSeleccionado;

    @FXML
    public void initialize() {


        cbEstado.getItems().addAll(EstadoRepartidor.values());

        configurarTablaRepartidores();
        cargarRepartidores();


        configurarTablaEnvios();
        cbEstadoEnvio.getItems().addAll(EstadoEnvio.EN_RUTA, EstadoEnvio.ENTREGADO, EstadoEnvio.INCIDENCIA);


        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            repartidorSeleccionado = newSel;
            if (newSel != null) {
                llenarCamposDesdeTabla(newSel);
                cargarEnviosAsignados(newSel);
            }
        });


        tablaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            envioSeleccionado = newSel;
        });
    }

    private void configurarTablaRepartidores() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdRepartidor()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colDocumento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDocumento()));
        colTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));
        colZona.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getZonaCobertura()));
        colEstado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEstado() != null ? data.getValue().getEstado().name() : "N/A")
        );
    }

    private void configurarTablaEnvios() {
        colEnvioId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdEnvio()));
        colEnvioEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().name()));
    }

    private void cargarRepartidores() {
        listaObservable.setAll(modelFactory.getRepartidorService().listar());
        tablaRepartidores.setItems(listaObservable);
    }

    private void cargarEnviosAsignados(Repartidor r) {
        tablaEnvios.setItems(
                FXCollections.observableArrayList(r.getEnviosAsignados())
        );
    }


    @FXML
    private void crearRepartidor() {
        if (camposVacios()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        Repartidor r = new Repartidor(
                txtId.getText(),
                txtNombre.getText(),
                txtDocumento.getText(),
                txtTelefono.getText(),
                cbEstado.getValue(),
                txtZona.getText()
        );

        modelFactory.getRepartidorService().crear(r);
        cargarRepartidores();
        limpiarCampos();
        mostrarAlerta("Éxito", "Repartidor creado correctamente.");
    }

    @FXML
    private void actualizarRepartidor() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor de la tabla.");
            return;
        }

        repartidorSeleccionado.setNombre(txtNombre.getText());
        repartidorSeleccionado.setDocumento(txtDocumento.getText());
        repartidorSeleccionado.setTelefono(txtTelefono.getText());
        repartidorSeleccionado.setZonaCobertura(txtZona.getText());
        repartidorSeleccionado.setEstado(cbEstado.getValue());

        modelFactory.getRepartidorService().actualizar(repartidorSeleccionado);
        tablaRepartidores.refresh();
        mostrarAlerta("Actualizado", "Repartidor actualizado correctamente.");
    }

    @FXML
    private void eliminarRepartidor() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor.");
            return;
        }

        modelFactory.getRepartidorService().eliminar(repartidorSeleccionado.getIdRepartidor());
        cargarRepartidores();
        tablaEnvios.getItems().clear();
        limpiarCampos();
        mostrarAlerta("Eliminado", "Repartidor eliminado correctamente.");
    }


    @FXML
    private void cambiarEstadoEnvio() {
        if (envioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un envío.");
            return;
        }

        if (cbEstadoEnvio.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un estado nuevo.");
            return;
        }

        EstadoEnvio nuevoEstado = cbEstadoEnvio.getValue();
        envioSeleccionado.setEstado(nuevoEstado);

        if (nuevoEstado == EstadoEnvio.ENTREGADO) {
            envioSeleccionado.setFechaEstimadaEntrega(java.time.LocalDateTime.now());
        }

        tablaEnvios.refresh();
        mostrarAlerta("Éxito", "Estado del envío actualizado.");
    }


    private void llenarCamposDesdeTabla(Repartidor r) {
        txtId.setText(r.getIdRepartidor());
        txtNombre.setText(r.getNombre());
        txtDocumento.setText(r.getDocumento());
        txtTelefono.setText(r.getTelefono());
        txtZona.setText(r.getZonaCobertura());
        cbEstado.setValue(r.getEstado());
    }

    private boolean camposVacios() {
        return txtId.getText().isEmpty() ||
                txtNombre.getText().isEmpty() ||
                txtDocumento.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                txtZona.getText().isEmpty() ||
                cbEstado.getValue() == null;
    }

    @FXML
    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtZona.clear();
        cbEstado.getSelectionModel().clearSelection();

        tablaRepartidores.getSelectionModel().clearSelection();
        tablaEnvios.getItems().clear();

        repartidorSeleccionado = null;
        envioSeleccionado = null;
    }

    @FXML
    private void actualizarTablaRepartidores() {
        cargarRepartidores();
        tablaRepartidores.refresh();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
