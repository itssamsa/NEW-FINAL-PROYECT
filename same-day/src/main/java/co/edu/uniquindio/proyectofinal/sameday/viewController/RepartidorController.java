package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;
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

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ObservableList<Repartidor> listaObservable = FXCollections.observableArrayList();

    private Repartidor repartidorSeleccionado;

    @FXML
    public void initialize() {
        cbEstado.getItems().addAll(EstadoRepartidor.values());
        configurarTabla();
        cargarRepartidores();

        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                repartidorSeleccionado = newSel;
                llenarCamposDesdeTabla(newSel);
            }
        });
    }

    private void configurarTabla() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdRepartidor()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colDocumento.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDocumento()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colZona.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getZonaCobertura()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEstado() != null ? data.getValue().getEstado().name() : "N/A"
        ));
    }

    private void cargarRepartidores() {
        listaObservable.setAll(modelFactory.getRepartidorService().listar());
        tablaRepartidores.setItems(listaObservable);
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

        repartidorSeleccionado.setEstado(cbEstado.getValue());
        repartidorSeleccionado.setEstado(cbEstado.getValue());
        repartidorSeleccionado.setEstado(cbEstado.getValue());

        modelFactory.getRepartidorService().actualizar(repartidorSeleccionado);
        tablaRepartidores.refresh();
        mostrarAlerta("Actualizado", "Repartidor actualizado correctamente.");
    }

    @FXML
    private void eliminarRepartidor() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor de la tabla.");
            return;
        }

        modelFactory.getRepartidorService().eliminar(repartidorSeleccionado.getIdRepartidor());
        cargarRepartidores();
        limpiarCampos();
        mostrarAlerta("Eliminado", "Repartidor eliminado correctamente.");
    }

    @FXML
    private void cambiarEstado() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor.");
            return;
        }

        EstadoRepartidor nuevoEstado = cbEstado.getValue();
        repartidorSeleccionado.setEstado(nuevoEstado);
        modelFactory.getRepartidorService().actualizar(repartidorSeleccionado);
        tablaRepartidores.refresh();
        mostrarAlerta("Cambio de estado", "El repartidor ahora está " + nuevoEstado + ".");
    }

    @FXML
    private void verEnviosAsignados() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor.");
            return;
        }

        var envios = repartidorSeleccionado.getEnviosAsignados();
        if (envios.isEmpty()) {
            mostrarAlerta("Sin envíos", "El repartidor no tiene envíos asignados.");
        } else {
            StringBuilder info = new StringBuilder("Envíos asignados:\n");
            envios.forEach(e -> info.append("- ").append(e.getIdEnvio()).append(" (").append(e.getEstado()).append(")\n"));
            mostrarAlerta("Detalle", info.toString());
        }
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
        return txtId.getText().isEmpty() || txtNombre.getText().isEmpty()
                || txtDocumento.getText().isEmpty() || txtTelefono.getText().isEmpty()
                || txtZona.getText().isEmpty() || cbEstado.getValue() == null;
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
        repartidorSeleccionado = null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
