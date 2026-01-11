package udla.hernandez.velastegui.ruales.poo.recHumanos;
import udla.hernandez.velastegui.ruales.poo.finanzas.Cuenta;
import udla.hernandez.velastegui.ruales.poo.productos.Auto;
import udla.hernandez.velastegui.ruales.poo.productos.Bus;
import udla.hernandez.velastegui.ruales.poo.productos.Combustible;
import udla.hernandez.velastegui.ruales.poo.productos.MedioTransporte;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.ArrayList;




public class Utilidades {
    Scanner sc = new Scanner(System.in);

    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/poojdbc";
        String user = "root";
        String passwd = "sasa";

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(url, user, passwd);
            return conn;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void insetarDatos(Cliente cliente, Connection conn){
        String sql = "INSERT INTO cliente (nombres, telefono, id) VALUES (?,?,?)";
        try{

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1,cliente.getNombres());
            ps.setString(2,cliente.getTelefono());
            ps.setString(3,cliente.getId());

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha insertado correctamente..");
            }else {
                System.out.println("El Cliente no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void insetarRenta(Renta renta, Connection conn){
        String sqlInsert = "INSERT INTO renta (codigo, idCli, placa, dias, estado) VALUES (?,?,?,?,?)";
        String sqlUpdate = "UPDATE vehiculos SET estado=? WHERE placa=?";

        try {
            // 1. Insertar la renta
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, renta.getCodigo());
            ps.setString(2, renta.getCliente().getId());
            ps.setString(3, renta.getVehiculo().getPlaca());
            ps.setInt(4, renta.getDias());
            ps.setBoolean(5, false); // estado de la renta

            int resultado = ps.executeUpdate();

            // 2. Actualizar el estado del vehículo
            PreparedStatement psUpd = conn.prepareStatement(sqlUpdate);
            psUpd.setBoolean(1, false); // vehículo pasa a "no disponible"
            psUpd.setString(2, renta.getVehiculo().getPlaca());
            int resultadoUpd = psUpd.executeUpdate();

            // 3. Mensajes de confirmación
            if (resultado > 0) {
                System.out.println("La renta se ha insertado correctamente.");
            } else {
                System.out.println("La renta no se insertó.");
            }

            if (resultadoUpd > 0) {
                System.out.println("El vehículo se actualizó correctamente.");
            } else {
                System.out.println("El vehículo no se actualizó.");
            }

            // 4. Cerrar recursos
            ps.close();
            psUpd.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void actualizarSaldo(Cuenta cuenta, Connection conn) {
        String sql = "UPDATE cuenta SET saldo = ? WHERE idregistro = 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, cuenta.getSaldo());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Saldo actualizado correctamente en BD.");
            } else {
                System.out.println("No existe la cuenta con idregistro = 1.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void insetarDatosAuto(Auto auto, Connection conn){
        String sql = "INSERT INTO vehiculos (placa, marca, modelo,combustible,max_pasajeros,tarifa_diaria,garantia,tiempo_mant,estado) VALUES (?,?,?,?,?,?,?,?,?)";
        try{

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1,auto.getPlaca());
            ps.setString(2,auto.getMarca());
            ps.setString(3,auto.getModelo());
            ps.setString(4,auto.getCombustible().toString());
            ps.setInt(5,auto.getMax_pasajeros());
            ps.setDouble(6,auto.getTarifa_diaria());
            ps.setDouble(7,auto.getGarantia());
            ps.setDouble(8,auto.getTiempo_mant());
            ps.setBoolean(9,auto.getEstado());

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El auto se ha insertado correctamente..");
            }else {
                System.out.println("El auto no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void insetarDatosBus(Bus bus, Connection conn) {
        String sql = "INSERT INTO vehiculos (placa, marca, modelo, combustible, max_pasajeros, tarifa_diaria, garantia, tiempo_mant, estado) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bus.getPlaca());
            ps.setString(2, bus.getMarca());
            ps.setString(3, bus.getModelo());
            ps.setString(4, bus.getCombustible().toString());
            ps.setInt(5, bus.getMax_pasajeros());
            ps.setDouble(6, bus.getTarifa_diaria());
            ps.setDouble(7, bus.getGarantia());
            ps.setDouble(8, bus.getTiempo_mant());
            ps.setBoolean(9, bus.getEstado());

            int resultado = ps.executeUpdate();

            System.out.println("Filas insertadas: " + resultado);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public boolean eliminarVehiculo (String placa, Connection conn) {
        String sql = "DELETE FROM vehiculos WHERE placa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, placa);
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean actualizarVehiculo(String placa, Connection conn) {

        System.out.println("¿Qué desea actualizar?");
        System.out.println("1. Marca");
        System.out.println("2. Modelo");
        System.out.println("3. Combustible");
        System.out.println("4. Max pasajeros");
        System.out.println("5. Tarifa diaria");
        System.out.println("6. Garantía");
        System.out.println("7. Tiempo mantenimiento");
        System.out.println("8. Estado (true/false)");
        System.out.print("Opción: ");
        int opc = sc.nextInt();
        sc.nextLine();

        String sql;
        try (PreparedStatement ps = conn.prepareStatement("SELECT placa FROM vehiculos WHERE placa=?")) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Placa no encontrada en BD.");
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            PreparedStatement psUpd = null;

            switch (opc) {
                case 1:
                    sql = "UPDATE vehiculos SET marca=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nueva marca: ");
                    psUpd.setString(1, sc.nextLine());
                    psUpd.setString(2, placa);
                    break;

                case 2:
                    sql = "UPDATE vehiculos SET modelo=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nuevo modelo: ");
                    psUpd.setString(1, sc.nextLine());
                    psUpd.setString(2, placa);
                    break;

                case 3:
                    sql = "UPDATE vehiculos SET combustible=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nuevo combustible (EXTRA/SUPER/DIESEL): ");
                    psUpd.setString(1, sc.nextLine().toUpperCase());
                    psUpd.setString(2, placa);
                    break;

                case 4:
                    sql = "UPDATE vehiculos SET max_pasajeros=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nuevo max pasajeros: ");
                    psUpd.setInt(1, sc.nextInt());
                    psUpd.setString(2, placa);
                    break;

                case 5:
                    sql = "UPDATE vehiculos SET tarifa_diaria=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nueva tarifa diaria: ");
                    psUpd.setDouble(1, sc.nextDouble());
                    psUpd.setString(2, placa);
                    break;

                case 6:
                    sql = "UPDATE vehiculos SET garantia=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nueva garantía: ");
                    psUpd.setDouble(1, sc.nextDouble());
                    psUpd.setString(2, placa);
                    break;

                case 7:
                    sql = "UPDATE vehiculos SET tiempo_mant=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nuevo tiempo mantenimiento: ");
                    psUpd.setDouble(1, sc.nextDouble());
                    psUpd.setString(2, placa);
                    break;

