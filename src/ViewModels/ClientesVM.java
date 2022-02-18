/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import Conexion.Consult;
import Models.TClientes;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author frodriguez
 */
public class ClientesVM extends Consult{
    private String accion = "insert";
    private final ArrayList<JLabel> _label;
    private final ArrayList<JTextField> _textField;
    public ClientesVM(Object[] objects, ArrayList<JLabel> label, ArrayList<JTextField> textField) {
        _label = label;
        _textField = textField;
    }
    
    public void registrarCliente() {
        if(_textField.get(0).getText().equals("")) {
            _label.get(0).setText("Falta Número de Cédula");
            _label.get(0).setForeground(Color.RED);
            _textField.get(0).requestFocus();
        } else if(_textField.get(1).getText().equals("")) {
            _label.get(1).setText("Ingrese el nombre");
            _label.get(1).setForeground(Color.RED);
            _textField.get(1).requestFocus();
        } else if(_textField.get(2).getText().equals("")) {
            _label.get(2).setText("Ingrese el apellido");
            _label.get(2).setForeground(Color.RED);
            _textField.get(2).requestFocus();
        } else {
            int count;
            List<TClientes> listEmail = clientes();
        }
    }
    
}
