package sample;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBC {
    private PreparedStatement statement = null;
    private Statement stmt = null;

        Connection dbConnection = null;

    private static DBC single_instance = null;

        private DBC(){}

        public static DBC getInstance(){
            if (single_instance==null){
                single_instance=new DBC();
            }
            return single_instance;
        }


        public boolean connect(){
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                 String URL = "jdbc:mysql://den1.mysql3.gear.host:3306/projkurs2hkr?user=projkurs2hkr&password=Vr2Gr2-qb9O~";
                dbConnection = DriverManager.getConnection(URL);
                stmt = dbConnection.createStatement();
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
                    stmt.close();
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // addBooking
    public void addBooking(Booking booking){
//        try {
//            String query = "INSERT INTO Booking (bookingDate, bookingDesc) VALUES (?, ?)";
//            statement = dbConnection.prepareStatement(query);
//            statement.setString(1, acc.getEmail());
//            statement.setString(2, acc.getPassword());
//            statement.execute();
//            statement.close();
//            System.out.println("felix är cool");
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
    }
    // manageBooking
    public void manageBooking (Booking booking){

    }
    // seeBookings
    public void seeBookings (){

    }
    // removeBooking
    public void removeBooking(Booking booking){

    }
//    public Account getAccount(){
//
//    }


    public void saveAccount(Account acc){
            try {
                String query = "INSERT INTO Account (email, password, name, phone) VALUES (?, ?, ?, ?)";
                statement = dbConnection.prepareStatement(query);
                statement.setString(1, acc.getEmail());
                statement.setString(2, acc.getPassword());
                statement.setString(3, acc.getName());
                statement.setString(4, acc.getPhone());
                statement.execute();
                statement.close();
                System.out.println("felix är cool");
            }catch (Exception ex){
                ex.printStackTrace();
            }
    }

    public void manageAccount(Account acc){

    }

    public boolean verifyAccount(String email, String pass, String phone){
            boolean statusLogin = false;
            boolean statusSignUp = false;
            boolean status = false;
            String queryLogin = "SELECT * FROM Account WHERE email = '" + email + "' AND password = '" + pass + "'";
            String querySignup = "SELECT * FROM Account WHERE email = '" + email + "' OR phone = '" + phone + "'";
            try {
                if (pass == null){
                    stmt = dbConnection.createStatement();
                    ResultSet rsSignup = stmt.executeQuery(querySignup);

                    if (!rsSignup.next()) {
                        statusSignUp=true;
                    } else {
                        statusSignUp=false;
                        do {
                            // lul inget :)
                        } while (rsSignup.next());
                    }
                    status = statusSignUp;
                }
                else if (pass != null){
                    stmt = dbConnection.createStatement();
                    ResultSet rsLogin = stmt.executeQuery(queryLogin);
                    if (!rsLogin.next()) {
                        statusLogin=true;
                    } else {
                        statusLogin=false;
                        do {
                            // lul inget :)
                        } while (rsLogin.next());
                    }
                    status = statusLogin;
                }
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }

            return status;
    }
    //seeUsers
    //setServiceCost
    //getPrice
    //get
    }

