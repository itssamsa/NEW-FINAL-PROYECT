package co.edu.uniquindio.proyectofinal.sameday.factory;

import co.edu.uniquindio.proyectofinal.sameday.model.Usuario;
import co.edu.uniquindio.proyectofinal.sameday.service.*;

public class ModelFactory {

    private static final ModelFactory instance = new ModelFactory();

    private final UsuarioService usuarioService;
    private final RepartidorService repartidorService;
    private final EnvioService envioService;
    private final TarifaService tarifaService;
    private final PagoService pagoService;
    private Usuario usuarioActual;

    private ModelFactory() {
        usuarioService = new UsuarioService();
        repartidorService = new RepartidorService();
        envioService = new EnvioService();
        tarifaService = new TarifaService();
        pagoService = new PagoService();
    }

    public static ModelFactory getInstance() {
        return instance;
    }

    public UsuarioService getUsuarioService() { return usuarioService; }
    public RepartidorService getRepartidorService() { return repartidorService; }
    public EnvioService getEnvioService() { return envioService; }
    public TarifaService getTarifaService() { return tarifaService; }
    public PagoService getPagoService() { return pagoService; }


    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Usuario buscarUsuarioPorId(String idUsuario) {
        return getUsuarioService()
                .obtenerPorId(idUsuario)
                .orElse(null);
    }

}
