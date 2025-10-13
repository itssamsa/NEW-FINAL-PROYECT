package co.edu.uniquindio.proyectofinal.plataformadeenvios.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ComboBox<String> cmbTipoCuenta;

    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbTipoCuenta.getItems().addAll("Usuario", "Repartidor", "Administrador");
    }

    @FXML
    private void handleLogin() {
        String idUsuario = txtContrasena.getText();
        String tipo = cmbTipoCuenta.getValue();

        if (idUsuario.isEmpty() || tipo == null) {
            lblMensaje.setText("Complete todos los campos");
            return;
        }


        if (idUsuario.equals("admin") && idUsuario.equals("1234") && tipo.equals("Administrador")) {
            lblMensaje.setText("Bienvenido, Administrador");
        } else {
            lblMensaje.setText("Credenciales incorrectas");
        }
    }

    @FXML
    private void handleRegistro() {
        lblMensaje.setText(" Redirigiendo al formulario de registro...");
        //lo puedo redirigir :)
    }
}