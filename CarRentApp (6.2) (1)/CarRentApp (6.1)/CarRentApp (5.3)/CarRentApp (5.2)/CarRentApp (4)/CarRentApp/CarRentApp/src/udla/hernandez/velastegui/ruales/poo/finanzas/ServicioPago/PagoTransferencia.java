package udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago;

public class PagoTransferencia implements FormaPago {

    private static final double DESCUENTO = 0.15;

    @Override
    public double calcularTotal(double cantidadBase) {
        return cantidadBase - (cantidadBase * DESCUENTO);
    }

    @Override
    public void pagar(double cantidadFinal) {
        System.out.println("Pago de " + cantidadFinal + " USD por Transferencia confirmado (-15% descuento).");
        System.out.println("Gracias por usar nuestro servicio, vuelva pronto!");
    }
}
