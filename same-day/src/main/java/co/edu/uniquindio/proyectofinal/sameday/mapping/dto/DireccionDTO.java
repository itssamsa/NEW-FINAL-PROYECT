package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

public class DireccionDTO {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private String coordenadas;

    public DireccionDTO() {}

    public String getIdDireccion() { return idDireccion; }
    public void setIdDireccion(String idDireccion) { this.idDireccion = idDireccion; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getCoordenadas() { return coordenadas; }
    public void setCoordenadas(String coordenadas) { this.coordenadas = coordenadas; }
}
