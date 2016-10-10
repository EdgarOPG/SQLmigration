
package sqlmigration.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger; 

/**
 *
 * @author kryst
 */

public class SqlServerConnection {  
         
    String connectionString =  
        "jdbc:sqlserver://yourserver.database.windows.net:1433;"  
        + "database=hr"  
        + "user=krystel;"  
        + "password=1234"  
        + "encrypt=true;"  
        + "trustServerCertificate=false;"  
        + "hostNameInCertificate=*.database.windows.net;"  
        + "loginTimeout=30;";

    private static OracleConnection INSTANCE;
    private Connection con;

    private SqlServerConnection() {
        this.initConection();
    }

    private void initConection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionString);
//            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OracleConnection.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    public static OracleConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OracleConnection();
        }
        return INSTANCE;
    }

    public Connection getCon() {
        return con;
    }
                
                
}  
