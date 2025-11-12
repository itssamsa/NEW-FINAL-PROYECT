package co.edu.uniquindio.proyectofinal.sameday.model.adapter;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GenerarReporteCSV implements GenerarReporte {

    @Override
    public void generar(List<Envio> listaEnvios, String rutaSalida) {
        System.out.println(rutaSalida);

        try {
            crearDirectorio(rutaSalida);
            String contenidoCSV = generarArchivoCSV(listaEnvios, rutaSalida);
            mostrarDialogoCSV(contenidoCSV, rutaSalida);
            System.out.println("");

        } catch (Exception e) {
        }
    }

    private String generarArchivoCSV(List<Envio> listaEnvios, String rutaSalida) throws IOException {
        StringBuilder contenido = new StringBuilder();
        contenido.append("ID_ENVIO,USUARIO,ESTADO\n");

        for (Envio envio : listaEnvios) {
            String linea = safeString(envio.getIdEnvio()) + "," +
                    escapeCSV(safeString(envio.getUsuario() != null ? envio.getUsuario().getNombreCompleto() : "N/A")) + "," +
                    escapeCSV(safeString(envio.getEstado() != null ? envio.getEstado().toString() : "N/A"));
            contenido.append(linea).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida))) {
            writer.write(contenido.toString());
        }

        return contenido.toString();
    }

    private void mostrarDialogoCSV(String contenidoCSV, String rutaSalida) {
        javafx.application.Platform.runLater(() -> {
            TextArea textArea = new TextArea(contenidoCSV);
            textArea.setEditable(false);
            textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
            textArea.setPrefSize(600, 300);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reporte CSV Generado");
            alert.setHeaderText("Archivo guardado en: " + rutaSalida);
            alert.getDialogPane().setContent(textArea);
            alert.getDialogPane().setPrefSize(650, 400);
            alert.showAndWait();
        });
    }

    private String safeString(Object value) {
        return value == null ? "" : value.toString();
    }

    private String escapeCSV(String valor) {
        if (valor == null || valor.isEmpty()) return "";
        if (valor.contains(",") || valor.contains("\"")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }

    private void crearDirectorio(String rutaSalida) throws IOException {
        java.nio.file.Path path = Paths.get(rutaSalida);
        java.nio.file.Path directorio = path.getParent();
        if (directorio != null && !Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }
    }

}