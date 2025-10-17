package co.edu.uniquindio.proyectofinal.sameday.model.factoryMethod;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

public class PrioridadFactory extends ServicioAdicionalFactory {
    @Override
    public ServicioAdicional crearServicio() {
        return ServicioAdicional.PRIORIDAD;
    }
}
