package sample;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBC {

        Connection dbConnection = null;

        public boolean connect(){
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                // String URL = "jdbc:mysql://194.47.47.18:3306/YOUR_DATABASE_NAME?user=YOUR_USER_NAME&password=YOUR_PASSWORD";
                //dbConnection = DriverManager.getConnection(URL);
                return true;
            } catch (Exception e) {
                System.err.println("ERROR: " + e);
                return false;
            }
        }//connect

        public void disconnect(){
            if (dbConnection!=null){
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

