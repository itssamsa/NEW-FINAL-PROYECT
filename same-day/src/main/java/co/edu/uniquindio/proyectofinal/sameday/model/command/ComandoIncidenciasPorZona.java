package co.edu.uniquindio.proyectofinal.sameday.model.command;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandoIncidenciasPorZona implements ComandoMetrica {

    private Map<String, Integer> resultados = new HashMap<>();

    @Override
    public void ejecutar() {
        List<Envio> envios = ModelFactory.getInstance().getEnvioService().listar();

        int incidencias = 0;
        int normales = 0;

        for (Envio e : envios) {
            if (e.getEstado() == EstadoEnvio.INCIDENCIA) {
                incidencias++;
            } else {
                normales++;
            }
        }

        resultados.put("Incidencias", incidencias);
        resultados.put("Sin Incidencias", normales);
    }

    public Map<String, Integer> getResultados() {
        return resultados;
    }
}
