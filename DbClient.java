package carsharing;

import java.sql.*;

public class DbClient {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    Connection conn = null;
    Statement stmt = null;

    public DbClient(){
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

        } catch(Exception e) {

        }
    }

    public void run(String str){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(str);
            //stmt.close();
        } catch(Exception e) {

        }
    }

    public ResultSet find(String str){
        try {
            stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(str);
            //stmt.close();
            return result;
        } catch(Exception e) {

        }
        return null;
    }

    public void close(){

        try {
            conn.close();
        } catch(Exception e) {

        }
    }
}
