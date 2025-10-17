package co.edu.uniquindio.proyectofinal.sameday.model.decorator;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;

public class EnvioConRastreoPremium extends EnvioDecorator {
    public EnvioConRastreoPremium(Envio envioBase) {
        super(envioBase);
    }

    @Override
    public double getCosto() {
        return super.getCosto() + 5.0;
    }

    @Override
    public String toString() {
        return super.toString() + " + Rastreo Premium";
    }
}
