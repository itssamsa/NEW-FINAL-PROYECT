package co.edu.uniquindio.proyectofinal.sameday.model.command;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandoServiciosMasUsados implements ComandoMetrica {

    private Map<ServicioAdicional, Integer> resultados;

    @Override
    public void ejecutar() {
        resultados = new HashMap<>();
        List<Envio> envios = ModelFactory.getInstance().getEnvioService().listar();
        for (Envio e : envios) {
            for (ServicioAdicional s : e.getServiciosAdicionales()) {
                resultados.put(s, resultados.getOrDefault(s, 0) + 1);
            }
        }
    }

    public Map<ServicioAdicional, Integer> getResultados() {
        return resultados;
    }
}
