package udla.hernandez.velastegui.ruales.poo.productos;

public class Auto extends MedioTransporte {

    public Auto(String placa, String marca, String modelo,
                Combustible combustible, int max_pasajeros,
                double tarifa_diaria, double garantia,
                double tiempo_mant, boolean estado) {

        super(placa, marca, modelo, combustible, max_pasajeros,
                tarifa_diaria, garantia, tiempo_mant, estado);
    }

    @Override
    public String getTipo() {
        return "AUTO";
    }
}
