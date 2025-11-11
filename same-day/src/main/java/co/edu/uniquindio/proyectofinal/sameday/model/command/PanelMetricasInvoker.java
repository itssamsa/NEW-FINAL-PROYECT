package co.edu.uniquindio.proyectofinal.sameday.model.command;

public class PanelMetricasInvoker {
    private ComandoMetrica comandoActual;

    public void setComando(ComandoMetrica comando) {
        this.comandoActual = comando;
    }

    public void ejecutarComando() {
        if (comandoActual != null) {
            comandoActual.ejecutar();
        }
    }
}
