package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.model.*;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.EnvioConEntregaNocturna;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.EnvioConRastreoPremium;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.EnvioConSeguro;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.*;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.FirmaRequeridaFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.PrioridadFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.ServicioAdicionalFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.strategy.*;
import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class EnvioController {

    @FXML private ComboBox<Direccion> cbOrigen;
    @FXML private ComboBox<Direccion> cbDestino;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private ComboBox<EstadoEnvio> cbEstado;
    @FXML private DatePicker dpFechaEntrega;
    @FXML private Label lblCostoTotal;
    @FXML private Label lblDesglose;
    @FXML private ComboBox<String> cbEstrategia;


    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkPrioridad;
    @FXML private CheckBox chkFirma;
    @FXML private CheckBox chkRastreo;
    @FXML private CheckBox chkNocturna;

    private Envio envioActual;

    @FXML
    public void initialize() {
        cbEstado.getItems().addAll(EstadoEnvio.values());
        cbEstrategia.getItems().addAll("Por peso", "Por distancia", "Por prioridad");


        cbOrigen.getItems().addAll(
                new Direccion("D1", "Casa", "Calle 1", "Ciudad A", "1,1"),
                new Direccion("D2", "Oficina", "Calle 2", "Ciudad B", "2,2")
        );
        cbDestino.getItems().addAll(cbOrigen.getItems());

        dpFechaEntrega.setValue(LocalDate.now().plusDays(3));
    }


    @FXML
    private void calcularTarifa() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());

            Usuario usuario = ModelFactory.getInstance()
                    .getUsuarioService().listarTodos().stream().findFirst().orElse(null);

            if (usuario == null) {
                mostrarAlerta("Error", "No hay usuarios registrados. Cree un usuario primero.");
                return;
            }

            envioActual = new Envio("E1", cbOrigen.getValue(), cbDestino.getValue(), peso, volumen, usuario);

            double costoAdicional = 0.0;

            if (chkSeguro.isSelected()) {
                envioActual = new EnvioConSeguro(envioActual);
                costoAdicional += 10.0;
                envioActual.addServicioAdicional(ServicioAdicional.SEGURO);
            }

            if (chkRastreo.isSelected()) {
                envioActual = new EnvioConRastreoPremium(envioActual);
                costoAdicional += 5.0;
                envioActual.addServicioAdicional(ServicioAdicional.RASTREO_PREMIUM);
            }

            if (chkNocturna.isSelected()) {
                envioActual = new EnvioConEntregaNocturna(envioActual);
                costoAdicional += 8.0;
                envioActual.addServicioAdicional(ServicioAdicional.ENTREGA_NOCTURNA);
            }


            if (chkPrioridad.isSelected()) {
                ServicioAdicionalFactory prioridadFactory = new PrioridadFactory();
                ServicioAdicional servicio = prioridadFactory.crearServicio();
                envioActual.addServicioAdicional(servicio);
                costoAdicional += 7.0;
            }

            if (chkFirma.isSelected()) {
                ServicioAdicionalFactory firmaFactory = new FirmaRequeridaFactory();
                ServicioAdicional servicio = firmaFactory.crearServicio();
                envioActual.addServicioAdicional(servicio);
                costoAdicional += 4.0;
            }


            EstrategiaTarifa estrategia;
            switch (cbEstrategia.getValue()) {
                case "Por distancia" -> estrategia = new TarifaPorDistancia();
                case "Por prioridad" -> estrategia = new TarifaPorPrioridad();
                default -> estrategia = new TarifaPorPeso();
            }

            double costoBase = estrategia.calcularTarifa(envioActual);
            double costoFinal = costoBase + costoAdicional;


            lblCostoTotal.setText("Costo total estimado: $" + costoFinal);
            lblDesglose.setText(
                    "Estrategia aplicada: " + estrategia.descripcion() +
                            "\nCosto base: $" + costoBase +
                            "\nServicios adicionales: " + envioActual.getServiciosAdicionales() +
                            "\nCosto adicional: $" + costoAdicional
            );

        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos o incompletos. Verifique los campos.");
            e.printStackTrace();
        }
    }


    @FXML
    private void confirmarEnvio() {
        if (envioActual == null) {
            mostrarAlerta("Error", "Primero calcule la tarifa antes de confirmar.");
            return;
        }
        mostrarAlerta("Confirmación", "El envío ha sido registrado correctamente.");
    }

    @FXML
    private void cancelarEnvio() {
        mostrarAlerta("Cancelación", "El envío ha sido cancelado.");
        limpiarCampos();
    }


    private void limpiarCampos() {
        txtPeso.clear();
        txtVolumen.clear();
        cbOrigen.getSelectionModel().clearSelection();
        cbDestino.getSelectionModel().clearSelection();
        cbEstrategia.getSelectionModel().clearSelection();
        lblCostoTotal.setText("Costo total estimado: $");
        lblDesglose.setText("Desglose de tarifa:");
        chkSeguro.setSelected(false);
        chkPrioridad.setSelected(false);
        chkFirma.setSelected(false);
        chkRastreo.setSelected(false);
        chkNocturna.setSelected(false);
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
