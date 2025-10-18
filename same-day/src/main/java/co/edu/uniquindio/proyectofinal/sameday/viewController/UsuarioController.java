package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.builder.UsuarioBuilder;
import co.edu.uniquindio.proyectofinal.sameday.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UsuarioController {

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

    private UsuarioService usuarioService;
    private ObservableList<Usuario> listaObservableUsuarios;
    private Usuario usuarioSeleccionado;

    @FXML
    public void initialize() {
        usuarioService = ModelFactory.getInstance().getUsuarioService();
        configurarTabla();
        cargarUsuarios();

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
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreCompleto()));
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colCedula.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(obtenerCedula(data.getValue())));
        colDireccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(obtenerDireccion(data.getValue())));
    }

    private void cargarUsuarios() {
        listaObservableUsuarios = FXCollections.observableArrayList(usuarioService.listarTodos());
        tablaUsuarios.setItems(listaObservableUsuarios);
    }

    @FXML
    public void registrarUsuario() {
        if (camposVacios()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        Direccion dir = new Direccion("D-" + txtCedula.getText(), "Casa", txtDireccion.getText(), "Ciudad", "0,0");

        Usuario usuario = new UsuarioBuilder()
                .withNombreCompleto(txtNombre.getText())
                .withCorreo(txtCorreo.getText())
                .withTelefono(txtTelefono.getText())
                .build();
        usuario.getMetodosPago().add(txtCedula.getText());
        usuario.getDireccionesFrecuentes().add(dir);

        usuarioService.crearUsuario(usuario);
        limpiarCampos();
        cargarUsuarios();
        mostrarAlerta("Éxito", "Usuario registrado correctamente.");
    }

    @FXML
    public void actualizarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario de la tabla.");
            return;
        }

        if (camposVacios()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        usuarioSeleccionado.setNombreCompleto(txtNombre.getText());
        usuarioSeleccionado.setCorreo(txtCorreo.getText());
        usuarioSeleccionado.setTelefono(txtTelefono.getText());
        usuarioSeleccionado.getMetodosPago().clear();
        usuarioSeleccionado.getMetodosPago().add(txtCedula.getText());
        usuarioSeleccionado.getDireccionesFrecuentes().clear();
        usuarioSeleccionado.getDireccionesFrecuentes()
                .add(new Direccion("D-" + txtCedula.getText(), "Casa", txtDireccion.getText(), "Ciudad", "0,0"));

        usuarioService.actualizarUsuario(usuarioSeleccionado);

        // 🔹 Refrescar tabla para que se vean los cambios
        tablaUsuarios.refresh();

        limpiarCampos();
        mostrarAlerta("Éxito", "Usuario actualizado correctamente.");
    }

    @FXML
    public void eliminarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario de la tabla.");
            return;
        }

        usuarioService.eliminarUsuario(usuarioSeleccionado.getIdUsuario());
        usuarioSeleccionado = null;
        cargarUsuarios();
        limpiarCampos();
        mostrarAlerta("Éxito", "Usuario eliminado correctamente.");
    }

    // 🔍 Métodos auxiliares
    private String obtenerCedula(Usuario u) {
        return (u.getMetodosPago() != null && !u.getMetodosPago().isEmpty()) ? u.getMetodosPago().get(0) : "";
    }

    private String obtenerDireccion(Usuario u) {
        return (u.getDireccionesFrecuentes() != null && !u.getDireccionesFrecuentes().isEmpty())
                ? u.getDireccionesFrecuentes().get(0).getCalle() : "";
    }

    private boolean camposVacios() {
        return txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()
                || txtTelefono.getText().isEmpty() || txtCedula.getText().isEmpty()
                || txtDireccion.getText().isEmpty();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCedula.clear();
        txtDireccion.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}