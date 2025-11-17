package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MisDireccionesController {

    @FXML private TableView<Direccion> tablaDirecciones;
    @FXML private TableColumn<Direccion, String> colAlias;
    @FXML private TableColumn<Direccion, String> colCalle;
    @FXML private TableColumn<Direccion, String> colCiudad;
    @FXML private TableColumn<Direccion, String> colCoordenadas;

    @FXML private TextField txtAlias;
    @FXML private TextField txtCalle;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtCoordenadas;
    @FXML private TextField txtIdUsuario;

    private ObservableList<Direccion> listaDirecciones;
    private Direccion direccionSeleccionada;
    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        configurarTabla();

        tablaDirecciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            direccionSeleccionada = newSel;
            if (newSel != null) {
                txtAlias.setText(newSel.getAlias());
                txtCalle.setText(newSel.getCalle());
                txtCiudad.setText(newSel.getCiudad());
                txtCoordenadas.setText(newSel.getCoordenadas());
            }
        });
    }

    private void configurarTabla() {
        colAlias.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAlias()));
        colCalle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCalle()));
        colCiudad.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCiudad()));
        colCoordenadas.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCoordenadas()));
    }

    private void cargarDirecciones(Usuario usuario) {
        if (usuario == null) {
            tablaDirecciones.setItems(FXCollections.observableArrayList());
            return;
        }
        listaDirecciones = FXCollections.observableArrayList(usuario.getDireccionesFrecuentes());
        tablaDirecciones.setItems(listaDirecciones);
    }

    @FXML
    public void buscarUsuarioPorId() {
        String id = txtIdUsuario.getText().trim();
        if (id.isEmpty()) {
            mostrarAlerta("Advertencia", "Ingrese un ID de usuario.");
            return;
        }

        usuarioActual = modelFactory.buscarUsuarioPorId(id);
        if (usuarioActual == null) {
            tablaDirecciones.setItems(FXCollections.observableArrayList());
            mostrarAlerta("Error", "No se encontró ningún usuario con ese ID.");
            return;
        }

        cargarDirecciones(usuarioActual);
    }

    @FXML
    public void agregarDireccion() {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Primero debe buscar un usuario válido.");
            return;
        }

        if (camposVacios()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        Direccion nueva = new Direccion(
                "D-" + usuarioActual.getIdUsuario() + "-" + (usuarioActual.getDireccionesFrecuentes().size() + 1),
                txtAlias.getText(),
                txtCalle.getText(),
                txtCiudad.getText(),
                txtCoordenadas.getText()
        );

        usuarioActual.getDireccionesFrecuentes().add(nueva);
        cargarDirecciones(usuarioActual);
        limpiarCampos();
        mostrarAlerta("Éxito", "Dirección agregada correctamente.");
    }

    @FXML
    public void actualizarDireccion() {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Primero debe buscar un usuario válido.");
            return;
        }

        if (direccionSeleccionada == null) {
            mostrarAlerta("Advertencia", "Seleccione una dirección de la tabla.");
            return;
        }

        direccionSeleccionada.setAlias(txtAlias.getText());
        direccionSeleccionada.setCalle(txtCalle.getText());
        direccionSeleccionada.setCiudad(txtCiudad.getText());
        direccionSeleccionada.setCoordenadas(txtCoordenadas.getText());

        cargarDirecciones(usuarioActual);
        limpiarCampos();
        mostrarAlerta("Éxito", "Dirección actualizada correctamente.");
    }

    @FXML
    public void eliminarDireccion() {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Primero debe buscar un usuario válido.");
            return;
        }

        if (direccionSeleccionada == null) {
            mostrarAlerta("Advertencia", "Seleccione una dirección para eliminar.");
            return;
        }

        usuarioActual.getDireccionesFrecuentes()
                .removeIf(d -> d.getIdDireccion().equals(direccionSeleccionada.getIdDireccion()));

        cargarDirecciones(usuarioActual);
        limpiarCampos();
        mostrarAlerta("Éxito", "Dirección eliminada correctamente.");
    }

    @FXML
    public void actualizarTablaDirecciones() {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Debe buscar un usuario antes de actualizar la tabla.");
            return;
        }

        cargarDirecciones(usuarioActual);
        mostrarAlerta("Información", "Tabla actualizada.");
    }

    @FXML
    public void limpiarCampos() {
        txtAlias.clear();
        txtCalle.clear();
        txtCiudad.clear();
        txtCoordenadas.clear();
        tablaDirecciones.getSelectionModel().clearSelection();
    }

    private boolean camposVacios() {
        return txtAlias.getText().isEmpty() || txtCalle.getText().isEmpty()
                || txtCiudad.getText().isEmpty() || txtCoordenadas.getText().isEmpty();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
