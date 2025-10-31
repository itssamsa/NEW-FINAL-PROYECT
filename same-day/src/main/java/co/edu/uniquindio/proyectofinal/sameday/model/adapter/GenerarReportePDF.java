package co.edu.uniquindio.proyectofinal.sameday.model.adapter;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import java.util.List;

public class GenerarReportePDF implements GenerarReporte {

    @Override
    public void generar(List<Envio> listaEnvios, String rutaSalida) {
        System.out.println("📄 Generando reporte PDF en: " + rutaSalida);
        listaEnvios.forEach(envio ->
                System.out.println(" - Envío ID: " + envio.getIdEnvio() + " | Estado: " + envio.getEstado()));
    }
}
