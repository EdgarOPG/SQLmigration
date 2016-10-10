/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlmigration;

import querys.Query;
import querys.SqlServerQuery;
import sqlmigration.conexiones.SQLDatabaseConnection;

/**
 *
 * @author eopg9
 */
public class SQLmigration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("corriendo");
        System.out.println(Query.concatInstructions("HR"));//System.out.println(SQLDatabaseConnection.makeSqlServerQuery("SELECT name FROM sys.databases WHERE state != 0;"));
    }
    
}
