package co.edu.uniquindio.proyectofinal.sameday.viewController;

import co.edu.uniquindio.proyectofinal.sameday.model.command.*;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

import java.util.Map;

public class PanelMetricasController {

    @FXML private BarChart<String, Number> bcServicios;
    @FXML private PieChart pcIngresos;
    @FXML private PieChart pcIncidencias;
    @FXML private Button btnActualizar;

    private final PanelMetricasInvoker invoker = new PanelMetricasInvoker();

    @FXML
    public void initialize() {
        actualizarMetricas();

        if (btnActualizar != null) {
            btnActualizar.setOnAction(event -> actualizarMetricas());
        }
    }

    // ---------------- METRICAS ---------------- //

    private void mostrarServiciosMasUsados() {
        ComandoServiciosMasUsados comando = new ComandoServiciosMasUsados();
        invoker.setComando(comando);
        invoker.ejecutarComando();

        bcServicios.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Map<ServicioAdicional, Integer> datos = comando.getResultados();

        datos.forEach((servicio, cantidad) ->
                series.getData().add(new XYChart.Data<>(servicio.toString(), cantidad))
        );

        bcServicios.getData().add(series);
    }

    private void mostrarIngresosPorPeriodo() {
        ComandoIngresosPorPeriodo comando = new ComandoIngresosPorPeriodo();
        invoker.setComando(comando);
        invoker.ejecutarComando();

        pcIngresos.getData().clear();
        comando.getIngresosPorPeriodo().forEach((mes, ingreso) ->
                pcIngresos.getData().add(new PieChart.Data(mes, ingreso))
        );
    }

    private void mostrarIncidencias() {
        ComandoIncidenciasPorZona comando = new ComandoIncidenciasPorZona();
        invoker.setComando(comando);
        invoker.ejecutarComando();

        pcIncidencias.getData().clear();
        comando.getResultados().forEach((tipo, cantidad) ->
                pcIncidencias.getData().add(new PieChart.Data(tipo, cantidad))
        );
    }

    // Método general para recargar todo
    public void actualizarMetricas() {
        mostrarServiciosMasUsados();
        mostrarIngresosPorPeriodo();
        mostrarIncidencias();
    }
}
