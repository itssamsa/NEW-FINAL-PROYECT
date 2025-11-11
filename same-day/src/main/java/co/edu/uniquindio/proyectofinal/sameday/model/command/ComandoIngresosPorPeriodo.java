package co.edu.uniquindio.proyectofinal.sameday.model.command;

public class ComandoIngresosPorPeriodo implements ComandoMetrica {
    @Override
    public void ejecutar() {
        System.out.println("Calculando ingresos por período...");
    }
}
