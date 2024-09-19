package Database;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String databaseUri = "jdbc:mysql://127.0.0.1:3306/HospitalDB";
    private static String name = "root";
    private static String password = "12345678";
    private static Connection con;

    static {
       con = null; 
    }

    public static Connection getConnection() throws SQLException {        
        if (con == null) {
            con = DriverManager.getConnection(databaseUri, name, password);
        }        
        return con;    
    }
}
