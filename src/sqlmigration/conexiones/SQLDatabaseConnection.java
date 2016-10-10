package sqlmigration.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 *
 * @author kryst
 */
public class SQLDatabaseConnection {

    public static Connection getSqlServerConnection() {
        String connectionString
                = "jdbc:sqlserver://yourserver.database.windows.net:1433;"
                + "database=hr"
                + "user=krystel;"
                + "password=1234"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "hostNameInCertificate=*.database.windows.net;"
                + "loginTimeout=30;";

        // Declare the JDBC objects.  
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionString);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;

    }
}
