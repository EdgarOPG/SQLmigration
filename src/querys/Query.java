package querys;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sqlmigration.conexiones.Conexion;


/**
 *
 * @author eopg9
 */
public class Query {
    
    //TODO Con string format crear el create tables
    
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
                                        + " data_type, nullable,"
                                        + "DATA_LENGTH "
                                        + "FROM all_tab_cols "
                                        + "WHERE table_name = '"
                                        + tablename + "' "
                                        + "ORDER BY column_id");
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

public ArrayList<String> getRows(String tablename){
    try {
        Statement st = Conexion.getInstance().getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tablename );
        ArrayList<String> columnList = new ArrayList<>();
        String cell = "";
        int j = 0;
        while (rs.next()) {
            List<String> rowList = new ArrayList<>();
            Integer columnNumber = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnNumber; i++) {
            String columnValue = rs.getString(i);
            Object obj = rs.getObject(i);
                if(obj == null){
                    cell = "NULL";
                } else {
                    if(obj.getClass().getSimpleName().equals("String")) {
                        columnValue = String.format("'%s'", columnValue);
                    } 
                    else if (rs.getMetaData().getColumnTypeName(i).equals("DATE")) {
                        columnValue = String.format("convert(datetime2,'%s')", columnValue);
                    }
                    cell = obj.toString();
                }
           rowList.add(columnValue);
        }
        columnList.add(fieldsToQuery(rowList));
        }
        return columnList;
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

public static String concatInstructions(String tableSpace){
        Query q = new Query();
        String script = "";
        for (int i = 0; i < q.getTablenames(tableSpace).size(); i++) {
            String tableName = q.getTablenames(tableSpace).get(i);
            String tableFields = 
                    fieldsToQuery(q.getNameColums(q.getTablenames(tableSpace).get(i)));
            String createInstructions = String.format("CREATE TABLE %s(%s);",
                    tableName, tableFields);
            String insertRow = "";
            String blockScript = "";
            ArrayList arrayRows = q.getRows(tableName); 
            Iterator<List> iteratorRows = arrayRows.iterator();
            while(iteratorRows.hasNext()){
                String insertInstructions = "";
                insertInstructions = 
                        String.format("INSERT INTO %s VALUES(%s);",
                                tableName, iteratorRows.next());
                insertRow = String.format("%s\n%s",insertRow,insertInstructions);
            }
                blockScript = String.format("\n%s%s",createInstructions,insertRow);
                script = String.format("%s\n%s", script, blockScript);
        }
    return script;
}

}
