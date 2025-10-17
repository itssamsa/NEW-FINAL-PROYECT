package co.edu.uniquindio.proyectofinal.sameday.model.decorator;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;

public abstract class EnvioDecorator extends Envio {
    protected Envio envioBase;

    public EnvioDecorator(Envio envioBase) {
        super(envioBase.getIdEnvio(), envioBase.getOrigen(), envioBase.getDestino(),
                envioBase.getPeso(), envioBase.getVolumen(), envioBase.getUsuario());
        this.envioBase = envioBase;
    }

    @Override
    public double getCosto() {
        return envioBase.getCosto();
    }

    @Override
    public String toString() {
        return envioBase.toString();
    }
}

