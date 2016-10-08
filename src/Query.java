
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
    
    //TODO Con string format crear el create tables
    public static void main(String[] args) {
        Query q = new Query();
        String fields;
        for (int i = 0; i < q.getTablenames("HR").size(); i++) {
            String tableName = q.getTablenames("HR").get(i);
            String tableFields = fieldsToQuery(q.getNameColums(q.getTablenames("HR").get(i)));
            String createInstruction = String.format("CREATE TABLE %s( %s );", tableName, tableFields);
            System.out.println(createInstruction);
            ArrayList arrayRows = q.getRows( q.getNameColums(tableName).size() ,tableName);        
            System.out.println(arrayRows);
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
        List<String> columsList = new ArrayList<>();
        Statement st = Conexion.getInstance().getCon().createStatement();
        String columnName = "";
        String dataType = "";
        String nullable = "";
        String query = ""; 
        Integer dataLength = 0;
        ResultSet rs = st.executeQuery("SELECT column_name,"
                                        + " DATA_TYPE, NULLABLE,"
                                        + "DATA_LENGTH "
                                        + "FROM all_tab_cols "
                                        + "WHERE table_name = '"
                                        + tablename + "'");
        while (rs.next()) {
            columnName = rs.getString("COLUMN_NAME");
            dataLength = rs.getInt("DATA_LENGTH");
            if(rs.getString("DATA_TYPE").equals("NUMBER")) {
                dataType = "INT";
            } else if(rs.getString("DATA_TYPE").equals("VARCHAR2")) {
                dataType = "VARCHAR("+ dataLength.toString() +")";
            } else if(rs.getString("DATA_TYPE").equals("CHAR")) {
                dataType = "CHAR("+ dataLength.toString() +")";
            } else {
                dataType = rs.getString("DATA_TYPE");
            }
            if(rs.getString("NULLABLE").equals("N")) {
                nullable = "NOT NULL";
            }else if (rs.getString("NULLABLE").equals("Y")) {
                nullable = "";
            }
            query = String.format("%s %s %s", columnName, dataType, nullable);
            columsList.add(query);
        }
        return columsList;
    } catch (SQLException ex) {
        System.out.println(ex);
        return null;
    }
}

public ArrayList<String> getRows(Integer columnNumber, String tablename){
    try {
        ArrayList<String> columsList = new ArrayList<>();
        Statement st = Conexion.getInstance().getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tablename );
        String cell = "";
        if (rs.next()) {
            for (int i = 1; i <= columnNumber; i++) {
                Object obj = rs.getObject(i);
                if(obj == null){
                    cell = "NULL";
                } else {
                    cell = obj.toString();
                }
                    columsList.add(cell);
            }
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
