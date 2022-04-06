/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Models.TCliente;
import Models.TConfiguracion;
import Models.TReportes_Cliente;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author frodriguez
 */
public class Consult extends Conexion {

    private QueryRunner QR = new QueryRunner();

    public List<TCliente> clientes() {
        List<TCliente> cliente = new ArrayList();
        try {
            cliente = (List<TCliente>) QR.query(getConn(), "SELECT * FROM tCliente", new BeanListHandler(TCliente.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return cliente;
    }

    public List<TReportes_Cliente> reporteCliente() {
        String where = "";
        List<TReportes_Cliente> reportes = new ArrayList();
        String condicion = " tcliente.id = tReporte_Cliente.idCliente";
        String campos = " tcliente.id, tcliente.numDoc,tcliente.nombre,tcliente.apellido,"
                + "tReporte_Cliente.id,tReporte_Cliente.deudaActual,tReporte_Cliente.fechaDeuda,tReporte_Cliente.ultimoPago,"
                + "tReporte_Cliente.fechapago, tReporte_Cliente.ticket,tReporte_Cliente.fechaLimite";
        try {
            reportes = (List<TReportes_Cliente>) QR.query(getConn(), "SELECT" + campos + " FROM tReporte_Cliente INNER JOIN tcliente ON"
                    + condicion + where, new BeanListHandler(TReportes_Cliente.class));
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return reportes;
    }

    public List<TConfiguracion> config() {
        List<TConfiguracion> config = new ArrayList();
        try {
            config = (List<TConfiguracion>) QR.query(getConn(), "SELECT * FROM tconfiguracion", new BeanListHandler(TConfiguracion.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return config;
    }
}
