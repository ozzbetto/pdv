/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Library;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author frodriguez
 */
public class FormatDecimal {
    Number numero;
    DecimalFormat formateador = new DecimalFormat("###,###,##0.00");
    public String decimal(double formato) {
        return formateador.format(formato);
    }
    
    public double reconstruir(String formato) {
        try {
            numero = formateador.parse(formato.replace(" ", ""));
        } catch (ParseException ex) {
            System.out.println("Error: " +ex);
        }
        return numero.doubleValue();
    }
}
