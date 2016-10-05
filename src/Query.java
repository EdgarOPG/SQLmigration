
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sqlmigration.conexiones.Conexion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eopg9
 */
public class Query {
    public static void main(String[] args) {
        Query q = new Query();
        q.getDirectorById();
    }
    public void getDirectorById() {
        Integer i = 0;
        try {
            Statement st = Conexion.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYEES");
            while (rs.next()) {
                System.out.println(i + " " + rs.getString(2));
                i++;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }    
}
