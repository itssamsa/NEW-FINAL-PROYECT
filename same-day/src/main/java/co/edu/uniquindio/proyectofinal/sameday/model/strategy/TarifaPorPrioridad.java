package co.edu.uniquindio.proyectofinal.sameday.model.strategy;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

public class TarifaPorPrioridad implements EstrategiaTarifa {

    @Override
    public double calcularTarifa(Envio envio) {
        double costoBase = 15 + (envio.getPeso() * 1.5);
        if (envio.getServiciosAdicionales().contains(ServicioAdicional.PRIORIDAD)) {
            costoBase *= 1.25;
        }

        return costoBase;
    }

    @Override
    public String descripcion() {
        return "Cálculo basado en la prioridad del envío";
    }
}
