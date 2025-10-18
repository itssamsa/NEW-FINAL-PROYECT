package co.edu.uniquindio.proyectofinal.sameday.model.strategy;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;

public class TarifaPorDistancia implements EstrategiaTarifa {

    @Override
    public double calcularTarifa(Envio envio) {
        Direccion origen = envio.getOrigen();
        Direccion destino = envio.getDestino();

        double distanciaSimulada = Math.abs(origen.getAlias().hashCode() - destino.getAlias().hashCode()) % 100;
        return 10 + distanciaSimulada * 0.5;
    }

    @Override
    public String descripcion() {
        return "Cálculo basado en distancia simulada entre origen y destino";
    }
}
