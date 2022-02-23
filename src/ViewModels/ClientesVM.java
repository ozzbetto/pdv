/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import Conexion.Consult;
import Library.Calendario;
import Library.RenderCheckBox;
import Models.TCliente;
import java.awt.Color;
import java.util.*;
import javax.swing.*;
import java.sql.*;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author frodriguez
 */
public class ClientesVM extends Consult {

    private String _accion = "insert";
    private final ArrayList<JLabel> _label;
    private final ArrayList<JTextField> _textField;
    private final JCheckBox _checkBoxCredito;
    private final JTable _tableCliente;
    private DefaultTableModel modelo1;
    private final int _idCliente = 0;
    private final int _reg_por_pagina = 10;
    private final int _num_paginas = 1;

    public ClientesVM(Object[] objects, ArrayList<JLabel> label, ArrayList<JTextField> textField) {
        _label = label;
        _textField = textField;
        _checkBoxCredito = (JCheckBox) objects[0];
        _tableCliente = (JTable) objects[1];
        restablecer();
    }

    public void registrarCliente() {
        if (_textField.get(0).getText().equals("")) {
            _label.get(0).setText("Falta Número de Cédula");
            _label.get(0).setForeground(Color.RED);
            _textField.get(0).requestFocus();
        } else if (_textField.get(1).getText().equals("")) {
            _label.get(1).setText("Ingrese el nombre");
            _label.get(1).setForeground(Color.RED);
            _textField.get(1).requestFocus();
        } else if (_textField.get(2).getText().equals("")) {
            _label.get(2).setText("Ingrese el apellido");
            _label.get(2).setForeground(Color.RED);
            _textField.get(2).requestFocus();
        } else {
            int count;
            List<TCliente> listEmail = clientes().stream().filter(u -> u.getEmail().equals(_textField.get(4).getText())).collect(Collectors.toList());
            count = listEmail.size();

            List<TCliente> listCedula = clientes().stream().filter(u -> u.getEmail().equals(_textField.get(4).getText())).collect(Collectors.toList());
            count += listCedula.size();
            switch (_accion) {
                case "insert":
                    try {
                    if (count == 0) {
                        Insert();
                    } else {
                        if (!listEmail.isEmpty()) {
                            _label.get(4).setText("Email ya existe.");
                            _label.get(4).setForeground(Color.RED);
                            _textField.get(4).requestFocus();
                        }

                        if (!listCedula.isEmpty()) {
                            _label.get(0).setText("Nro de cédula ya existe.");
                            _label.get(0).setForeground(Color.RED);
                            _textField.get(0).requestFocus();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                break;
                default:
                    throw new AssertionError();
            }

        }
    }

    private void Insert() throws SQLException {
        try {
            final QueryRunner qr = new QueryRunner();
            getConn().setAutoCommit(false);

            String sqlCliente = "INSERT INTO tCliente(NumDoc, nombre, apellido, telefono, email, direccion, fecha, credito) VALUES(?,?,?,?,?,?,?,?)";

            Object[] dataCliente = {
                _textField.get(0).getText(),
                _textField.get(1).getText(),
                _textField.get(2).getText(),
                _textField.get(3).getText(),
                _textField.get(4).getText(),
                _textField.get(5).getText(),
                new Calendario().getFecha(),
                _checkBoxCredito.isSelected(), //                imagen,
            };
            qr.insert(getConn(), sqlCliente, new ColumnListHandler(), dataCliente);

            String sqlReport = "INSERT INTO tReporte_Cliente(deudaActual, fechaDeuda,ultimoPago,fechaPago,ticket,fechaLimite,idCliente) VALUES (?,?,?,?,?,?,?)";
            List<TCliente> cliente = clientes();
            Object[] dataReport = {
                0,
                "--/--/--",
                0,
                "--/--/--",
                "0000000",
                "--/--/--",
                cliente.get(cliente.size() - 1).getId(),};
            qr.insert(getConn(), sqlReport, new ColumnListHandler(), dataReport);
            getConn().commit();
            restablecer();
        } catch (Exception ex) {
            getConn().rollback();
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void SearchClientes(String campo) {
        List<TCliente> clienteFilter;
        String[] titulos = {"ID", "Cédula", "Nombre", "Apellido", "Email", "Dirección", "Teléfono", "Crédito"};
        modelo1 = new DefaultTableModel(null, titulos);
        int inicio = (_num_paginas - 1) * _reg_por_pagina;
        if (campo.equals("")) {
            clienteFilter = clientes().stream().skip(inicio).limit(_reg_por_pagina).collect(Collectors.toList());
        } else {
            clienteFilter = clientes().stream()
                    .filter(C -> C.getNumDoc().startsWith(campo) || C.getNombre().startsWith(campo) || C.getApellido().startsWith(campo))
                    .skip(inicio).limit(_reg_por_pagina).collect(Collectors.toList());
        }
        if (!clienteFilter.isEmpty()) {
            clienteFilter.forEach(item -> {
                Object[] registros = {
                    item.getId(),
                    item.getNumDoc(),
                    item.getNombre(),
                    item.getApellido(),
                    item.getEmail(),
                    item.getDireccion(),
                    item.getTelefono(),
                    item.isCredito()
                };
                modelo1.addRow(registros);
            });
        }
        _tableCliente.setModel(modelo1);
        _tableCliente.setRowHeight(20);
        _tableCliente.getColumnModel().getColumn(0).setMaxWidth(0);
        _tableCliente.getColumnModel().getColumn(0).setMinWidth(0);
        _tableCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tableCliente.getColumnModel().getColumn(7).setCellRenderer(new RenderCheckBox());
    }

    public final void restablecer() {
        _accion = "insert";
        _textField.get(0).setText("");
        _textField.get(1).setText("");
        _textField.get(2).setText("");
        _textField.get(3).setText("");
        _textField.get(4).setText("");
        _textField.get(5).setText("");
        _checkBoxCredito.setSelected(false);

        _label.get(0).setForeground(new Color(0, 0, 0));
        _label.get(1).setForeground(new Color(0, 0, 0));
        _label.get(2).setForeground(new Color(0, 0, 0));
        _label.get(3).setForeground(new Color(0, 0, 0));
        _label.get(4).setForeground(new Color(0, 0, 0));
        _label.get(5).setForeground(new Color(0, 0, 0));

        SearchClientes("");
    }
}
