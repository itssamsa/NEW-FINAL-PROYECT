package co.edu.uniquindio.proyectofinal.sameday.model.decorator;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

public class EnvioConSeguro extends EnvioDecorator {
    public EnvioConSeguro(Envio envioBase) {
        super(envioBase);
        envioBase.addServicioAdicional(ServicioAdicional.SEGURO);
    }

    @Override
    public double getCosto() {
        return super.getCosto() + 10.0;
    }

    @Override
    public String toString() {
        return super.toString() + " + Seguro";
    }
}
