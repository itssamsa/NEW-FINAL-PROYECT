package co.edu.uniquindio.proyectofinal.sameday.model.decorator;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;

public class EnvioConEntregaNocturna extends EnvioDecorator {
    public EnvioConEntregaNocturna(Envio envioBase) {
        super(envioBase);
    }

    @Override
    public double getCosto() {
        return super.getCosto() + 8.0;
    }

    @Override
    public String toString() {
        return super.toString() + " + Entrega Nocturna";
    }
}
