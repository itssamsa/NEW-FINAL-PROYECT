package co.edu.uniquindio.proyectofinal.sameday.model.command;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandoIngresosPorPeriodo implements ComandoMetrica {

    private Map<String, Double> ingresosPorPeriodo;

    @Override
    public void ejecutar() {
        ingresosPorPeriodo = new HashMap<>();
        List<Envio> envios = ModelFactory.getInstance().getEnvioService().listar();
        for (Envio e : envios) {
            String mes = e.getFechaCreacion().getMonth().toString();
            ingresosPorPeriodo.put(mes, ingresosPorPeriodo.getOrDefault(mes, 0.0) + e.getCostoTotal());
        }
    }

    public Map<String, Double> getIngresosPorPeriodo() {
        return ingresosPorPeriodo;
    }
}
