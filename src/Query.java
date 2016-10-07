
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static javafx.scene.input.KeyCode.T;
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
    
    //TODO Con string format crear el create tables
    public static void main(String[] args) {
        Query q = new Query();
        
        for (int i = 0; i < q.getTablenames("HR").size(); i++) {
            System.out.println(q.getTablenames("HR").get(i));
            System.out.println(fieldsToQuery(q.getNameColums(q.getTablenames("HR").get(i))));
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
        ResultSet rs = st.executeQuery("SELECT column_name || ' ' "
                                        + "|| DATA_TYPE || ' ' || NULLABLE "
                                        + "FROM all_tab_cols "
                                        + "WHERE table_name = '"
                                        + tablename + "'");
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
        
public static String fieldsToQuery(List<String> fields) {
            String campos = "";
            List<String> fieldsList = fields;
            String [] fieldsArray = fieldsList.toArray(new String[fieldsList.size()]);
            for (String field : fieldsArray) {
                campos = String.format("%s, %s", campos, field);   
            }
            campos = campos.substring(1);
            return campos;
            }
}
