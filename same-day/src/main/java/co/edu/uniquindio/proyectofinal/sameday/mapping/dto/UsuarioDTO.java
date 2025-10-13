package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

import java.util.List;

public class UsuarioDTO {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private List<DireccionDTO> direcciones;
    private List<String> metodosPago;

    public UsuarioDTO() {}

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<DireccionDTO> getDirecciones() { return direcciones; }
    public void setDirecciones(List<DireccionDTO> direcciones) { this.direcciones = direcciones; }
    public List<String> getMetodosPago() { return metodosPago; }
    public void setMetodosPago(List<String> metodosPago) { this.metodosPago = metodosPago; }
}
