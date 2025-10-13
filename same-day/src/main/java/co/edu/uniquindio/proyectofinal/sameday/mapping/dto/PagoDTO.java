package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.MetodoPago;
import java.time.LocalDateTime;

public class PagoDTO {
    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private MetodoPago metodoPago;
    private String resultado;
    private String idEnvio;

    public PagoDTO() {}

    public String getIdPago() { return idPago; }
    public void setIdPago(String idPago) { this.idPago = idPago; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
}
