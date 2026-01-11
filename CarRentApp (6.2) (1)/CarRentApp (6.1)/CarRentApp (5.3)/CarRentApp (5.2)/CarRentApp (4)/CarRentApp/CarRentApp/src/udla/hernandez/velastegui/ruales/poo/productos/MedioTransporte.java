package udla.hernandez.velastegui.ruales.poo.productos;

/**
 * Clase abstracta que representa un medio de transporte
 */
public abstract class MedioTransporte {

    protected String placa;
    protected String marca;
    protected String modelo;
    protected Combustible combustible;
    protected int max_pasajeros;
    protected double tarifa_diaria;
    protected double garantia;
    protected double tiempo_mant;
    protected boolean estado; // true = disponible, false = en uso

    public MedioTransporte() {
        this.estado = true;
    }

    // Constructor con parámetros (FORMA BÁSICA)
    public MedioTransporte(String placa, String marca, String modelo,
                           Combustible combustible, int max_pasajeros,
                           double tarifa_diaria, double garantia,
                           double tiempo_mant, boolean estado) {

        if (placa == null) {
            this.placa = "SIN PLACA";
        } else {
            this.placa = placa;
        }

        if (marca == null) {
            this.marca = "";
        } else {
            this.marca = marca;
        }

        if (modelo == null) {
            this.modelo = "";
        } else {
            this.modelo = modelo;
        }

        if (combustible == null) {
            this.combustible = Combustible.EXTRA;
        } else {
            this.combustible = combustible;
        }

        if (max_pasajeros <= 0) {
            this.max_pasajeros = 1;
        } else {
            this.max_pasajeros = max_pasajeros;
        }

        if (tarifa_diaria < 0) {
            this.tarifa_diaria = 0;
        } else {
            this.tarifa_diaria = tarifa_diaria;
        }

        if (garantia < 0) {
            this.garantia = 0;
        } else {
            this.garantia = garantia;
        }

        if (tiempo_mant < 0) {
            this.tiempo_mant = 0;
        } else {
            this.tiempo_mant = tiempo_mant;
        }

        this.estado = estado;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }

    public void setMax_pasajeros(int max_pasajeros) {
        this.max_pasajeros = max_pasajeros;
    }

    public void setTarifa_diaria(double tarifa_diaria) {
        this.tarifa_diaria = tarifa_diaria;
    }

    public void setGarantia(double garantia) {
        this.garantia = garantia;
    }

    public void setTiempo_mant(double tiempo_mant) {
        this.tiempo_mant = tiempo_mant;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // Métodos básicos
    public boolean isDisponible() {
        return estado;
    }

    public void marcarDisponible() {
        estado = true;
    }

    public void marcarEnUso() {
        estado = false;
    }

    public abstract String getTipo();

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public Combustible getCombustible() { return combustible; }
    public int getMax_pasajeros() { return max_pasajeros; }
    public double getTarifa_diaria() { return tarifa_diaria; }
    public double getGarantia() { return garantia; }
    public double getTiempo_mant() { return tiempo_mant; }
    public boolean getEstado() { return estado; }

    @Override
    public String toString() {
        String estadoTexto;

        if (estado) {
            estadoTexto = "Disponible";
        } else {
            estadoTexto = "En uso";
        }

        return "Tipo: " + getTipo() +
                ", Placa: " + placa +
                ", Marca: " + marca +
                ", Modelo: " + modelo +
                ", Combustible: " + combustible +
                ", Pasajeros: " + max_pasajeros +
                ", Tarifa: " + tarifa_diaria +
                ", Estado: " + estadoTexto;
    }

}
