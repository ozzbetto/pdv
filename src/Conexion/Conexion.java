/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;


/**
 *
 * @author frodriguez
 */
public class Conexion {

    private String db = "punto_de_ventas";
    private String user = "root";
    private String password = "1982";
    private String urlMysql = "jdbc:mysql://localhost/" + db + "?SslMode=none";
    private Connection conn = null;

    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(this.urlMysql,this.user,this.password);
            if(conn != null) {
                System.out.println("Conexión a base de datos " + this.db + " OK");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public Connection getConn() {
        return conn;
    }
    
}
