package co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo;

import co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo.enums.Estado;

import java.time.LocalDate;
import java.util.ArrayList;

public class Envio {
    private String idEnvio;
    private String idUsuario;
    private Direccion origen;
    private Direccion destino;
    private double pesoKg;
    private Dimenciones dimensiones;
    private double costo;
    private Estado estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaEstimadoEntrega;
    private ArrayList<String> servicioAdicional=new ArrayList<>();

}