package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.*;
import co.edu.uniquindio.proyectofinal.sameday.model.decorator.*;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.*;
import co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod.*;
import co.edu.uniquindio.proyectofinal.sameday.model.strategy.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.Random;

public class EnvioController {

    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private ComboBox<String> cbEstrategia;
    @FXML private Label lblCostoTotal;
    @FXML private TextArea txtResumen;

    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkPrioridad;
    @FXML private CheckBox chkFirma;
    @FXML private CheckBox chkRastreo;
    @FXML private CheckBox chkNocturna;

    private Envio envioActual;

    @FXML
    public void initialize() {
        cbEstrategia.getItems().addAll("Por peso", "Por distancia", "Por prioridad");
        lblCostoTotal.setText("Costo total estimado: $");
        txtResumen.setText("Resumen del envío:");
    }

    @FXML
    private void calcularTarifa() {
        try {
            if (txtIdUsuario.getText().isEmpty()) {
                mostrarAlerta("Error", "Debe ingresar el ID del usuario.");
                return;
            }

            // Buscar usuario registrado por ID
            Optional<Usuario> usuarioOpt = ModelFactory.getInstance()
                    .getUsuarioService().obtenerPorId(txtIdUsuario.getText());

            if (usuarioOpt.isEmpty()) {
                mostrarAlerta("Error", "No se encontró ningún usuario con ese ID registrado.");
                return;
            }

            Usuario usuario = usuarioOpt.get();

            // Direcciones predeterminadas (por ahora)
            Direccion origen = new Direccion("D1", "Bodega Central", "Calle 10 #20-30", "Armenia", "0,0");
            Direccion destino = new Direccion("D2", "Destino Cliente", "Carrera 15 #40-22", "Cali", "0,0");

            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());

            String idEnvio = String.format("E-%04d", new Random().nextInt(10000));

            envioActual = new Envio(idEnvio, origen, destino, peso, volumen, usuario);

            double costoAdicional = 0.0;

            //decarotor
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

            // factory
            if (chkPrioridad.isSelected()) {
                ServicioAdicionalFactory prioridadFactory = new PrioridadFactory();
                envioActual.addServicioAdicional(prioridadFactory.crearServicio());
                costoAdicional += 7.0;
            }
            if (chkFirma.isSelected()) {
                ServicioAdicionalFactory firmaFactory = new FirmaRequeridaFactory();
                envioActual.addServicioAdicional(firmaFactory.crearServicio());
                costoAdicional += 4.0;
            }

            // strategy
            EstrategiaTarifa estrategia;
            switch (cbEstrategia.getValue()) {
                case "Por distancia" -> estrategia = new TarifaPorDistancia();
                case "Por prioridad" -> estrategia = new TarifaPorPrioridad();
                default -> estrategia = new TarifaPorPeso();
            }

            double costoBase = estrategia.calcularTarifa(envioActual);
            double costoFinal = costoBase + costoAdicional;

            envioActual.setCostoTotal(costoFinal);

            lblCostoTotal.setText("Costo total estimado: $" + costoFinal);
            txtResumen.setText(
                    "Usuario: " + usuario.getNombreCompleto() +
                            "\nID Usuario: " + usuario.getIdUsuario() +
                            "\nID Envío: " + idEnvio +
                            "\nEstrategia: " + estrategia.descripcion() +
                            "\nCosto base: $" + costoBase +
                            "\nServicios adicionales: " + envioActual.getServiciosAdicionales() +
                            "\nCosto adicional: $" + costoAdicional +
                            "\n----------------------------------" +
                            "\nCosto total: $" + costoFinal
            );

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El peso y volumen deben ser valores numéricos.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un problema al calcular la tarifa.");
            e.printStackTrace();
        }
    }

    @FXML
    private void confirmarEnvio() {
        if (envioActual == null) {
            mostrarAlerta("Error", "Primero calcule la tarifa antes de confirmar.");
            return;
        }

        ModelFactory.getInstance().getEnvioService().crear(envioActual);

        mostrarAlerta("Confirmación",
                "✅ Envío registrado correctamente.\n" +
                        "ID de Envío: " + envioActual.getIdEnvio() + "\n" +
                        "Asociado al usuario: " + envioActual.getUsuario().getNombreCompleto() + ".");

        limpiarCampos();
    }

    @FXML
    private void cancelarEnvio() {
        limpiarCampos();
        mostrarAlerta("Cancelación", "El envío ha sido cancelado.");
    }

    private void limpiarCampos() {
        txtIdUsuario.clear();
        txtPeso.clear();
        txtVolumen.clear();
        cbEstrategia.getSelectionModel().clearSelection();
        lblCostoTotal.setText("Costo total estimado: $");
        txtResumen.setText("Resumen del envío:");
        chkSeguro.setSelected(false);
        chkPrioridad.setSelected(false);
        chkFirma.setSelected(false);
        chkRastreo.setSelected(false);
        chkNocturna.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
