package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.model.facade.AdminFacade;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    // --- Facade ---
    private final AdminFacade adminFacade = new AdminFacade();

    // --- Paneles ---
    @FXML private VBox panelUsuarios;
    @FXML private VBox panelRepartidores;
    @FXML private VBox panelAsignacion;


    // --- Campos Usuarios ---
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCedula;
    @FXML private TextField txtDireccion;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colTelefono;
    @FXML private TableColumn<Usuario, String> colCedula;
    @FXML private TableColumn<Usuario, String> colDireccion;
    @FXML private Button btnRefrescarUsuarios;

    private ObservableList<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;

    // --- Campos Repartidores ---
    @FXML private TextField txtId;
    @FXML private TextField txtNombreR;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefonoR;
    @FXML private TextField txtZona;
    @FXML private ComboBox<EstadoRepartidor> cbEstado;
    @FXML private TableView<Repartidor> tablaRepartidores;
    @FXML private TableColumn<Repartidor, String> colId;
    @FXML private TableColumn<Repartidor, String> colNombreRR;
    @FXML private TableColumn<Repartidor, String> colDocumentoR;
    @FXML private TableColumn<Repartidor, String> colTelefonoRR;
    @FXML private TableColumn<Repartidor, String> colZonaR;
    @FXML private TableColumn<Repartidor, String> colEstadoR;
    @FXML private Button btnRefrescarRepartidores;



    private ObservableList<Repartidor> listaRepartidores;
    private Repartidor repartidorSeleccionado;

    // --- Campos Asignación ---
    @FXML private ComboBox<Envio> cbEnvios;
    @FXML private ComboBox<Repartidor> cbRepartidores;
    @FXML private Button btnAsignar;

    private ObservableList<Envio> listaEnvios;
    private ObservableList<Repartidor> listaRepartidoresActivos;

    // --- Inicialización ---
    @FXML
    public void initialize() {
        cbEstado.getItems().addAll(EstadoRepartidor.values());

        configurarTablaUsuarios();
        cargarUsuarios();

        configurarTablaRepartidores();
        cargarRepartidores();

        cargarEnvios();

        // Selección tabla usuarios
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            usuarioSeleccionado = newVal;
            if (newVal != null) {
                txtNombre.setText(newVal.getNombreCompleto());
                txtCorreo.setText(newVal.getCorreo());
                txtTelefono.setText(newVal.getTelefono());
                txtCedula.setText(obtenerCedula(newVal));
                txtDireccion.setText(obtenerDireccion(newVal));
            }
        });

        // Selección tabla repartidores
        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            repartidorSeleccionado = newVal;
            if (newVal != null) {
                txtId.setText(newVal.getIdRepartidor());
                txtNombreR.setText(newVal.getNombre());
                txtDocumento.setText(newVal.getDocumento());
                txtTelefonoR.setText(newVal.getTelefono());
                txtZona.setText(newVal.getZonaCobertura());
                cbEstado.setValue(newVal.getEstado());
            }
        });

        // Botón asignar repartidor a envío
        btnAsignar.setOnAction(e -> asignarRepartidorAEnvio());
    }

    // --- Métodos para alternar paneles ---
    @FXML
    private void mostrarUsuarios() {
        panelUsuarios.setVisible(true);
        panelRepartidores.setVisible(false);
        panelAsignacion.setVisible(false);
    }

    @FXML
    private void mostrarRepartidores() {
        panelUsuarios.setVisible(false);
        panelRepartidores.setVisible(true);
        panelAsignacion.setVisible(false);
    }

    @FXML
    private void mostrarAsignacion() {
        panelUsuarios.setVisible(false);
        panelRepartidores.setVisible(false);
        panelAsignacion.setVisible(true);

        cargarEnvios();
        cargarRepartidoresActivos();
    }

    // --- CRUD Usuarios ---
    @FXML private void registrarUsuario() {
        if (camposVaciosUsuario()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }
        adminFacade.crearUsuario(txtNombre.getText(), txtCorreo.getText(), txtTelefono.getText(),
                txtCedula.getText(), txtDireccion.getText());
        limpiarCamposUsuario();
        cargarUsuarios();
        mostrarAlerta("Éxito", "Usuario registrado correctamente.");
    }

    @FXML private void actualizarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario.");
            return;
        }
        adminFacade.actualizarUsuario(usuarioSeleccionado, txtNombre.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtCedula.getText(), txtDireccion.getText());
        tablaUsuarios.refresh();
        limpiarCamposUsuario();
        mostrarAlerta("Éxito", "Usuario actualizado correctamente.");
    }

    @FXML private void eliminarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario.");
            return;
        }
        adminFacade.eliminarUsuario(usuarioSeleccionado.getIdUsuario());
        usuarioSeleccionado = null;
        cargarUsuarios();
        limpiarCamposUsuario();
        mostrarAlerta("Éxito", "Usuario eliminado correctamente.");
    }

    private void configurarTablaUsuarios() {
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreCompleto()));
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colCedula.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(obtenerCedula(data.getValue())));
        colDireccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(obtenerDireccion(data.getValue())));
    }

    private void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(adminFacade.listarUsuarios());
        tablaUsuarios.setItems(listaUsuarios);
    }

    private boolean camposVaciosUsuario() {
        return txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()
                || txtTelefono.getText().isEmpty() || txtCedula.getText().isEmpty()
                || txtDireccion.getText().isEmpty();
    }

    private void limpiarCamposUsuario() {
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCedula.clear();
        txtDireccion.clear();
        tablaUsuarios.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    private String obtenerCedula(Usuario u) {
        return (u.getMetodosPago() != null && !u.getMetodosPago().isEmpty()) ? u.getMetodosPago().get(0) : "";
    }

    private String obtenerDireccion(Usuario u) {
        return (u.getDireccionesFrecuentes() != null && !u.getDireccionesFrecuentes().isEmpty())
                ? u.getDireccionesFrecuentes().get(0).getCalle() : "";
    }

    @FXML
    private void actualizarTablaUsuarios() {
        cargarUsuarios();
        tablaUsuarios.refresh();
    }

    // --- CRUD Repartidores ---
    @FXML private void registrarRepartidor() {
        if (camposVaciosRepartidor()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }
        adminFacade.crearRepartidor(txtId.getText(), txtNombreR.getText(), txtDocumento.getText(),
                txtTelefonoR.getText(), cbEstado.getValue(), txtZona.getText());
        limpiarCamposRepartidor();
        cargarRepartidores();
        mostrarAlerta("Éxito", "Repartidor registrado correctamente.");
    }

    @FXML private void actualizarRepartidor() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor.");
            return;
        }
        adminFacade.actualizarRepartidor(repartidorSeleccionado, txtNombreR.getText(), txtDocumento.getText(),
                txtTelefonoR.getText(), cbEstado.getValue(), txtZona.getText());
        tablaRepartidores.refresh();
        limpiarCamposRepartidor();
        mostrarAlerta("Éxito", "Repartidor actualizado correctamente.");
    }

    @FXML private void eliminarRepartidor() {
        if (repartidorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un repartidor.");
            return;
        }
        adminFacade.eliminarRepartidor(repartidorSeleccionado.getIdRepartidor());
        repartidorSeleccionado = null;
        cargarRepartidores();
        limpiarCamposRepartidor();
        mostrarAlerta("Éxito", "Repartidor eliminado correctamente.");
    }

    private void configurarTablaRepartidores() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdRepartidor()));
        colNombreRR.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colDocumentoR.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDocumento()));
        colTelefonoRR.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colZonaR.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getZonaCobertura()));
        colEstadoR.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEstado() != null ? data.getValue().getEstado().name() : "N/A"
        ));
    }

    private void cargarRepartidores() {
        listaRepartidores = FXCollections.observableArrayList(adminFacade.listarRepartidores());
        tablaRepartidores.setItems(listaRepartidores);
    }

    private boolean camposVaciosRepartidor() {
        return txtId.getText().isEmpty() || txtNombreR.getText().isEmpty()
                || txtDocumento.getText().isEmpty() || txtTelefonoR.getText().isEmpty()
                || txtZona.getText().isEmpty() || cbEstado.getValue() == null;
    }

    private void limpiarCamposRepartidor() {
        txtId.clear();
        txtNombreR.clear();
        txtDocumento.clear();
        txtTelefonoR.clear();
        txtZona.clear();
        cbEstado.getSelectionModel().clearSelection();
        tablaRepartidores.getSelectionModel().clearSelection();
        repartidorSeleccionado = null;
    }

    @FXML
    private void actualizarTablaRepartidores() {
        cargarRepartidores();
        tablaRepartidores.refresh();
    }


    // --- Gestión Asignación de Envíos ---
    private void cargarEnvios() {
        List<Envio> enviosSolicitados = adminFacade.listarEnviosPorEstado(EstadoEnvio.SOLICITADO);
        listaEnvios = FXCollections.observableArrayList(enviosSolicitados);
        cbEnvios.setItems(listaEnvios);
    }

    private void cargarRepartidoresActivos() {
        List<Repartidor> activos = adminFacade.listarRepartidores()
                .stream()
                .filter(r -> r.getEstado() == EstadoRepartidor.ACTIVO)
                .collect(Collectors.toList());
        listaRepartidoresActivos = FXCollections.observableArrayList(activos);
        cbRepartidores.setItems(listaRepartidoresActivos);
    }

    @FXML
    public void asignarRepartidorAEnvio() {
        Envio envio = cbEnvios.getValue();
        Repartidor repartidor = cbRepartidores.getValue();

        if (envio == null || repartidor == null) {
            mostrarAlerta("Error", "Seleccione un envío y un repartidor.");
            return;
        }

        boolean exito = adminFacade.asignarRepartidorAEnvio(envio, repartidor);
        if (exito) {
            mostrarAlerta("Éxito", "Repartidor asignado correctamente.");
            cargarEnvios();
            cargarRepartidoresActivos();
        } else {
            mostrarAlerta("Error", "No se pudo asignar el repartidor.");
        }
    }


    // --- Alerta ---
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}