package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Tarifa;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.*;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.*;
import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class EnvioController {

    @FXML private ComboBox<Direccion> cbOrigen;
    @FXML private ComboBox<Direccion> cbDestino;
    @FXML private ComboBox<EstadoEnvio> cbEstado;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private DatePicker dpFechaEntrega;

    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkPrioridad;
    @FXML private CheckBox chkFirma;
    @FXML private CheckBox chkRastreo;
    @FXML private CheckBox chkNocturna;

    @FXML private Label lblCostoTotal;
    @FXML private Label lblDesglose;

    private Tarifa tarifaBase;
    private Envio envioActual;

    @FXML
    public void initialize() {
        cbEstado.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));

        // Direcciones de ejemplo
        ObservableList<Direccion> direcciones = FXCollections.observableArrayList(
                new Direccion("D1", "Casa", "Calle 10 #5-20", "Armenia", "4.534, -75.672"),
                new Direccion("D2", "Oficina", "Av Bolívar #23-45", "Armenia", "4.537, -75.670")
        );
        cbOrigen.setItems(direcciones);
        cbDestino.setItems(direcciones);

        // Tarifa base ejemplo
        tarifaBase = new Tarifa("T1", 10.0, 1.0, 1.0, 1.0, 5.0, 10.0);
    }

    @FXML
    public void calcularTarifa() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());

            // ✅ Obtener un usuario existente (el primero en la lista)
            Usuario usuario = ModelFactory.getInstance().getUsuarioService()
                    .listarTodos().stream().findFirst().orElse(null);

            if (usuario == null) {
                mostrarAlerta("Error", "No hay usuarios registrados. Cree un usuario primero.");
                return;
            }

            // Crear envío base
            envioActual = new Envio(
                    "E1",
                    cbOrigen.getValue(),
                    cbDestino.getValue(),
                    peso,
                    volumen,
                    usuario
            );

            double costo = tarifaBase.getCostoBase()
                    + (peso * tarifaBase.getCostoPorKg())
                    + (volumen * tarifaBase.getCostoPorM3());

            // Aplicar factories
            if (chkSeguro.isSelected()) {
                ServicioAdicionalFactory factory = new SeguroFactory();
                envioActual.addServicioAdicional(factory.crearServicio());
                envioActual = new EnvioConSeguro(envioActual);
            }
            if (chkPrioridad.isSelected()) {
                ServicioAdicionalFactory factory = new PrioridadFactory();
                envioActual.addServicioAdicional(factory.crearServicio());
                costo += tarifaBase.getRecargoPrioridad();
            }
            if (chkFirma.isSelected()) {
                ServicioAdicionalFactory factory = new FirmaRequeridaFactory();
                envioActual.addServicioAdicional(factory.crearServicio());
                costo += 3.0;
            }

            // Aplicar decorators
            if (chkRastreo.isSelected()) {
                envioActual = new EnvioConRastreoPremium(envioActual);
            }
            if (chkNocturna.isSelected()) {
                envioActual = new EnvioConEntregaNocturna(envioActual);
            }

            double costoTotal = envioActual.getCosto() + costo;
            lblCostoTotal.setText(String.format("Costo total estimado: $%.2f", costoTotal));

            String desglose = String.format(
                    "Base: $%.2f\nPeso: $%.2f\nVolumen: $%.2f\nServicios: $%.2f",
                    tarifaBase.getCostoBase(),
                    peso * tarifaBase.getCostoPorKg(),
                    volumen * tarifaBase.getCostoPorM3(),
                    (costoTotal - tarifaBase.getCostoBase() - peso - volumen)
            );
            lblDesglose.setText("Desglose de tarifa:\n" + desglose);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese valores numéricos válidos para peso y volumen.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Complete todos los campos antes de calcular.");
        }
    }

    @FXML
    public void confirmarEnvio() {
        if (envioActual == null) {
            mostrarAlerta("Advertencia", "Primero calcule la tarifa.");
            return;
        }

        envioActual.setEstado(cbEstado.getValue());
        envioActual.setFechaEstimadaEntrega(dpFechaEntrega.getValue() != null
                ? dpFechaEntrega.getValue().atStartOfDay()
                : LocalDate.now().atStartOfDay());

        mostrarAlerta("Éxito", "El envío ha sido confirmado correctamente.");
    }

    @FXML
    public void cancelarEnvio() {
        txtPeso.clear();
        txtVolumen.clear();
        cbOrigen.getSelectionModel().clearSelection();
        cbDestino.getSelectionModel().clearSelection();
        cbEstado.getSelectionModel().clearSelection();
        chkSeguro.setSelected(false);
        chkPrioridad.setSelected(false);
        chkFirma.setSelected(false);
        chkRastreo.setSelected(false);
        chkNocturna.setSelected(false);
        lblCostoTotal.setText("Costo total estimado: $");
        lblDesglose.setText("Desglose de tarifa:");
        envioActual = null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
