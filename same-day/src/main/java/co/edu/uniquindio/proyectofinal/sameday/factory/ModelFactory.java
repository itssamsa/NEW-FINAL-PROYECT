package co.edu.uniquindio.proyectofinal.sameday.factory;

import co.edu.uniquindio.proyectofinal.sameday.model.Direccion;
import co.edu.uniquindio.proyectofinal.sameday.service.*;

import java.util.UUID;

public class ModelFactory {
    private static ModelFactory instance;

    private final UsuarioService usuarioService;
    private final RepartidorService repartidorService;
    private final EnvioService envioService;
    private final TarifaService tarifaService;
    private final PagoService pagoService;

    private ModelFactory() {
        usuarioService = new UsuarioService();
        repartidorService = new RepartidorService();
        envioService = new EnvioService();
        tarifaService = new TarifaService();
        pagoService = new PagoService();
    }

    public static synchronized ModelFactory getInstance() {
        if (instance == null) instance = new ModelFactory();
        return instance;
    }

    public UsuarioService getUsuarioService() { return usuarioService; }
    public RepartidorService getRepartidorService() { return repartidorService; }
    public EnvioService getEnvioService() { return envioService; }
    public TarifaService getTarifaService() { return tarifaService; }
    public PagoService getPagoService() { return pagoService; }


    public Direccion crearDireccionSimple(String alias, String calle, String ciudad) {
        return new Direccion(UUID.randomUUID().toString(), alias, calle, ciudad, "0,0");
    }
}