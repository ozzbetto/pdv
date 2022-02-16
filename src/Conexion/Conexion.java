/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frodriguez
 */
public class Conexion {

    private String db = "punto_de_ventas";
    private String user = "root";
    private String password = "1992";
    private String urlMysql = "jdbc:mysql://localhost/" + db + "?SslMode=none";
    private Connection conn = null;

    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(this.urlMysql,this.user,this.password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public Connection getConn() {
        return conn;
    }
    
}
