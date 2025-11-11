package co.edu.uniquindio.proyectofinal.sameday.model.command;

public class ComandoServiciosMasUsados implements ComandoMetrica {
    @Override
    public void ejecutar() {
        System.out.println("Mostrando servicios adicionales más usados...");
    }
}

