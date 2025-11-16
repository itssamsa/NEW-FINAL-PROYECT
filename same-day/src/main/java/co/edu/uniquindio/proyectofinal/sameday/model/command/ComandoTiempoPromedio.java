package co.edu.uniquindio.proyectofinal.sameday.model.command;

public class ComandoTiempoPromedio implements ComandoMetrica {
    @Override
    public void ejecutar() {
        System.out.println("Calculando tiempo promedio de entrega...");
    }
}