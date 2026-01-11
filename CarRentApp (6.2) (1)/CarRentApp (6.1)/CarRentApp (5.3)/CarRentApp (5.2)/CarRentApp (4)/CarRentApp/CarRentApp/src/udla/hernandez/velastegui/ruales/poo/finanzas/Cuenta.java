package udla.hernandez.velastegui.ruales.poo.finanzas;

import udla.hernandez.velastegui.ruales.poo.recHumanos.Admin;
import udla.hernandez.velastegui.ruales.poo.recHumanos.Utilidades;

import java.sql.Connection;

/**
 * Llevamos registro del saldo, ingresos, egresos y deuda
 */
public class Cuenta {

    /** Saldo actual de la cuenta */
    private double saldo;

    /** Último ingreso registrado */
    private double ultimoIng;

    /** Último egreso registrado */
    private double ultimoEgr;

    /** Deuda acumulada cuando no hay saldo suficiente */
    private double deuda;

    Utilidades util = new Utilidades();


    /**
     * Constructor de la cuenta
     * @param saldoI saldo inicial
     */
    public Cuenta(int saldoI) {
        this.saldo = (double) saldoI;
        this.deuda = 0;
        this.ultimoIng = 0;
        this.ultimoEgr = 0;
    }

    // ===== GETTERS / SETTERS =====

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getUltimoIng() {
        return this.ultimoIng;
    }

    public void setUltimoIng(double ultimoIng) {
        this.ultimoIng = ultimoIng;
    }

    public double getUltimoEgr() {
        return this.ultimoEgr;
    }

    public void setUltimoEgr(double ultimoEgr) {
        this.ultimoEgr = ultimoEgr;
    }

    public double getDeuda() {
        return this.deuda;
    }

    // ===== MÉTODOS DE CUENTA =====

    /**
     * Registra un ingreso:
     * - si hay deuda, primero la paga
     * - si sobra, aumenta el saldo
     */
    public void registroIngreso(double ingreso) {
        if (ingreso <= 0) {
            return;
        }

        this.ultimoIng = ingreso;

        if (this.deuda > 0) {
            if (ingreso >= this.deuda) {
                ingreso -= this.deuda;
                this.deuda = 0;
                this.saldo += ingreso;
            } else {
                this.deuda -= ingreso;
            }
        } else {
            this.saldo += ingreso;
        }
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.actualizarSaldo(this, conn);
        }

    }

    /**
     * Registra un egreso:
     * - si alcanza el saldo, descuenta normal
     * - si no alcanza, pone saldo 0 y genera deuda por el faltante
     */
    public void registroEgreso(double egreso) {
        if (egreso <= 0) {
            return;
        }

        this.ultimoEgr = egreso;

        if (egreso <= this.saldo) {
            this.saldo -= egreso;
        } else {
            double faltante = egreso - this.saldo;
            this.saldo = 0;
            this.deuda += faltante;
            System.out.println("Saldo insuficiente. Se generó una deuda de $" + faltante);
        }
        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.actualizarSaldo(this, conn);
        }
    }

    /**
     * Muestra la información de la cuenta
     */
    public void consultaCuenta() {

        Connection conn = util.getConnection();
        if (conn != null) {

            double saldoBD = util.obtenerSaldoBD(conn);
            this.saldo = saldoBD; // sincroniza memoria con BD

            System.out.println("========= ESTADO DE CUENTA =========");
            System.out.println("Saldo en Base de Datos : $" + saldoBD);
            System.out.println("-----------------------------------");
            System.out.println("Último ingreso         : $" + this.ultimoIng);
            System.out.println("Último egreso          : $" + this.ultimoEgr);
            System.out.println("Deuda acumulada        : $" + this.deuda);
            System.out.println("-----------------------------------");

            if (this.deuda > 0) {
                System.out.println(" Existe deuda pendiente");
            } else {
                System.out.println("Cuenta al día");
            }

            System.out.println("===================================");
        } else {
            System.out.println("Error: no se pudo conectar a la base de datos.");
        }
    }

}
