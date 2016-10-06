
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        
        for (int i = 0; i < q.getTablenames("HR").size(); i++) {
            System.out.println(q.getTablenames("HR").get(i));
            System.out.println(q.getNameColums(q.getTablenames("HR").get(i)));
//            System.out.println(q.getRows(q.getTablenames("HR").get(i)));
        }
    }
    
public List<String> getTablenames(String user){
    try {
        ArrayList<String> tablesnamesList = new ArrayList<>();
        Statement st = Conexion.getInstance().getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT table_name FROM all_all_tables "
                                        + "WHERE owner = '" + user + "'");
        while (rs.next()) {
            tablesnamesList.add(rs.getString(1));
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
        ResultSet rs = st.executeQuery("        SELECT column_name FROM all_tab_columns "
                                        + "WHERE table_name = '"+ tablename + "'");
        while (rs.next()) {
            columsList.add(rs.getString(1));
        }
        return columsList;
    } catch (SQLException ex) {
        System.out.println(ex);
        return null;
    }
}

public List<String> getRows(String tablename){
    try {
        ArrayList<String> columsList = new ArrayList<>();
        Statement st = Conexion.getInstance().getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM "+ tablename );
        while (rs.next()) {
            columsList.add(rs.getString(1));
        }
        return columsList;
    } catch (SQLException ex) {
        System.out.println(ex);
        return null;
    }
}
        
}