                case 8:
                    sql = "UPDATE vehiculos SET estado=? WHERE placa=?";
                    psUpd = conn.prepareStatement(sql);
                    System.out.print("Nuevo estado (true/false): ");
                    psUpd.setBoolean(1, sc.nextBoolean());
                    psUpd.setString(2, placa);
                    break;

                default:
                    System.out.println("Opción inválida.");
                    return false;
            }

            int r = psUpd.executeUpdate();
            psUpd.close();

            return r > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public void obtenerVehiculo(Connection conn) {
        String sql = "SELECT * FROM vehiculos";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("=== VEHICULOS REGISTRADOS ===");

            boolean hay = false;
            while (rs.next()) {
                hay = true;
                System.out.println(
                        "Placa: " + rs.getString("placa") +
                                ", Marca: " + rs.getString("marca") +
                                ", Modelo: " + rs.getString("modelo") +
                                ", Combustible: " + rs.getString("combustible") +
                                ", Pasajeros: " + rs.getInt("max_pasajeros") +
                                ", Tarifa: " + rs.getDouble("tarifa_diaria") +
                                ", Garantía: " + rs.getDouble("garantia") +
                                ", Mant: " + rs.getDouble("tiempo_mant") +
                                ", Estado: " + rs.getBoolean("estado")
                );
            }

            if (!hay) System.out.println("No hay vehículos registrados en la base.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void obtenerRenta(Connection conn) {
        String sql = "SELECT r.codigo, r.dias, " +
                "c.id, c.nombres, c.telefono, " +
                "v.placa, v.marca, v.modelo, v.combustible, v.max_pasajeros, " +
                "v.tarifa_diaria, v.garantia, v.tiempo_mant, v.estado " +
                "FROM renta r " +
                "JOIN cliente c ON r.idCli = c.id " +
                "JOIN vehiculos v ON r.placa = v.placa";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Crear Cliente con el constructor correcto
                Cliente cli = new Cliente(
                        rs.getString("nombres"),
                        rs.getString("telefono"),
                        rs.getString("id")
                );

                // Convertir combustible (si es enum)
                Combustible comb = Combustible.valueOf(rs.getString("combustible").toUpperCase());

                // Crear Auto con el constructor correcto
                Auto auto = new Auto(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        comb,
                        rs.getInt("max_pasajeros"),
                        rs.getDouble("tarifa_diaria"),
                        rs.getDouble("garantia"),
                        rs.getDouble("tiempo_mant"),
                        rs.getBoolean("estado")
                );

                // Crear Renta con Cliente y Auto
                Renta rent = new Renta(
                        rs.getString("codigo"),
                        cli,
                        auto,
                        rs.getInt("dias")
                );

                System.out.println(rent);
            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void actualizaRenta (String id,Connection conn){
        System.out.println("Seleccione el dato que se desea cambiar");
        System.out.println("1.código\n2.dias\n3.estado");
        Integer opc= sc.nextInt();

        String sql = "UPDATE renta SET id= WHERE id=0";
        switch (opc){
            case 1:
                System.out.println("Nuevo codigo:");
                String nuevoid = sc.next();
                sql = "UPDATE renta SET codigo=\""+nuevoid+"\" WHERE codigo=\""+id+"\"";
                break;
            case 2:
                System.out.println("Nuevo tiempo(dias):");
                String nombre = sc.next();
                sql = "UPDATE renta SET dias=\""+nombre+"\" WHERE codigo=\""+id+"\"";
                break;
            case 3:
                System.out.println("Nuevo estado:");
                String telefono = sc.next();
                sql = "UPDATE renta SET estado=\""+telefono+"\" WHERE codigo=\""+id+"\"";
                break;
            default:
                System.out.println("No se cambió nada");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha actualizado correctamente..");
            }else {
                System.out.println("El Cliente no se ha actualizado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarRenta (String id,Connection conn){
        String sql = "DELETE  FROM renta WHERE codigo=\""+id+"\"";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("La renta se ha eliminado correctamente..");
            }else {
                System.out.println("La renta no se ha eliminado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void obtenerDatos(Connection conn) {
        String sql = "SELECT * FROM poojdbc.cliente";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                System.out.println(cli.toString());
            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void actualizaDatos(String id,Connection conn) {
        System.out.println("Seleccione el dato que se desea cambiar");
        System.out.println("1.id\n2.Nombre\n3.Telefono");
        Integer opc= sc.nextInt();

        String sql = "UPDATE cliente SET id= WHERE id=0";
        switch (opc){
            case 1:
                System.out.println("Nuevo id:");
               String nuevoid = sc.next();
                 sql = "UPDATE cliente SET id=\""+nuevoid+"\" WHERE id=\""+id+"\"";
                 break;
            case 2:
                System.out.println("Nuevo nombre:");
                String nombre = sc.next();
                sql = "UPDATE cliente SET nombres=\""+nombre+"\" WHERE id=\""+id+"\"";
                break;
            case 3:
                System.out.println("Nuevo telefono:");
                String telefono = sc.next();
                sql = "UPDATE cliente SET telefono=\""+telefono+"\" WHERE id=\""+id+"\"";
                break;
            default:
                System.out.println("No se cambió nada");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha actualizado correctamente..");
            }else {
                System.out.println("El Cliente no se ha actualizado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void eliminaDatos(String id, Connection conn) {
        String sql = "DELETE  FROM cliente WHERE id=\""+id+"\"";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha eliminado correctamente..");
            }else {
                System.out.println("El Cliente no se ha eliminado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int cargarVehiculosDesdeBD(MedioTransporte[] vehiculos, Connection conn) {
        String sql = "SELECT * FROM vehiculos";
        int n = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next() && n < vehiculos.length) {

                Combustible comb = Combustible.valueOf(
                        rs.getString("combustible").toUpperCase()
                );

                vehiculos[n] = new Auto(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        comb,
                        rs.getInt("max_pasajeros"),
                        rs.getDouble("tarifa_diaria"),
                        rs.getDouble("garantia"),
                        rs.getDouble("tiempo_mant"),
                        rs.getBoolean("estado")
                );
                n++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public boolean existeClienteBD(String id, Connection conn) {
        String sql = "SELECT 1 FROM cliente WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si existe
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int cargarClientesDesdeBD(ArrayList<Cliente> clientes, Connection conn) {
        String sql = "SELECT id, nombres, telefono FROM cliente";
        int n = 0;
        clientes.clear();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(
                        new Cliente(
                                rs.getString("nombres"),
                                rs.getString("telefono"),
                                rs.getString("id")
                        )
                );
                n++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return n;
    }


    public double obtenerSaldoBD(Connection conn) {
        String sql = "SELECT saldo FROM cuenta WHERE idregistro = 1";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("saldo");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }



}







