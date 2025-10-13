package co.edu.uniquindio.proyectofinal.sameday.model;

public class Direccion {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private String coordenadas;

    public Direccion(String idDireccion, String alias, String calle, String ciudad, String coordenadas) {
        this.idDireccion = idDireccion;
        this.alias = alias;
        this.calle = calle;
        this.ciudad = ciudad;
        this.coordenadas = coordenadas;
    }

    public String getIdDireccion() { return idDireccion; }
    public String getAlias() { return alias; }
    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getCoordenadas() { return coordenadas; }

    @Override
    public String toString() {
        return alias + " - " + calle + ", " + ciudad;
    }
}