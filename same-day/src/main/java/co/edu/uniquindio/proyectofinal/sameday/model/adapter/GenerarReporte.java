package co.edu.uniquindio.proyectofinal.sameday.model.adapter;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import java.util.List;

public interface GenerarReporte {
    void generar(List<Envio> listaEnvios, String rutaSalida);
}
