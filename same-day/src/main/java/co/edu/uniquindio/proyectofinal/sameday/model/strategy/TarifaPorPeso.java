package co.edu.uniquindio.proyectofinal.sameday.model.strategy;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;

public class TarifaPorPeso implements EstrategiaTarifa {

    @Override
    public double calcularTarifa(Envio envio) {
        return 5 + (envio.getPeso() * 2);
    }

    @Override
    public String descripcion() {
        return "Cálculo basado en el peso del envío";
    }
}
