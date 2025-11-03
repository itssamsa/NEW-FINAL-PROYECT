package co.edu.uniquindio.proyectofinal.sameday.model;

import co.edu.uniquindio.proyectofinal.sameday.model.observer.ObservadorRepartidor;

public class Administrador implements ObservadorRepartidor {

    private String nombre;

    public Administrador(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizarEstado(Repartidor repartidor) {
        System.out.println("🔔 Notificación para " + nombre + ": "
                + "El repartidor " + repartidor.getNombre()
                + " cambió su estado a " + repartidor.getEstado());
    }
}

