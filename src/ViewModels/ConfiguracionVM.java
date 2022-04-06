/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import Conexion.Consult;
import Models.TConfiguracion;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author frodriguez
 */
public class ConfiguracionVM extends Consult {

    public static String Mony;
    private static List<JRadioButton> _radio;

    public ConfiguracionVM() {
        TypeMoney();
    }

    public ConfiguracionVM(List<JRadioButton> radio) {
        _radio = radio;
        RadioEvent();
        TypeMoney();
    }

    private void RadioEvent() {
        _radio.get(0).addActionListener((ActionEvent e) -> {
            TypeMoney("Gs.", _radio.get(0).isSelected());
        });

        _radio.get(1).addActionListener((ActionEvent e) -> {
            TypeMoney("US$", _radio.get(1).isSelected());
        });

        _radio.get(2).addActionListener((ActionEvent e) -> {
            TypeMoney("R$", _radio.get(2).isSelected());
        });
    }

    private String sqlConfig = "INSERT INTO tconfiguracion(TypeMoney) VALUES(?)";

    private void TypeMoney() {
        List<TConfiguracion> config = config();
        final QueryRunner qr = new QueryRunner(true);
        if (config.isEmpty()) {
            Mony = "Gs.";
            Object[] dataConfig = {Mony};
            try {
                qr.insert(getConn(), sqlConfig, new ColumnListHandler(), dataConfig);
            } catch (SQLException ex) {

            }
        } else {
            int count = config.size();
            count--;
            TConfiguracion data = config.get(count);
            Mony = data.getTypeMoney();
            switch (Mony) {
                case "Gs.":
                    _radio.get(0).setSelected(true);
                    break;
                case "US$":
                    _radio.get(1).setSelected(true);
                case "R$":
                    _radio.get(2).setSelected(true);
            }
        }
    }

    private void TypeMoney(String typeMoney, boolean valor) {
        final QueryRunner qr = new QueryRunner(true);
        if (valor) {
            try {
                List<TConfiguracion> config = config();
                if (config.isEmpty()) {
                    Mony = typeMoney;
                    Object[] dataConfig = {Mony};

                    qr.insert(getConn(), sqlConfig, new ColumnListHandler(), dataConfig);

                } else {
                    int count = config.size();
                    count--;
                    TConfiguracion data = config.get(count);
                    if (data.getTypeMoney().equals(typeMoney)) {
                        Mony = typeMoney;
                    } else {
                        Mony = typeMoney;
                        Object[] dataConfig = {Mony};
                        qr.insert(getConn(), sqlConfig, new ColumnListHandler(), dataConfig);
                    }
                }
            } catch (SQLException ex) {
            }
        }
    }
}
