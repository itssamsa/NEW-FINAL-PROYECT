package co.edu.uniquindio.proyectofinal.sameday.model.command;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;

import java.time.Duration;
import java.util.List;

public class ComandoTiempoPromedio implements ComandoMetrica {

    private double tiempoPromedioHoras = 0.0;

    @Override
    public void ejecutar() {

        List<Envio> envios = ModelFactory.getInstance()
                .getEnvioService()
                .listar();

        long totalHoras = 0;
        int entregados = 0;

        for (Envio e : envios) {
            if (e.getEstado() == EstadoEnvio.ENTREGADO &&
                    e.getFechaCreacion() != null &&
                    e.getFechaEstimadaEntrega() != null) {

                Duration d = Duration.between(
                        e.getFechaCreacion(),
                        e.getFechaEstimadaEntrega()
                );

                totalHoras += d.toHours();
                entregados++;
            }
        }

        if (entregados > 0) {
            tiempoPromedioHoras = (double) totalHoras / entregados;
        } else {
            tiempoPromedioHoras = 0.0;
        }
    }

    public double getTiempoPromedioHoras() {
        return tiempoPromedioHoras;
    }
}
