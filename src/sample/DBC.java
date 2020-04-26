package sample;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBC {

        Connection dbConnection = null;

        public boolean connect(){
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                 String URL = "jdbc:mysql://den1.mysql3.gear.host:3306/projkurs2hkr?user=projkurs2hkr&password=Vr2Gr2-qb9O~";
                dbConnection = DriverManager.getConnection(URL);
                System.out.println("connected to db");
                return true;
            } catch (Exception e) {
                System.err.println("ERROR: " + e);
                return false;
            }
        }

        public void disconnect(){
            if (dbConnection!=null){
                try {
                    System.out.println("closing db");
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

