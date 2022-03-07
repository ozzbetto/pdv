/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author frodriguez
 */
public class Calendario {

    private DateFormat dateFormat;
    private Date date = new Date();
    private Calendar c = new GregorianCalendar();
    private final String Fecha;
    private final String Dia;
    private final String Mes;
    private final String Year;
    private final String Hora;
    private String am_pm;

    public Calendario() {
        switch (c.get(Calendar.AM_PM)) {
            case 0:
                am_pm = "am";
                break;
            case 1:
                am_pm = "pm";
                break;
        }
        dateFormat = new SimpleDateFormat("dd");
        Dia = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("MM");
        Mes = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("yyyy");
        Year = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Fecha = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("hh:mm:ss");
        Hora = dateFormat.format(date) + " " + am_pm;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getDia() {
        return Dia;
    }

    public String getMes() {
        return Mes;
    }

    public String getYear() {
        return Year;
    }

    public String getHora() {
        return Hora;
    }
    
}
