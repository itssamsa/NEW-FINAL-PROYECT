package co.edu.uniquindio.proyectofinal.sameday.model.decorator;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

public class EnvioFragil extends EnvioDecorator {
    public EnvioFragil(Envio envioBase) {
        super(envioBase);
        envioBase.addServicioAdicional(ServicioAdicional.FRAGIL);

    }

    @Override
    public double getCosto() {
        return super.getCosto() + 7.0;
    }

    @Override
    public String toString() {
        return super.toString() + " + Entrega Nocturna";
    }
}
