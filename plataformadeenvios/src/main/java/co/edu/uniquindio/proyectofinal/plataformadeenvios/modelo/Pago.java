package co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.enums.MetodoPago;
import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.enums.Resultado;

import java.time.LocalDate;

public class Pago {
    private String idPago;
    private String idEnvio;
    private double monto;
    private LocalDate fecha;
    private MetodoPago metodo;
    private Resultado resultado;
}