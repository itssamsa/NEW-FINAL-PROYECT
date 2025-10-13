package co.edu.uniquindio.proyectofinal.sameday.model;

import co.edu.uniquindio.proyectofinal.sameday.model.enums.MetodoPago;

import java.time.LocalDateTime;

public class Pago {
    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private MetodoPago metodoPago;
    private String resultado;
    private Envio envio;

    public Pago(String idPago, double monto, MetodoPago metodoPago, Envio envio) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.envio = envio;
        this.fecha = LocalDateTime.now();
    }

    public String getIdPago() { return idPago; }
    public double getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public Envio getEnvio() { return envio; }

    @Override
    public String toString() {
        return "Pago{" + idPago + ", monto=" + monto + ", resultado=" + resultado + "}";
    }
}
