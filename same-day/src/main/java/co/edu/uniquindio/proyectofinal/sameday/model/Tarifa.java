package co.edu.uniquindio.proyectofinal.sameday.model;

public class Tarifa {
    private String idTarifa;
    private double costoBase;
    private double costoPorKm;
    private double costoPorKg;
    private double costoPorM3;
    private double recargoPrioridad;
    private double recargoSeguro;

    public Tarifa(String idTarifa, double costoBase, double costoPorKm, double costoPorKg,
                  double costoPorM3, double recargoPrioridad, double recargoSeguro) {
        this.idTarifa = idTarifa;
        this.costoBase = costoBase;
        this.costoPorKm = costoPorKm;
        this.costoPorKg = costoPorKg;
        this.costoPorM3 = costoPorM3;
        this.recargoPrioridad = recargoPrioridad;
        this.recargoSeguro = recargoSeguro;
    }

    public String getIdTarifa() { return idTarifa; }
    public double getCostoBase() { return costoBase; }
    public double getCostoPorKm() { return costoPorKm; }
    public double getCostoPorKg() { return costoPorKg; }
    public double getCostoPorM3() { return costoPorM3; }
    public double getRecargoPrioridad() { return recargoPrioridad; }
    public double getRecargoSeguro() { return recargoSeguro; }
}