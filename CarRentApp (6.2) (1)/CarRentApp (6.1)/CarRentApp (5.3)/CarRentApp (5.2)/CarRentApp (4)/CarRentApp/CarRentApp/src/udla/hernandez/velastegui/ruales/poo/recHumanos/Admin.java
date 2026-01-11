package udla.hernandez.velastegui.ruales.poo.recHumanos;
import udla.hernandez.velastegui.ruales.poo.productos.Auto;
import udla.hernandez.velastegui.ruales.poo.productos.Bus;

import udla.hernandez.velastegui.ruales.poo.productos.MedioTransporte;

import java.sql.Connection;
import java.util.ArrayList;
import udla.hernandez.velastegui.ruales.poo.finanzas.Cuenta;
import java.util.Scanner;

public class Admin {

    private String usuario;
    private String contrasena;
    private boolean logueado;

    // "Base de datos" simple
    private ArrayList<Cliente> clientes;
    Utilidades util = new Utilidades();

    private Renta[] rentas;
    private int nRentas;

    public Admin(String usuario, String contrasena, int maxClientes, int maxRentas) {

        if (usuario == null) {
            usuario = "admin";
        }

        if (contrasena == null) {
            contrasena = "1234";
        }

        if (maxClientes <= 0) {
            maxClientes = 50;
        }

        if (maxRentas <= 0) {
            maxRentas = 100;
        }

        this.usuario = usuario;
        this.contrasena = contrasena;
        this.logueado = false;
        this.clientes = new ArrayList<>();
        this.rentas = new Renta[maxRentas];
        this.nRentas = 0;
    }

    // ===== LOGIN =====
    public boolean login(String user, String pass) {

        if (user.equals(usuario)) {
            if (pass.equals(contrasena)) {
                logueado = true;
                return true;
            }
        }

        return false;
    }

    public void logout() {
        logueado = false;
    }

    public boolean isLogueado() {
        return logueado;
    }

    // ===== CLIENTES =====
    public boolean registrarCliente(Cliente c) {

        if (!logueado) {
            System.out.println("Acceso denegado. Inicie sesión.");
            return false;
        }

        if (c == null) {
            return false;
        }

        // Validar ID repetido
        if (buscarCliente(c.getId()) != null) {
            System.out.println("Cliente ya registrado.");
            return false;
        }

        clientes.add(c);
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.insetarDatos(c, conn);
        }

        return true;
    }
public void editarCliente(String id){
        util.actualizaDatos(id, util.getConnection());
}

public void editarRenta(String id){
        util.actualizaRenta(id,util.getConnection());
}

    public Cliente buscarCliente(String id) {

        if (!logueado) {
            return null;
        }

        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }



    public void listarClientes() {

        if (!logueado) {
            return;
        }
/***
        if (clientes.isEmpty()) {
            System.out.println("Aún no existen clientes registrados.");
            return;
        }

       for (Cliente c : clientes) {
            System.out.println(c);
        }***/
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.obtenerDatos(conn);
        }


    }


