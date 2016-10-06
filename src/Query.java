
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        q.getTablenames("HR");
        q.getNameColums("EMPLOYEES");
        
    }
    
public List<String> getTablenames(String user){
    try {
        ArrayList<String> tablesnamesList = new ArrayList<>();
        Statement st = Conexion.getInstance().getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT table_name FROM all_all_tables WHERE owner = '" + user + "'");
        while (rs.next()) {
            tablesnamesList.add(rs.getString(1));
        }
        for(int x=0; x < tablesnamesList.size(); x++) {
            System.out.println(tablesnamesList.get(x));
        }
        return tablesnamesList;
    } catch (SQLException ex) {
        System.out.println(ex);
        return null;
    }
}

public List<String> getNameColums(String tablename){
    try {
        ArrayList<String> columsList = new ArrayList<>();
        Statement st = Conexion.getInstance().getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT column_name FROM all_tab_columns "
                + "WHERE table_name = '"+ tablename + "'");
        while (rs.next()) {
            columsList.add(rs.getString(1));
        }
        for(int x=0; x < columsList.size(); x++) {
            System.out.println(columsList.get(x));
        }
        return columsList;
    } catch (SQLException ex) {
        System.out.println(ex);
        return null;
    }
}
    
    
    
    public void getDirectorById() {
        try {
            Statement st = Conexion.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM all_all_tables WHERE owner = 'HR'");
            while (rs.next()) {
                System.out.println(rs.getMetaData().getColumnCount());
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }    
}
