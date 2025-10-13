package co.edu.uniquindio.proyectofinal.plataformadeenvios.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UsuarioViewController {

    @FXML
    private Label lblRegistroLogin, lblPerfil, lblCotizacion, lblSolicitudes,
            lblServicios, lblPagos, lblRetiro, lblHistorial;

    @FXML
    public void initialize() {
        // Asignor acciones a los labels
        lblRegistroLogin.setOnMouseClicked(event -> abrirRegistro());
        lblPerfil.setOnMouseClicked(event -> abrirPerfil());
        lblCotizacion.setOnMouseClicked(event -> abrirCotizacion());
        lblSolicitudes.setOnMouseClicked(event -> abrirSolicitudes());
        lblServicios.setOnMouseClicked(event -> abrirServicios());
        lblPagos.setOnMouseClicked(event -> abrirPagos());
        lblRetiro.setOnMouseClicked(event -> abrirRetiro());
        lblHistorial.setOnMouseClicked(event -> abrirHistorial());
    }

    private void abrirRegistro() {
        System.out.println("Abriendo Registro/Login...");
    }

    private void abrirPerfil() {
        System.out.println("Abriendo Perfil...");
    }

    private void abrirCotizacion() {
        System.out.println("Abriendo Cotización...");
    }

    private void abrirSolicitudes() {
        System.out.println("Abriendo Solicitudes...");
    }

    private void abrirServicios() {
        System.out.println("Abriendo Servicios Adicionales...");
    }

    private void abrirPagos() {
        System.out.println("Abriendo Pagos...");
    }

    private void abrirRetiro() {
        System.out.println("Abriendo Retiro...");
    }

    private void abrirHistorial() {
        System.out.println("Abriendo Historial...");
    }
}