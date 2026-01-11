package udla.hernandez.velastegui.ruales.poo.recHumanos;

public class Cliente {

    private String id;
    private String nombres;
    private String telefono;

    private boolean activo;

    public Cliente(String nombres, String telefono,String id) {
        if (id == null) id = "SIN-ID";
        if (nombres == null) nombres = "";
        if (telefono == null) telefono = "";

        this.id = id;
        this.nombres = nombres;
        this.telefono = telefono;
        this.activo = false; // inicia sin renta
    }



    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Cliente ID: " + id +
                ", Nombre: " + nombres +
                ", Telefono: " + telefono;
    }
}
