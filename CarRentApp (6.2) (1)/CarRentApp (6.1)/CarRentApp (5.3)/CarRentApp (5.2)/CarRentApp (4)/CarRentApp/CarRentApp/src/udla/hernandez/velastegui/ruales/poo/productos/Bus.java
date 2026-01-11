package udla.hernandez.velastegui.ruales.poo.productos;

public class Bus extends MedioTransporte {

    public Bus(String placa, String marca, String modelo,
               Combustible combustible, int max_pasajeros,
               double tarifa_diaria, double gerantia,
               double tiempo_mant, boolean estado) {

        super(placa, marca, modelo, combustible, max_pasajeros,
                tarifa_diaria, gerantia, tiempo_mant, estado);
    }

    @Override
    public String getTipo() {
        return "BUS";
    }
}
