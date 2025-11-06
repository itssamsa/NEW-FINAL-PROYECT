package co.edu.uniquindio.proyectofinal.sameday.model;

import co.edu.uniquindio.proyectofinal.sameday.model.observer.ObservadorRepartidor;

public class Administrador implements ObservadorRepartidor {

    private String idAdministrador;
    private String nombre;

    public Administrador(String idAdministrador, String nombre) {
        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
    }

    @Override
    public void actualizarEstado(Repartidor repartidor) {
        System.out.println("🔔 Notificación para " + nombre + ": "
                + "El repartidor " + repartidor.getNombre()
                + " cambió su estado a " + repartidor.getEstado());
    }

    public String getIdAdministrador() { return idAdministrador; }
    public String getNombre() { return nombre; }
}


