package co.edu.uniquindio.proyectofinal.sameday.model.observer;

public interface SujetoRepartidor {
    void agregarObservador(ObservadorRepartidor observador);
    void eliminarObservador(ObservadorRepartidor observador);
    void notificarObservadores();
}