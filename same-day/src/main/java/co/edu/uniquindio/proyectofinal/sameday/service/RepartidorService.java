package co.edu.uniquindio.proyectofinal.sameday.service;

import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;

import java.util.*;

public class RepartidorService {
    private final Map<String, Repartidor> repo = new HashMap<>();

    public Repartidor crear(Repartidor r) {
        repo.put(r.getIdRepartidor(), r); return r;
    }
    public Optional<Repartidor> obtener(String id) { return Optional.ofNullable(repo.get(id)); }
    public List<Repartidor> listar() { return new ArrayList<>(repo.values()); }
    public Repartidor actualizar(Repartidor r) { repo.put(r.getIdRepartidor(), r); return r; }
    public boolean eliminar(String id) { return repo.remove(id) != null; }
}