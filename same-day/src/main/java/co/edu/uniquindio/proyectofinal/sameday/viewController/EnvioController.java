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
    @FXML private ComboBox<Direccion> cbOrigen;
    @FXML private ComboBox<Direccion> cbDestino;

    @FXML private Label lblCostoTotal;
    @FXML private TextArea txtResumen;

    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkPrioridad;
    @FXML private CheckBox chkFirma;
    @FXML private CheckBox chkRastreo;
    @FXML private CheckBox chkNocturna;
    @FXML private CheckBox chkFragil;

    private Envio envioActual;
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        cbEstrategia.getItems().addAll("Por peso", "Por distancia", "Por prioridad");
        lblCostoTotal.setText("Costo total estimado: $");
        txtResumen.setText("Resumen del envío:");
    }

    @FXML
    private void cargarDireccionesUsuario() {

        if (txtIdUsuario.getText().isEmpty()) {
            mostrarAlerta("Advertencia", "Ingrese un ID de usuario.");
            return;
        }

        Optional<Usuario> usuarioOpt =
                ModelFactory.getInstance().getUsuarioService().obtenerPorId(txtIdUsuario.getText());

        if (usuarioOpt.isEmpty()) {
            mostrarAlerta("Error", "Usuario no encontrado.");
            return;
        }

        usuarioActual = usuarioOpt.get();

        cbOrigen.getItems().clear();
        cbDestino.getItems().clear();

        cbOrigen.getItems().addAll(usuarioActual.getDireccionesFrecuentes());
        cbDestino.getItems().addAll(usuarioActual.getDireccionesFrecuentes());
    }

    //OJO CON COORDENADAS


    private double[] parsearCoordenadas(Direccion d) {

        if (d == null || d.getCoordenadas() == null || d.getCoordenadas().trim().isEmpty()) {
            throw new IllegalArgumentException("Coordenadas vacías para la dirección: " + d.getAlias());
        }

        String raw = d.getCoordenadas().trim();
        raw = raw.replace("-", " ");
        raw = raw.replaceAll("\\s+", " ");
        String[] parts;
        if (raw.contains(",")) {
            parts = raw.split(",");
        } else {
            parts = raw.split(" ");
        }

        double lat, lon;

        if (parts.length == 1) {

            lat = Double.parseDouble(parts[0].trim());
            lon = 0;
        } else {
            lat = Double.parseDouble(parts[0].trim());
            lon = Double.parseDouble(parts[1].trim());
        }

        return new double[]{lat, lon};
    }
    private double calcularDistancia(Direccion d1, Direccion d2) {

        double[] c1 = parsearCoordenadas(d1);
        double[] c2 = parsearCoordenadas(d2);

        double x1 = c1[0], y1 = c1[1];
        double x2 = c2[0], y2 = c2[1];

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    //TARIFAS
    @FXML
    private void calcularTarifa() {
        try {

            if (usuarioActual == null) {
                mostrarAlerta("Error", "Primero debe cargar las direcciones del usuario.");
                return;
            }

            Direccion origen = cbOrigen.getValue();
            Direccion destino = cbDestino.getValue();

            if (origen == null || destino == null) {
                mostrarAlerta("Error", "Seleccione origen y destino.");
                return;
            }

            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());

            String idEnvio = String.format("E-%04d", new Random().nextInt(10000));

            envioActual = new Envio(idEnvio, origen, destino, peso, volumen, usuarioActual);

            double costoAdicional = 0;

            // Decorators
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
            if (chkFragil.isSelected()) {
                envioActual = new EnvioFragil(envioActual);
                costoAdicional += 7.0;
                envioActual.addServicioAdicional(ServicioAdicional.FRAGIL);
            }

            // Factory
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

            // Strategy
            EstrategiaTarifa estrategia;

            switch (cbEstrategia.getValue()) {
                case "Por distancia" -> {
                    estrategia = new TarifaPorDistancia();
                    double distancia = calcularDistancia(origen, destino);
                    envioActual.setDistancia(distancia);
                }
                case "Por prioridad" -> estrategia = new TarifaPorPrioridad();
                default -> estrategia = new TarifaPorPeso();
            }

            double costoBase = estrategia.calcularTarifa(envioActual);
            double costoFinal = costoBase + costoAdicional;

            envioActual.setCostoTotal(costoFinal);

            lblCostoTotal.setText("Costo total estimado: $" + costoFinal);

            txtResumen.setText(
                    "Usuario: " + usuarioActual.getNombreCompleto() +
                            "\nOrigen: " + origen.getAlias() +
                            "\nID Envío: " + idEnvio +
                            "\nDestino: " + destino.getAlias() +
                            "\nPeso: " + peso +
                            "\nVolumen: " + volumen +
                            "\nCosto base: $" + costoBase +
                            "\nServicios adicionales: " + envioActual.getServiciosAdicionales() +
                            "\nCosto adicional: $" + costoAdicional +
                            "\n----------------------------------" +
                            "\nCosto total: $" + costoFinal
            );

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un problema: " + e.getMessage());
        }
    }

    @FXML
    private void confirmarEnvio() {

        if (envioActual == null) {
            mostrarAlerta("Error", "Primero calcule la tarifa.");
            return;
        }

        ModelFactory.getInstance().getEnvioService().crear(envioActual);

        mostrarAlerta("Confirmación",
                "Envío registrado.\nID: " + envioActual.getIdEnvio());

        limpiarCampos();
    }

    @FXML
    private void cancelarEnvio() {
        limpiarCampos();
        mostrarAlerta("Cancelado", "El envío ha sido cancelado.");
    }

    private void limpiarCampos() {
        txtIdUsuario.clear();
        txtPeso.clear();
        txtVolumen.clear();
        cbOrigen.getItems().clear();
        cbDestino.getItems().clear();
        cbEstrategia.getSelectionModel().clearSelection();
        lblCostoTotal.setText("Costo total estimado: $");
        txtResumen.setText("Resumen del envío:");
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.show();
    }
}
