package co.edu.uniquindio.proyectofinal.sameday;

import co.edu.uniquindio.proyectofinal.sameday.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal.sameday.model.*;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.EstadoEnvio;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.MetodoPago;
import co.edu.uniquindio.proyectofinal.sameday.model.enums.ServicioAdicional;
import co.edu.uniquindio.proyectofinal.sameday.service.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class EmpresaTransporte {
    private final UsuarioService usuarioService;
    private final RepartidorService repartidorService;
    private final EnvioService envioService;
    private final TarifaService tarifaService;
    private final PagoService pagoService;

    public EmpresaTransporte() {
        ModelFactory factory = ModelFactory.getInstance();
        this.usuarioService = factory.getUsuarioService();
        this.repartidorService = factory.getRepartidorService();
        this.envioService = factory.getEnvioService();
        this.tarifaService = factory.getTarifaService();
        this.pagoService = factory.getPagoService();
    }

    public void registrarUsuario(Usuario u) {
        usuarioService.crearUsuario(u);
    }

    public Optional<Usuario> obtenerUsuario(String id) {
        return usuarioService.obtenerPorId(id);
    }

    public Envio crearEnvio(Direccion origen, Direccion destino, double peso, double volumen, Usuario usuario,
                            Tarifa tarifa, double km, List<ServicioAdicional> servicios) {
        Envio e = new Envio(UUID.randomUUID().toString(), origen, destino, peso, volumen, usuario);
        boolean prioridad = servicios != null && servicios.contains(ServicioAdicional.PRIORIDAD);
        boolean seguro = servicios != null && servicios.contains(ServicioAdicional.SEGURO);
        double costo = tarifaService.calcularCostoEstimado(tarifa, peso, volumen, km, prioridad, seguro);
        e.setCosto(costo);
        if (servicios != null) {
            servicios.forEach(e::addServicioAdicional);
        }
        envioService.crear(e);
        return e;
    }

    public boolean asignarRepartidorAEnvio(String envioId, String repartidorId) {
        var rOpt = repartidorService.obtener(repartidorId);
        if (rOpt.isEmpty()) return false;
        return envioService.asignarRepartidor(envioId, rOpt.get());
    }

    public Envio actualizarEstadoEnvio(String envioId, EstadoEnvio estado) {
        var opt = envioService.obtener(envioId);
        if (opt.isEmpty()) return null;
        envioService.cambiarEstado(envioId, estado);
        return envioService.obtener(envioId).orElse(null);
    }

    public Pago procesarPago(String envioId, double monto, MetodoPago metodo) {
        var envioOpt = envioService.obtener(envioId);
        if (envioOpt.isEmpty()) return null;
        Pago p = new Pago(UUID.randomUUID().toString(), monto, metodo, envioOpt.get());
        pagoService.registrar(p);
        return p;
    }

    public java.util.List<Usuario> listarUsuarios() { return usuarioService.listarTodos(); }
    public java.util.List<Envio> listarEnvios() { return envioService.listar(); }
    public java.util.List<Repartidor> listarRepartidores() { return repartidorService.listar(); }

}