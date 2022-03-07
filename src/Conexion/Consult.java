/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Models.TCliente;
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
}
