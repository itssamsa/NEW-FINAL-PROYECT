package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.model.facade.AdminFacade;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminController {

    private final AdminFacade adminFacade = new AdminFacade();

    // --- COMPONENTES DE LA VISTA ---
    @FXML private TableView<Repartidor> tablaRepartidores;
    @FXML private TableView<Envio> tablaEnvios;
    @FXML private PieChart chartEnvios;
    @FXML private Label lblTotalEntregados;
    @FXML private Label lblTotalEnRuta;

    @FXML private VBox panelRepartidores;
    @FXML private VBox panelEnvios;
    @FXML private VBox panelMetricas;

    // ---------- BOTONES ----------
    @FXML
    public void handleVerRepartidores() {
        mostrarPanel(panelRepartidores);
        cargarRepartidores();
    }

    @FXML
    public void handleVerEnvios() {
        mostrarPanel(panelEnvios);
        cargarEnvios();
    }

    @FXML
    public void handleVerMetricas() {
        mostrarPanel(panelMetricas);
        cargarMetricas();
    }

    @FXML
    public void handleCerrarSesion() {
        mostrarInfo("Sesión cerrada", "Has cerrado sesión correctamente.");
    }

    // ---------- MÉTODOS INTERNOS ----------
    private void mostrarPanel(VBox panel) {
        panelRepartidores.setVisible(false);
        panelEnvios.setVisible(false);
        panelMetricas.setVisible(false);
        panel.setVisible(true);
    }

    private void cargarRepartidores() {
        List<Repartidor> lista = adminFacade.listarRepartidores();
        ObservableList<Repartidor> datos = FXCollections.observableArrayList(lista);
        tablaRepartidores.setItems(datos);
    }

    private void cargarEnvios() {
        List<Envio> lista = adminFacade.listarEnvios();
        ObservableList<Envio> datos = FXCollections.observableArrayList(lista);
        tablaEnvios.setItems(datos);
    }

    private void cargarMetricas() {
        long entregados = adminFacade.contarEnviosEntregados();
        long enRuta = adminFacade.contarEnviosEnRuta();

        lblTotalEntregados.setText("Entregados: " + entregados);
        lblTotalEnRuta.setText("En ruta: " + enRuta);

        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList(
                new PieChart.Data("Entregados", entregados),
                new PieChart.Data("En Ruta", enRuta)
        );
        chartEnvios.setData(datos);
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
