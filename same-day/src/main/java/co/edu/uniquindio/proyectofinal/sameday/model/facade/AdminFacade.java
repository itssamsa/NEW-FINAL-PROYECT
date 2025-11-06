package co.edu.uniquindio.proyectofinal.sameday.model.facade;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.Envio;
import co.edu.uniquindio.proyectofinal.sameday.model.Repartidor;
import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoRepartidor;
import co.edu.uniquindio.proyectofinal.sameday.service.*;

import java.util.List;
import java.util.Optional;

public class AdminFacade {

    private final UsuarioService usuarioService;
    private final RepartidorService repartidorService;
    private final EnvioService envioService;

    public AdminFacade() {
        this.usuarioService = ModelFactory.getInstance().getUsuarioService();
        this.repartidorService = ModelFactory.getInstance().getRepartidorService();
        this.envioService = ModelFactory.getInstance().getEnvioService();
    }

    // --------- Gestión de Usuarios ---------
    public Usuario crearUsuario(Usuario u) { return usuarioService.crearUsuario(u); }
    public List<Usuario> listarUsuarios() { return usuarioService.listarTodos(); }
    public Usuario actualizarUsuario(Usuario u) { return usuarioService.actualizarUsuario(u); }
    public boolean eliminarUsuario(String id) { return usuarioService.eliminarUsuario(id); }

    // --------- Gestión de Repartidores ---------
    public Repartidor crearRepartidor(Repartidor r) { return repartidorService.crear(r); }
    public List<Repartidor> listarRepartidores() { return repartidorService.listar(); }
    public boolean eliminarRepartidor(String id) { return repartidorService.eliminar(id); }
    public void cambiarEstadoRepartidor(String id, EstadoRepartidor nuevoEstado) {
        Optional<Repartidor> opt = repartidorService.obtener(id);
        opt.ifPresent(r -> {
            r.setEstado(nuevoEstado);
            repartidorService.actualizar(r);
        });
    }

    // --------- Gestión de Envíos ---------
    public void asignarEnvioARepartidor(String idEnvio, String idRepartidor) {
        Optional<Envio> envioOpt = envioService.obtener(idEnvio);
        Optional<Repartidor> repOpt = repartidorService.obtener(idRepartidor);
        if (envioOpt.isPresent() && repOpt.isPresent()) {
            Repartidor r = repOpt.get();
            Envio e = envioOpt.get();
            r.getEnviosAsignados().add(e);
            e.setEstado(EstadoEnvio.ASIGNADO);
            envioService.actualizar(e);
            repartidorService.actualizar(r);
        }
    }

    public List<Envio> listarEnvios() { return envioService.listar(); }

    // --------- Métricas básicas (ejemplo) ---------
    public long contarEnviosEntregados() {
        return envioService.listar().stream()
                .filter(e -> e.getEstado() == EstadoEnvio.ENTREGADO)
                .count();
    }

    public long contarEnviosEnRuta() {
        return envioService.listar().stream()
                .filter(e -> e.getEstado() == EstadoEnvio.EN_RUTA)
                .count();
    }
}

