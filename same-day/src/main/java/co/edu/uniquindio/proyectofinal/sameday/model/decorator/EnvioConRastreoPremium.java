package co.edu.uniquindio.proyectofinal.sameday.model.decorator;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

public class EnvioConRastreoPremium extends EnvioDecorator {
    public EnvioConRastreoPremium(Envio envioBase) {
        super(envioBase);
        envioBase.addServicioAdicional(ServicioAdicional.RASTREO_PREMIUM);
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
