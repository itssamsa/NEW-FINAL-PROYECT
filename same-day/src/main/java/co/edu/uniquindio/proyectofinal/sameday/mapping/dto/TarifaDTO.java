package co.edu.uniquindio.proyectofinal.sameday.mapping.dto;

public class TarifaDTO {
    private String idTarifa;
    private double costoBase;
    private double costoPorKm;
    private double costoPorKg;
    private double costoPorM3;
    private double recargoPrioridad;
    private double recargoSeguro;

    public TarifaDTO() {}

    public String getIdTarifa() { return idTarifa; }
    public void setIdTarifa(String idTarifa) { this.idTarifa = idTarifa; }
    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }
    public double getCostoPorKm() { return costoPorKm; }
    public void setCostoPorKm(double costoPorKm) { this.costoPorKm = costoPorKm; }
    public double getCostoPorKg() { return costoPorKg; }
    public void setCostoPorKg(double costoPorKg) { this.costoPorKg = costoPorKg; }
    public double getCostoPorM3() { return costoPorM3; }
    public void setCostoPorM3(double costoPorM3) { this.costoPorM3 = costoPorM3; }
    public double getRecargoPrioridad() { return recargoPrioridad; }
    public void setRecargoPrioridad(double recargoPrioridad) { this.recargoPrioridad = recargoPrioridad; }
    public double getRecargoSeguro() { return recargoSeguro; }
    public void setRecargoSeguro(double recargoSeguro) { this.recargoSeguro = recargoSeguro; }
}
