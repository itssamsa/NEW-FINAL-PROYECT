package co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo;

import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica {
    private  static EmpresaLogistica instancia;
    private String nombre;
    private List<Usuario> usuarios;
    private List<Repartidor> repartidors;
    private List<Envio>envios;
    private List<Direccion> direcciones;
    private List<Tarifa> tarifas;
    private List<Pago> pagos;
    private List<Incidencia> incidencias;
    private EmpresaLogistica(String nombre){
        this.nombre=nombre;
        this.usuarios=new ArrayList<>();
        this.repartidors= new ArrayList<>();
        this.envios= new ArrayList<>();
        this.direcciones=new ArrayList<>();
        this.tarifas=new ArrayList<>();
        this.pagos=new ArrayList<>();
        this.incidencias=new ArrayList<>();
    }
    public static EmpresaLogistica getInstance(String nombre){
        if(instancia ==null){
            instancia= new EmpresaLogistica(nombre);
        }
        return instancia;
    }

    public boolean crearUsuario(String idUsuario,
                                String nombre,
                                String correo,
                                String telefono,
                                Direccion direccion){
        Usuario usuarioEncontrado= obtenerUsuario(idUsuario);
        if(usuarioEncontrado == null){
            Usuario usuario= getBuildUsuario(idUsuario,nombre,correo,telefono,direccion);//4.se crea el usuario con el metodo Builder
            getUsuarios().add(usuario);
            return true;
        }
        return false;

    }
    public Usuario obtenerUsuario(String IdUsuario){
        Usuario usuario = null;
        for(Usuario usuario1: getUsuarios()){
            if(usuario1.getIdUsuario().equals(IdUsuario)){
                usuario = usuario1;
                break;
            }
        }
        return usuario;
    }
    //3. se usa Builder para crear un usuario
    private Usuario getBuildUsuario(String idUsuario, String nombre, String correo,
                                    String telefono, Direccion direccion) {
        return Usuario.builder()
                .idUsuario(idUsuario)
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .direccion(direccion)
                .build();

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Repartidor> getRepartidors() {
        return repartidors;
    }

    public void setRepartidors(List<Repartidor> repartidors) {
        this.repartidors = repartidors;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}