package co.edu.uniquindio.proyectofinal.sameday.service;

import co.edu.uniquindio.proyectofinal.sameday.model.Pago;

import java.util.*;

public class PagoService {
    private final Map<String, Pago> repo = new HashMap<>();

    public Pago registrar(Pago p) {
        p.setResultado("Aprobado"); // simulación
        repo.put(p.getIdPago(), p);
        return p;
    }

    public Optional<Pago> obtener(String id) { return Optional.ofNullable(repo.get(id)); }
    public List<Pago> listar() { return new ArrayList<>(repo.values()); }
}