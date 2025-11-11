package co.edu.uniquindio.proyectofinal.sameday.model.command;

public class ComandoIncidenciasPorZona implements ComandoMetrica {
    @Override
    public void ejecutar() {
        System.out.println("Analizando incidencias por zona...");
    }
}
