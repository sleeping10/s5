package sample;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBC {
    private PreparedStatement statement = null;
    private Connection conn = null;

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
        // addBooking
    // manageBooking
    // removeBooking
    //seeBookings
    //saveAccount
    public void saveAccount(Account acc){
            try {
                String query = "INSERT INTO Account (email, password, name, phone, accessType) VALUES (?, ?, ?, ?, ?)";
                statement = conn.prepareStatement(query);
                statement.setString(1, acc.getEmail());
                statement.setString(2, acc.getPassword());
                statement.setString(3, acc.getName());
                statement.setString(4, acc.getPhone());
                statement.setString(5, acc.getAccessType().toString());
                statement.execute();
                statement.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
    }
    //manageAccount
    //verifyAccount
    //seeUsers
    //setServiceCost
    //getPrice
    //get
    }

