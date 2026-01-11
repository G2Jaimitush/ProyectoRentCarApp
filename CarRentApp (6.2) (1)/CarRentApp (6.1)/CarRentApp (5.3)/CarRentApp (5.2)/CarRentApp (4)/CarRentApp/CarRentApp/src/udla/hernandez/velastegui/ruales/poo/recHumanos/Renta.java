package udla.hernandez.velastegui.ruales.poo.recHumanos;

import udla.hernandez.velastegui.ruales.poo.productos.MedioTransporte;

public class Renta {

    private String codigo;
    private Cliente cliente;
    private MedioTransporte vehiculo;

    private int dias;
    private double costoTotal;

    private boolean activa;

    public Renta(String codigo, Cliente cliente, MedioTransporte vehiculo, int dias) {
        if (codigo == null) codigo = "SIN-CODIGO";
        if (dias <= 0) dias = 1;

        this.codigo = codigo;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.activa = true;

        calcularCosto();
    }

    private void calcularCosto() {
        // costo = tarifa diaria * días + garantía
        if (vehiculo != null) {
            costoTotal = (vehiculo.getTarifa_diaria() * dias) + vehiculo.getGarantia();
        } else {
            costoTotal = 0;
        }
    }

    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public MedioTransporte getVehiculo() { return vehiculo; }
    public int getDias() { return dias; }
    public double getCostoTotal() { return costoTotal; }
    public boolean isActiva() { return activa; }

    public void finalizarRenta() {
        this.activa = false;
    }

    @Override
    public String toString() {

        String placa;
        if (vehiculo == null) {
            placa = "SIN-VEHICULO";
        } else {
            placa = vehiculo.getPlaca();
        }

        String cli;
        if (cliente == null) {
            cli = "SIN-CLIENTE";
        } else {
            cli = cliente.getId();
        }

        return "Renta = " +
                "Codigo ='" + codigo + '\'' +
                ", Cliente=" + cli +
                ", Vehiculo=" + placa +
                ", Dias=" + dias +
                ", Costo Total =" + costoTotal +
                ", Activa=" + activa;
    }

}
