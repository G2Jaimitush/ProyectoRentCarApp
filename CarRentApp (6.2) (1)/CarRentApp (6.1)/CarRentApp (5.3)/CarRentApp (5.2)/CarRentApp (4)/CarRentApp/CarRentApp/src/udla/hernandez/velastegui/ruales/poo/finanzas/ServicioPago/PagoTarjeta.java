package udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago;

public class PagoTarjeta implements FormaPago {

    private static final double RECARGO = 0.10;

    @Override
    public double calcularTotal(double cantidadBase) {
        return cantidadBase + (cantidadBase * RECARGO);
    }

    @Override
    public void pagar(double cantidadFinal) {
        System.out.println("Pago de " + cantidadFinal + " USD por Tarjeta confirmado (+10% recargo).");
        System.out.println("Gracias por usar nuestro servicio, vuelva pronto!");
    }
}
