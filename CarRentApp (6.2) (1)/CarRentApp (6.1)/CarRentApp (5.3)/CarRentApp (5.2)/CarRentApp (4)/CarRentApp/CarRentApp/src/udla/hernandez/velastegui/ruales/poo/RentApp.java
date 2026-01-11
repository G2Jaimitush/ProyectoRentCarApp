package udla.hernandez.velastegui.ruales.poo;

import java.util.Scanner;

import udla.hernandez.velastegui.ruales.poo.finanzas.Cuenta;
import udla.hernandez.velastegui.ruales.poo.recHumanos.Admin;
import udla.hernandez.velastegui.ruales.poo.recHumanos.Cliente;
import udla.hernandez.velastegui.ruales.poo.productos.*;
import udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago.FormaPago;
import udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago.PagoTarjeta;
import udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago.PagoTransferencia;
import udla.hernandez.velastegui.ruales.poo.finanzas.ServicioPago.ServicePago;
import udla.hernandez.velastegui.ruales.poo.recHumanos.Renta;

import udla.hernandez.velastegui.ruales.poo.recHumanos.Utilidades;
import java.sql.Connection;


public class RentApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        Utilidades util = new Utilidades();
        Connection conn = util.getConnection();

        if (conn == null) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            return;
        }


        // Crear ADMIN
        Admin admin = new Admin("admin", "1234", 50, 100);

        //Cuenta Inicializada en 0
        Cuenta cuenta = new Cuenta(0);


        ServicePago servicePago = new ServicePago(cuenta);

        // Arreglo de vehículos
        MedioTransporte[] vehiculos = new MedioTransporte[50];
        int nVehiculos;
        nVehiculos = util.cargarVehiculosDesdeBD(vehiculos, conn);

        int opcionPrincipal;

        do {
            System.out.println("\n=== SISTEMA DE RENTA DE VEHÍCULOS ===");
            System.out.println("1. Administrador");
            System.out.println("2. Empleado");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcionPrincipal = sc.nextInt();

            // ===== ADMIN =====
            if (opcionPrincipal == 1) {

                System.out.print("Usuario: ");
                String user = sc.next();
                System.out.print("Contraseña: ");
                String pass = sc.next();


                if (!admin.login(user, pass)) {
                    System.out.println("Credenciales incorrectas.");
                    continue;
                }

                if (admin.login(user, pass)) {
                    admin.cargarClientesBD(conn);
                }

                int opAdmin;

                do {
                    System.out.println("\n--- MENÚ ADMIN ---");
                    System.out.println("1. Registrar vehículo");
                    System.out.println("2. Listar vehículos registrados");
                    System.out.println("3. Registrar cliente");
                    System.out.println("4. Listar clientes");
                    System.out.println("5. Crear renta");
                    System.out.println("6. Devolver renta");
                    System.out.println("7. Listar rentas");
                    System.out.println("8. Cuenta");
                    System.out.println("9. Elimar vehiculo");
                    System.out.println("10. Eliminar cliente");
                    System.out.println("11. Editar clientes ");
                    System.out.println("12. Editar Vehiculo");
                    System.out.println("13. Editar renta");
                    System.out.println("14. Eliminar Renta");
                    System.out.println("0. Cerrar sesión");
                    System.out.print("Opción: ");
                    opAdmin = sc.nextInt();

                    switch (opAdmin) {

                        //REGISTRAR VEHÍCULO
                        case 1:
                            if (nVehiculos >= vehiculos.length) {
                                System.out.println("No hay espacio para más vehículos.");
                                break;
                            }

                            System.out.println("Tipo de vehículo:");
                            System.out.println("1. Auto");
                            System.out.println("2. Bus");
                            System.out.print("Opción: ");
                            int tipo = sc.nextInt();

                            System.out.print("Placa: ");
                            String placa = sc.next();
                            sc.nextLine();

                            // VALIDAR PLACA REPETIDA
                            boolean placaExiste = false;
                            for (int i = 0; i < nVehiculos; i++) {
                                if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placa)) {
                                    placaExiste = true;
                                    break;
                                }
                            }

                            if (placaExiste) {
                                System.out.println("Error: ya existe un vehículo con esa placa.");
                                break;
                            }

                            System.out.print("Marca: ");
                            String marca = sc.nextLine();

                            System.out.print("Modelo: ");
                            String modelo = sc.nextLine();

                            System.out.println("Combustible:");
                            System.out.println("1. EXTRA");
                            System.out.println("2. SUPER");
                            System.out.println("3. DIESEL");
                            System.out.print("Tipo de Combustible: ");
                            int c = sc.nextInt();

                            do {
                                if (c > 3 || c < 1) {
                                    System.out.println("Solo se puede ingresar (1. EXTRA - 2.SUPER - 3.DIESEL)");
                                    System.out.print("Tipo de Combustible: ");
                                    c = sc.nextInt();
                                }
                            } while (c > 3 || c < 1);

                            Combustible comb = Combustible.EXTRA;
                            if (c == 2) {
                                comb = Combustible.SUPER;
                            } else if (c == 3) {
                                comb = Combustible.DIESEL;
                            }

                            System.out.print("Máx pasajeros: ");
                            int maxPas = sc.nextInt();

                            System.out.print("Tarifa diaria: ");
                            double tarifa = sc.nextDouble();

                            System.out.print("Garantía: ");
                            double garantia = sc.nextDouble();

                            System.out.print("Tiempo mantenimiento: ");
                            double mant = sc.nextDouble();


                            //REGISTRO + EGRESO
                            if (tipo == 1) {
                                vehiculos[nVehiculos] = new Auto(placa, marca, modelo, comb, maxPas, tarifa, garantia, mant, true);
                                if (admin.registrarAuto(new Auto(placa,marca,modelo,comb,maxPas,tarifa,garantia,mant,true))) {
                                    System.out.println("Auto registrado correctamente.");
                                } else {
                                    System.out.println("No se pudo registrar el auto.");
                                }
                                cuenta.registroEgreso(20);
                                System.out.println("Egreso registrado: $20 (Auto tanqueado)");

                                } else if (tipo == 2) { // Bus
                                Bus nuevoBus = new Bus(placa, marca, modelo, comb, maxPas, tarifa, garantia, mant, true);
                                vehiculos[nVehiculos] = nuevoBus;

                                if (admin.registrarBus(nuevoBus)) {
                                    System.out.println("Bus registrado correctamente.");
                                } else {
                                    System.out.println("No se pudo registrar el bus.");
                                }

                                cuenta.registroEgreso(40);
                                System.out.println("Egreso registrado: $40 (Bus tanqueado)");
                            } else {
                                System.out.println("Tipo inválido.");
                                break;
                            }

                            nVehiculos++;


                            System.out.println("Vehículo registrado correctamente.");
                            break;



                        // 2 LISTAR VEHÍCULOS
                        case 2:
                            if (nVehiculos == 0) {
                                System.out.println("No hay vehículos registrados.");
                                break;
                            }
                            System.out.println("Vehículos registrados:");

                            util.obtenerVehiculo(conn);
                            break;

                        // 3 REGISTRAR CLIENTE
                        case 3:
                            System.out.print("ID cliente: ");
                            String id = sc.next();
                            sc.nextLine(); // limpiar salto

                            // Verificar si el cliente ya existe
                            if (admin.buscarCliente(id) != null) {
                                System.out.println("Cliente ya registrado.");
                                break;
                            }

                            System.out.print("Nombres: ");
                            String nombres = sc.nextLine();

                            System.out.print("Teléfono: ");
                            String telefono = sc.next();

                            if (admin.registrarCliente(new Cliente(nombres, telefono,id))) {
                                System.out.println("Cliente registrado correctamente.");
                            } else {
                                System.out.println("No se pudo registrar el cliente.");
                            }
                            break;

                        // 4 LISTAR CLIENTES
                        case 4:
                            admin.listarClientes();

                            break;

                        // 5 CREAR RENTA
                        case 5:
                            System.out.print("Código renta: ");
                            String cod = sc.next();

                            System.out.print("ID cliente: ");
                            String idCli = sc.next();

                            System.out.print("Placa vehículo: ");
                            String placaBuscada = sc.next();

                            System.out.print("Días: ");
                            int dias = sc.nextInt();

                            boolean encontrado = false;

                            for (int i = 0; i < nVehiculos; i++) {

                                if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placaBuscada)) {

                                    //Intentar crear la renta
                                    boolean rentaCreada = admin.crearRenta(cod, idCli, vehiculos[i], dias);

                                    if (!rentaCreada) {
                                        System.out.println("No se pudo crear la renta.");
                                        System.out.println("Verifique:");
                                        System.out.println("- Que el cliente exista");
                                        System.out.println("- Que el vehículo esté disponible");
                                        System.out.println("- Que el cliente no tenga una renta activa");
                                        encontrado = true;
                                        break;
                                    }

                                    //Calcular monto base
                                    double montoBase = vehiculos[i].getTarifa_diaria() * dias;
                                    System.out.println("Monto base: " + montoBase + " USD");

                                    //Elegir forma de pago
                                    System.out.println("Forma de pago:");
                                    System.out.println("1. Transferencia (15% descuento)");
                                    System.out.println("2. Tarjeta (10% recargo)");
                                    int opPago = sc.nextInt();

                                    FormaPago formaPago;
                                    if (opPago == 1) {
                                        formaPago = new PagoTransferencia();
                                    } else {
                                        formaPago = new PagoTarjeta();
                                    }

                                    //Cobrar (REGISTRA INGRESO EN CUENTA)
                                    servicePago.cobrar(formaPago, montoBase);

                                    //Mostrar total final
                                    double totalFinal = formaPago.calcularTotal(montoBase);
                                    System.out.println("Renta creada correctamente.");
                                    System.out.println("Total final a pagar: " + totalFinal + " USD");

                                    encontrado = true;
                                    break;
                                }
                            }

                            if (!encontrado) {
                                System.out.println("Vehículo no encontrado.");
                            }
                            break;



                        // 6 DEVOLVER RENTA
                        case 6:
                            /*** System.out.print("Código renta a devolver: ");
                            String codDev = sc.next();
                            admin.devolverRenta(codDev, cuenta, sc);
                            ***/
                            break;

                        // 7 LISTAR RENTAS
                        case 7:
                            admin.listarRentas();
                            break;

                        // 8 CUENTA
                        case 8:
                            cuenta.consultaCuenta();
                            break;



                        // 9 ELIMINAR VEHICULO
                        case 9:
                            if (nVehiculos == 0) {
                                System.out.println("Aún no existen vehículos registrados.");
                                break;
                            }

                            System.out.print("Placa del vehículo a eliminar: ");
                            String placaEliminar = sc.next();

                            int posVeh = -1;

                            // buscar vehículo en el arreglo
                            for (int i = 0; i < nVehiculos; i++) {
                                if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placaEliminar)) {
                                    posVeh = i;
                                    break;
                                }
                            }

                            if (posVeh == -1) {
                                System.out.println("Vehículo no existe.");
                                break;
                            }

                            // validar disponibilidad
                            if (!vehiculos[posVeh].isDisponible()) {
                                System.out.println("No se puede eliminar: vehículo con renta activa.");
                                break;
                            }

                            //ELIMINAR EN BD
                            boolean eliminadoBD = util.eliminarVehiculo(placaEliminar, conn);

                            if (eliminadoBD) {
                                System.out.println("Vehículo eliminado en la Base de Datos.");
                            } else {
                                System.out.println("Aviso: no se eliminó en la BD.");
                            }

                            //ELIMINAR EN MEMORIA (tu lógica)
                            for (int j = posVeh; j < nVehiculos - 1; j++) {
                                vehiculos[j] = vehiculos[j + 1];
                            }

                            vehiculos[nVehiculos - 1] = null;
                            nVehiculos--;

                            System.out.println("Vehículo eliminado correctamente.");
                            break;



                        // 10 ELIMINAR CLIENTE
                        case 10:
                            admin.listarClientes();
                            System.out.print("ID del cliente a eliminar: ");
                            String idEliminar = sc.next();

                            admin.eliminarCliente(idEliminar);
                            break;

                            // 11 EDITAR CLIENTE
                        case 11:
                            admin.listarClientes();
                            System.out.print("ID del cliente a editar: ");
                            String idEditar = sc.next();

                            admin.editarCliente(idEditar);
                            break;

                            // 12 EDITAR VEHICULO
                        case 12:
                            if (nVehiculos == 0) {
                                System.out.println("No hay vehículos cargados en memoria.");
                                break;
                            }

                            System.out.print("Placa del vehículo a editar: ");
                            String placaEdit = sc.next();

                            int pos = -1;
                            for (int i = 0; i < nVehiculos; i++) {
                                if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placaEdit)) {
                                    pos = i;
                                    break;
                                }
                            }

                            if (pos == -1) {
                                System.out.println("Vehículo no encontrado en memoria.");
                                break;
                            }

                            boolean ok = util.actualizarVehiculo(placaEdit, conn);

                            if (!ok) {
                                System.out.println("No se pudo actualizar en la base de datos.");
                                break;
                            }

                            nVehiculos = util.cargarVehiculosDesdeBD(vehiculos, conn);

                            System.out.println("Vehículo actualizado correctamente.");
                            break;
                        case 13:
                            admin.listarRentas();
                            System.out.print("codigo de renta a editar: ");
                             idEditar = sc.next();

                            admin.editarRenta(idEditar);
                            break;
                        case 14:
                            admin.listarRentas();
                            System.out.print("codigo de renta a eliminar: ");
                            idEliminar = sc.next();

                            admin.eliminarRenta(idEliminar);
                            break;
                        // 0) CERRAR SESIÓN
                        case 0:
                            admin.logout();
                            System.out.println("Sesion cerrada.");
                            break;

                        default:
                            System.out.println("Opcion invalida.");
                    }

                } while (opAdmin != 0);

                // ===== EMPLEADO =====
            } else if (opcionPrincipal == 2) {

                int opEmp;
                do {
                    System.out.println("\n--- MENÚ EMPLEADO ---");
                    System.out.println("1. Ver vehículos disponibles");
                    System.out.println("2. Ver estado de vehículos");
                    System.out.println("0. Volver");
                    System.out.print("Opción: ");
                    opEmp = sc.nextInt();

                    switch (opEmp) {

                        case 1:
                            System.out.println("Vehículos disponibles:");
                            boolean hay = false;

                            for (int i = 0; i < nVehiculos; i++) {
                                if (vehiculos[i].isDisponible()) {
                                    System.out.println(vehiculos[i]);
                                    hay = true;
                                }
                            }

                            if (!hay) {
                                System.out.println("No hay vehículos disponibles.");
                            }
                            break;

                        case 2:
                            System.out.println("Estado de vehículos:");

                            if (nVehiculos == 0) {
                                System.out.println("No hay vehículos registrados.");
                            } else {
                                for (int i = 0; i < nVehiculos; i++) {
                                    System.out.println(vehiculos[i]);
                                }
                            }
                            break;

                        case 0:
                            System.out.println("Volviendo al menú principal...");
                            break;

                        default:
                            System.out.println("Opción inválida.");

                    }
                }while (opEmp != 0);

            }else if (opcionPrincipal != 0) {
                System.out.println("Opcion invalida.");
            }

        } while (opcionPrincipal != 0);

        System.out.println("Programa finalizado.");
    }
}