// ========AUTOS====
public boolean registrarAuto(Auto a) {

    if (!logueado) {
        System.out.println("Acceso denegado. Inicie sesión.");
        return false;
    }

    if (a == null) {
        return false;
    }

    Connection conn = null;
    conn = util.getConnection();
    if(conn != null){
        System.out.println("Conectados ..!!");
        util.insetarDatosAuto(a, conn);
    }

    return true;
}

    public boolean registrarBus(Bus b) {

        if (!logueado) {
            System.out.println("Acceso denegado.");
            return false;
        }

        if (b == null) {
            return false;
        }

        Connection conn = util.getConnection();
        if (conn != null) {
            util.   insetarDatosBus(b, conn);
            return true;
        }

        return false;
    }



    // ===== RENTAS =====
    public boolean crearRenta(String codigo, String idCliente, MedioTransporte vehiculo, int dias) {

        if (!logueado) {
            return false;
        }

        if (nRentas >= rentas.length) {
            return false;
        }

        if (vehiculo == null) {
            return false;
        }

        if (dias <= 0) {
            dias = 1;
        }

        Cliente c = buscarCliente(idCliente);

        if (!util.existeClienteBD(idCliente, util.getConnection())) {
            System.out.println("El cliente no existe en la base de datos.");
            return false;
        }


        if (!vehiculo.isDisponible()) {
            return false;
        }

        if (c.isActivo()) {
            return false;
        }

        vehiculo.marcarEnUso();
        c.setActivo(true);

        rentas[nRentas] = new Renta(codigo, c, vehiculo, dias);
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.insetarRenta(rentas[nRentas], conn);
        }
        nRentas++;
        return true;
    }


    public boolean devolverRenta(String codigo, Cuenta cuenta, Scanner sc) {//ELIMINAR PORQUE PUEDO EDITAR

        if (!logueado) {
            return false;
        }

        for (int i = 0; i < nRentas; i++) {

            if (rentas[i] != null && rentas[i].getCodigo().equals(codigo)) { //busquedacódigo

                if (!rentas[i].isActiva()) {
                    System.out.println("La renta ya fue devuelta.");
                    return false;
                }

                System.out.println("¿El vehículo se devuelve en excelentes condiciones?");
                System.out.println("1. Sí");
                System.out.println("0. No");
                System.out.print("Opción: ");
                int opc = sc.nextInt();

                if (opc == 0) {
                    double garantia = rentas[i].getVehiculo().getGarantia();
                    cuenta.registroIngreso(garantia);
                    System.out.println("Daños detectados.");
                    System.out.println("Se ha cobrado la garantía: $" + garantia);
                }
                else if (opc == 1) {
                    System.out.println("Vehículo devuelto en excelentes condiciones.");
                }
                else {
                    System.out.println("Opción inválida. No se devolvió la renta.");
                    return false;
                }

                // Finalizar renta
                rentas[i].getVehiculo().marcarDisponible();
                rentas[i].getCliente().setActivo(false);
                rentas[i].finalizarRenta();

                return true;
            }
        }

        System.out.println("No se encontró una renta con ese código.");
        return false;
    }


    public void listarRentas() {

        if (!logueado) {
            return;
        }
/***
        if (nRentas == 0) {
            System.out.println("Aún no hay rentas registradas.");
            return;
        }

        for (int i = 0; i < nRentas; i++) {
            System.out.println(rentas[i]);
        }***/
        util.obtenerRenta(util.getConnection());
    }

    public boolean eliminarCliente(String id) {

        if (!logueado) {
            System.out.println("Acceso denegado. Inicie sesión.");
            return false;
        }
/***
        if (clientes.isEmpty()) {
            System.out.println("Aún no existen clientes registrados.");
            return false;
        }***/
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.eliminaDatos(id,conn);
        }
/***
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {

                if (c.isActivo()) {
                    System.out.println("No se puede eliminar: cliente con renta activa.");
                    return false;
                }

                clientes.remove(c);
                //eliminación de BDD

                System.out.println("Cliente eliminado correctamente.");
                return true;
            }
        }

        System.out.println("Cliente no existe.");***/
        return false;
    }
    public boolean eliminarRenta(String id) {

        if (!logueado) {
            System.out.println("Acceso denegado. Inicie sesión.");
            return false;
        }
/***
 if (clientes.isEmpty()) {
 System.out.println("Aún no existen clientes registrados.");
 return false;
 }***/
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.eliminarRenta(id,conn);
        }
/***
 for (Cliente c : clientes) {
 if (c.getId().equals(id)) {

 if (c.isActivo()) {
 System.out.println("No se puede eliminar: cliente con renta activa.");
 return false;
 }

 clientes.remove(c);
 //eliminación de BDD

 System.out.println("Cliente eliminado correctamente.");
 return true;
 }
 }

 System.out.println("Cliente no existe.");***/
        return false;
    }
    public void cargarClientesBD(Connection conn) {
        util.cargarClientesDesdeBD(this.clientes, conn);
    }


}

