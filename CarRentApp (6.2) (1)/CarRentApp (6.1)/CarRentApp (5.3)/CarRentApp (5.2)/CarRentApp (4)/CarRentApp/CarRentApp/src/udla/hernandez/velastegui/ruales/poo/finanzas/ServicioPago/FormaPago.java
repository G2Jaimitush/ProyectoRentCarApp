package udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago;

public interface FormaPago {
    double calcularTotal(double cantidadBase);
    void pagar(double cantidadFinal);
}
