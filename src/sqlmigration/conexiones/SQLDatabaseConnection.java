
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
      
            // Connect to your database.  
            // Replace server name, username, and password with your credentials  
            public static void main(String[] args) {  
                String connectionString =  
                    "jdbc:sqlserver://yourserver.database.windows.net:1433;"  
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
      
                }  
                catch (Exception e) {  
                    e.printStackTrace();  
                }  
                finally {  
                    if (connection != null) try { connection.close(); } catch(Exception e) {}  
                }  
            }  
        }  
