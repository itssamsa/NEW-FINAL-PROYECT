package co.edu.uniquindio.proyectofinal.sameday.service;

import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;

import java.util.*;

public class EnvioService {
    private final Map<String, Envio> repo = new HashMap<>();

    public Envio crear(Envio e) {
        repo.put(e.getIdEnvio(), e);
        return e;
    }

    public Optional<Envio> obtener(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    public List<Envio> listar() {
        return new ArrayList<>(repo.values());
    }

    public Envio actualizar(Envio e) {
        repo.put(e.getIdEnvio(), e);
        return e;
    }

    public boolean eliminar(String id) {
        return repo.remove(id) != null;
    }

    public boolean asignarRepartidor(String envioId, Repartidor r) {
        Envio e = repo.get(envioId);
        if (e == null) return false;
        e.setRepartidor(r);
        e.setEstado(EstadoEnvio.ASIGNADO);
        r.getEnviosAsignados().add(e);
        return true;
    }

    public boolean cambiarEstado(String envioId, EstadoEnvio estado) {
        Envio e = repo.get(envioId);
        if (e == null) return false;
        e.setEstado(estado);
        return true;
    }


    public List<Envio> listarPorUsuario(String idUsuario) {
        return repo.values().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().getIdUsuario().equals(idUsuario))
                .toList();
    }

    public List<Envio> obtenerPorUsuario(String idUsuario) {
        List<Envio> resultado = new ArrayList<>();
        for (Envio e : repo.values()) {
            if (e != null && e.getUsuario() != null && idUsuario.equals(e.getUsuario().getIdUsuario())) {
                resultado.add(e);
            }
        }
        return resultado;
    }
}
