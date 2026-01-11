package udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago;

import udla.hernandez.velastegui.ruales.poo.finanzas.Cuenta;

public class ServicePago {

    private Cuenta cuenta;

    public ServicePago(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void cobrar(FormaPago formaPago, double montoBaseRenta) {

        double total = formaPago.calcularTotal(montoBaseRenta);

        // 1) Confirmar pago (imprime)
        formaPago.pagar(total);

        // 2) Registrar ingreso en la cuenta
        cuenta.registroIngreso(total);

        // 3) Mostrar cuenta actualizada (si quieres que se vea en cada pago)
        System.out.println("---- Cuenta actualizada ----");
        cuenta.consultaCuenta();
    }
}
