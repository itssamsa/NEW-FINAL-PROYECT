package co.edu.uniquindio.proyectofinal.sameday.service;

import co.edu.uniquindio.proyectofinal.sameday.model.Tarifa;

import java.util.*;

public class TarifaService {
    private final Map<String, Tarifa> repo = new HashMap<>();

    public Tarifa crear(Tarifa t) { repo.put(t.getIdTarifa(), t); return t; }
    public Optional<Tarifa> obtener(String id) { return Optional.ofNullable(repo.get(id)); }
    public List<Tarifa> listar() { return new ArrayList<>(repo.values()); }

    public double calcularCostoEstimado(Tarifa tarifa, double peso, double volumen, double km, boolean prioridad, boolean seguro) {
        double c = tarifa.getCostoBase() + (peso * tarifa.getCostoPorKg()) + (volumen * tarifa.getCostoPorM3()) + (km * tarifa.getCostoPorKm());
        if (prioridad) c += tarifa.getRecargoPrioridad();
        if (seguro) c += tarifa.getRecargoSeguro();
        return c;
    }
}