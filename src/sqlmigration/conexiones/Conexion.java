/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlmigration.conexiones;

/**
 *
 * @author eopg9
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Conexion a base deda tos MYSQL que utuliza un singleton
 * @author Luis Antonio Ramirez Martinez
 */
public class Conexion {
    
    private final static String USUARIO = "hr";
    private final static String PASSWORD = "hr";
    private final static String CONEXION = "jdbc:oracle:thin:@localhost:1521:XE";
    private static Conexion INSTANCE;
    private Connection con;

    private Conexion() {
        this.initConection();
    }

    private void initConection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(CONEXION, USUARIO, PASSWORD);
//            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    public static Conexion getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Conexion();
        }
        return INSTANCE;
    }

    public Connection getCon() {
        return con;
    }

}
