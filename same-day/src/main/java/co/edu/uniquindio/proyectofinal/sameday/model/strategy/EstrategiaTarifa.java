package co.edu.uniquindio.proyectofinal.sameday.model.strategy;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;

public interface EstrategiaTarifa {
    double calcularTarifa(Envio envio);
    String descripcion();
}
