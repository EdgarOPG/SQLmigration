package sqlmigration.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kryst
 */
public class SQLDatabaseConnection {

    private static Connection getSqlServerConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionString
                    = "jdbc:sqlserver://localhost:1433;"
                    + "database=hr;"
                    + "user=krys;"
                    + "password=1234;"
                    + "loginTimeout=5;";
            
            connection = DriverManager.getConnection(connectionString);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SQLDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;

    }

    public static ResultSet makeSqlServerQuery(String sql) {
        ResultSet rs = null;
        try {
            Statement stmt = getSqlServerConnection().createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
