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
    @FXML private Button btnActualizar; // botón para actualizar métricas

    private final PanelMetricasInvoker invoker = new PanelMetricasInvoker();

    @FXML
    public void initialize() {
        // Inicialmente mostramos los gráficos (posiblemente vacíos)
        mostrarServiciosMasUsados();
        mostrarIngresosPorPeriodo();

        // Configurar botón para recargar métricas
        if (btnActualizar != null) {
            btnActualizar.setOnAction(event -> actualizarMetricas());
        }
    }

    private void mostrarServiciosMasUsados() {
        ComandoServiciosMasUsados comando = new ComandoServiciosMasUsados();
        invoker.setComando(comando);
        invoker.ejecutarComando();

        bcServicios.getData().clear(); // Limpiar datos anteriores
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Map<ServicioAdicional, Integer> datos = comando.getResultados();

        datos.forEach((servicio, cantidad) -> {
            series.getData().add(new XYChart.Data<>(servicio.toString(), cantidad));
        });

        bcServicios.getData().add(series);
    }

    private void mostrarIngresosPorPeriodo() {
        ComandoIngresosPorPeriodo comando = new ComandoIngresosPorPeriodo();
        invoker.setComando(comando);
        invoker.ejecutarComando();

        pcIngresos.getData().clear(); // Limpiar datos anteriores
        Map<String, Double> datos = comando.getIngresosPorPeriodo();
        datos.forEach((mes, ingreso) -> {
            PieChart.Data slice = new PieChart.Data(mes, ingreso);
            pcIngresos.getData().add(slice);
        });
    }

    // Método público para recargar métricas en cualquier momento
    public void actualizarMetricas() {
        mostrarServiciosMasUsados();
        mostrarIngresosPorPeriodo();
    }
}
