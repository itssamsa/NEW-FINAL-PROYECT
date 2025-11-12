package co.edu.uniquindio.proyectofinal.sameday.model.adapter;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class GenerarReportePDF implements GenerarReporte {

    @Override
    public void generar(List<Envio> listaEnvios, String rutaSalida) {
        System.out.println("📄 Generando reporte PDF en: " + rutaSalida);

        try {
            // Crear contenido del PDF
            VBox contenido = crearContenidoPDF(listaEnvios);

            // Generar PDF usando JavaFX Print API
            boolean exito = generarPDF(contenido);

            if (exito) {
                System.out.println("✅ PDF generado exitosamente!");
            } else {
                System.out.println("⚠️  Generación de PDF cancelada por el usuario");
            }

        } catch (Exception e) {
            System.err.println("❌ Error generando PDF: " + e.getMessage());
            // Fallback: mostrar información en consola
            mostrarEnConsola(listaEnvios);
        }
    }

    private VBox crearContenidoPDF(List<Envio> listaEnvios) {
        VBox contenido = new VBox(15);
        contenido.setStyle("-fx-padding: 30; -fx-background-color: white;");

        // Título principal
        Label titulo = new Label("REPORTE DE ENVÍOS - SAME DAY");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Información del reporte
        Label info = new Label("Total de envíos: " + listaEnvios.size() +
                " | Fecha: " + java.time.LocalDate.now());
        info.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        contenido.getChildren().addAll(titulo, info);

        // Agregar línea separadora
        Label separador = new Label("────────────────────────────────────────");
        separador.setStyle("-fx-text-fill: #bdc3c7;");
        contenido.getChildren().add(separador);

        // Agregar cada envío
        for (Envio envio : listaEnvios) {
            String textoEnvio = String.format("📦 %s | 👤 %s | 📍 %s",
                    envio.getIdEnvio(),
                    envio.getUsuario().getNombreCompleto(),
                    envio.getEstado()
            );

            Label labelEnvio = new Label(textoEnvio);
            labelEnvio.setStyle("-fx-font-size: 11px; -fx-padding: 5px 0;");
            contenido.getChildren().add(labelEnvio);
        }

        return contenido;
    }

    private boolean generarPDF(Node contenido) {
        try {
            PrinterJob job = PrinterJob.createPrinterJob();

            if (job != null) {
                // Mostrar diálogo de impresión
                boolean proceder = job.showPrintDialog(null);

                if (proceder) {
                    // Intentar imprimir (esto abrirá el guardado como PDF)
                    boolean exito = job.printPage(contenido);
                    if (exito) {
                        job.endJob();
                        return true;
                    }
                }
            }
            return false;

        } catch (Exception e) {
            throw new RuntimeException("Error en la generación del PDF: " + e.getMessage(), e);
        }
    }

    private void mostrarEnConsola(List<Envio> listaEnvios) {
        System.out.println("\n=== REPORTE DE ENVÍOS (Consola) ===");
        System.out.println("Total envíos: " + listaEnvios.size());
        System.out.println("Fecha: " + java.time.LocalDate.now());
        System.out.println("-----------------------------------");

        listaEnvios.forEach(envio -> {
            System.out.printf("📦 %s | 👤 %s | 📍 %s%n",
                    envio.getIdEnvio(),
                    envio.getUsuario().getNombreCompleto(),
                    envio.getEstado()
            );
        });

        System.out.println("===================================\n");
    }
}