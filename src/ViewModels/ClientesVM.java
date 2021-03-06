/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import Conexion.Consult;
import Library.Calendario;
import Library.FormatDecimal;
import Library.Paginador;
import Library.RenderCheckBox;
import Models.TCliente;
import Models.TReportes_Cliente;
import java.awt.Color;
import static java.lang.String.format;
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
    
    private String _accion = "insert", _mony;
    private final ArrayList<JLabel> _label;
    private final ArrayList<JTextField> _textField;
    private final JCheckBox _checkBoxCredito;
    private final JTable _tableCliente, _tableReporte;
    private DefaultTableModel modelo1, modelo2;
    private final JSpinner _spinnerPaginas;
    private int _idCliente = 0;
    private int _reg_por_pagina = 10;
    private int _num_paginas = 1;
    public int seccion;
    private final FormatDecimal _format;
    private Paginador<TCliente> _paginadorCliente;
    private Paginador<TReportes_Cliente> _paginadorReportes;

    // <editor-fold defaultstate="collapsed" desc="Registro de Clientes">
    public ClientesVM(Object[] objects, ArrayList<JLabel> label, ArrayList<JTextField> textField) {
        _label = label;
        _textField = textField;
        _checkBoxCredito = (JCheckBox) objects[0];
        _tableCliente = (JTable) objects[1];
        _spinnerPaginas = (JSpinner) objects[2];
        _tableReporte = (JTable) objects[3];
        _format = new FormatDecimal();
        restablecer();
        restablecerReport();
    }
    
    public void registrarCliente() {
        if (_textField.get(0).getText().equals("")) {
            _label.get(0).setText("Falta N??mero de C??dula");
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
            try {
                switch (_accion) {
                    case "insert":
                        
                        if (count == 0) {
                            Insert();
                        } else {
                            if (!listEmail.isEmpty()) {
                                _label.get(5).setText("Email ya existe.");
                                _label.get(5).setForeground(Color.RED);
                                _textField.get(5).requestFocus();
                            }
                            
                            if (!listCedula.isEmpty()) {
                                _label.get(0).setText("Nro de c??dula ya existe.");
                                _label.get(0).setForeground(Color.RED);
                                _textField.get(0).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (count == 2) {
                            if (listEmail.get(0).getId() == _idCliente && listCedula.get(0).getId() == _idCliente) {
                                Insert();
                            } else {
                                if (listCedula.get(0).getId() != _idCliente) {
                                    _label.get(0).setText("El dato ya existe.");
                                    _label.get(0).setForeground(Color.red);
                                    _textField.get(0).requestFocus();
                                }
                                
                                if (listEmail.get(0).getId() != _idCliente) {
                                    _label.get(5).setText("Email ya existe.");
                                    _label.get(5).setForeground(Color.red);
                                    _textField.get(5).requestFocus();
                                }
                            }
                        } else {
                            if (count == 0) {
                                Insert();
                            } else {
                                if (!listCedula.isEmpty()) {
                                    if (listCedula.get(0).getId() == _idCliente) {
                                        Insert();
                                    } else {
                                        _label.get(0).setText("El dato ya existe.");
                                        _label.get(0).setForeground(Color.red);
                                        _textField.get(0).requestFocus();
                                    }
                                }
                                if (!listEmail.isEmpty()) {
                                    if (listEmail.get(0).getId() == _idCliente) {
                                        Insert();
                                    } else {
                                        _label.get(4).setText("Email ya existe.");
                                        _label.get(4).setForeground(Color.red);
                                        _textField.get(4).requestFocus();
                                    }
                                }
                            }
                        }
                        break;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    private void Insert() throws SQLException {
        try {
            final QueryRunner qr = new QueryRunner();
            getConn().setAutoCommit(false);
            
            switch (_accion) {
                case "insert":
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
                    break;
                
                case "update":
                    Object[] dataCliente2 = {
                        _textField.get(0).getText(),
                        _textField.get(1).getText(),
                        _textField.get(2).getText(),
                        _textField.get(3).getText(),
                        _textField.get(4).getText(),
                        _textField.get(5).getText(),
                        _checkBoxCredito.isSelected(), //                imagen,
                    };
                    String sqlCliente2 = "UPDATE tCliente SET numDoc = ?, nombre = ?, apellido = ?, telefono = ?, email = ?, direccion = ?, credito = ? WHERE id = " + _idCliente;
                    qr.update(getConn(), sqlCliente2, dataCliente2);
                    break;
            }
            
            getConn().commit();
            restablecer();
        } catch (Exception ex) {
            getConn().rollback();
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void SearchClientes(String campo) {
        List<TCliente> clienteFilter;
        String[] titulos = {"ID", "C??dula", "Nombre", "Apellido", "Email", "Direcci??n", "Tel??fono", "Cr??dito"};
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
    
    public void getCliente() {
        _accion = "update";
        int filas = _tableCliente.getSelectedRow();
        _idCliente = (Integer) modelo1.getValueAt(filas, 0);
        _textField.get(0).setText((String) modelo1.getValueAt(filas, 1));
        _textField.get(1).setText((String) modelo1.getValueAt(filas, 2));
        _textField.get(2).setText((String) modelo1.getValueAt(filas, 3));
        _textField.get(3).setText((String) modelo1.getValueAt(filas, 6));
        _textField.get(4).setText((String) modelo1.getValueAt(filas, 4));
        _textField.get(5).setText((String) modelo1.getValueAt(filas, 5));
        _checkBoxCredito.setSelected((Boolean) modelo1.getValueAt(filas, 7));
    }
    
    public final void restablecer() {
        seccion = 1;
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
        listClientes = clientes();
        if (!listClientes.isEmpty()) {
            _paginadorCliente = new Paginador<>(listClientes, _label.get(6), _reg_por_pagina);
        }
        
        SpinnerNumberModel model = new SpinnerNumberModel(
                new Integer(10), //Dato visualizado al inicio del Spinner
                new Integer(1), //Limite inferior
                new Integer(100), //Limite superior
                new Integer(1) //Incremento - Decremento
        );
        _spinnerPaginas.setModel(model);
        
        SearchClientes("");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pagos y Reportes">
    public void searchReportes(String valor) {
        String[] titulos = {"ID", "Cedula", "Nombre", "Apellido", "Deuda actual", "Fecha deuda", "Ultimo pago", "Fecha ult. pago", "Factura", "Fecha limite"};
        modelo2 = new DefaultTableModel(null, titulos);
        int inicio = (_num_paginas - 1) * _reg_por_pagina;
        List<TReportes_Cliente> reporteFilter;
        if (valor.equals("")) {
            reporteFilter = reporteCliente().stream()
                    .skip(inicio).limit(_reg_por_pagina)
                    .collect(Collectors.toList());
        } else {
            reporteFilter = reporteCliente().stream().filter(C -> C.getNumDoc()
                    .startsWith(valor) || C.getNombre().startsWith(valor) || C.getApellido().startsWith(valor)).skip(inicio).limit(_reg_por_pagina).collect(Collectors.toList());
        }
        if (!reporteFilter.isEmpty()) {
            reporteFilter.forEach(item -> {
                Object[] registros = {
                    item.getId(),
                    item.getNumDoc(),
                    item.getNombre(),
                    item.getApellido(),
                    item.getDeudaActual(),
                    item.getFechaDeuda(),
                    item.getUltimoPago(),
                    item.getFechaPago(),
                    item.getTicket(),
                    item.getFechaLimite(),};
                modelo2.addRow(registros);
            });
        }
        _tableReporte.setModel(modelo2);
        _tableReporte.setRowHeight(20);
        _tableReporte.getColumnModel().getColumn(0).setMaxWidth(0);
        _tableReporte.getColumnModel().getColumn(0).setMinWidth(0);
        _tableReporte.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public void GetReportCliente() {
        int filas = _tableReporte.getSelectedRow();
        _idCliente = (Integer) modelo2.getValueAt(filas, 0);
        _label.get(8).setText(_mony + _format.decimal((Double) modelo2.getValueAt(filas, 4)));
        _label.get(9).setText((String) modelo2.getValueAt(filas, 5));
        _label.get(10).setText(_mony + _format.decimal((Double) modelo2.getValueAt(filas, 6)));
        _label.get(11).setText((String) modelo2.getValueAt(filas, 7));
        _label.get(12).setText((String) modelo2.getValueAt(filas, 8));
    }
    
    public void restablecerReport() {
        listaReportes = reporteCliente();
        if (!listaReportes.isEmpty()) {
            _paginadorReportes = new Paginador<>(listaReportes, _label.get(6), _reg_por_pagina);
        }
        searchReportes("");
    }
    // </editor-fold>
    private List<TCliente> listClientes;
    private List<TReportes_Cliente> listaReportes;
    
    public void paginador(String metodo) {
        switch (metodo) {
            case "Primero":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _num_paginas = _paginadorCliente.primero();
                        }
                        break;
                    case 2:
                        if (!listaReportes.isEmpty()) {
                            _num_paginas = _paginadorCliente.primero();
                        }
                        break;
                }
                break;
            case "Anterior":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _num_paginas = _paginadorCliente.anterior();
                        }
                        break;
                    case 2:
                        if (!listaReportes.isEmpty()) {
                            _num_paginas = _paginadorCliente.anterior();
                        }
                        break;
                }
                break;
            case "Siguiente":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _num_paginas = _paginadorCliente.siguiente();
                        }
                        break;
                    case 2:
                        if (!listaReportes.isEmpty()) {
                            _num_paginas = _paginadorCliente.siguiente();
                        }
                }
                break;
            case "Ultimo":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _num_paginas = _paginadorCliente.ultimo();
                        }
                        break;
                    case 2:
                        if (!listaReportes.isEmpty()) {
                            _num_paginas = _paginadorCliente.ultimo();
                        }
                }
                break;
        }
        switch (seccion) {
            case 1:
                SearchClientes("");
                break;
            case 2:
                SearchClientes("");
                break;
        }
    }
    
    public void Registro_Paginas() {
        _num_paginas = 1;
        Number caja = (Number) _spinnerPaginas.getValue();
        _reg_por_pagina = caja.intValue();
        switch (seccion) {
            case 1:
                if (!listClientes.isEmpty()) {
                    _paginadorCliente = new Paginador<>(listClientes, _label.get(6), _reg_por_pagina);
                }
                SearchClientes("");
                break;
            case 2:
                if (!listClientes.isEmpty()) {
                    _paginadorCliente = new Paginador<>(listClientes, _label.get(6), _reg_por_pagina);
                }
                searchReportes("");
                break;
        }
    }
}
