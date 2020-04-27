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
    // manageBooking
    // removeBooking
    //seeBookings

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
            boolean status = false;
            try {
                String query = "SELECT * FROM Account WHERE email = '" + email + "' AND password = '" + pass + "'";
                stmt = dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (!rs.next()) {
                    status=true;
                } else {
                    status=false;
                    do {
                        // lul inget :)
                    } while (rs.next());
                }

            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }

            return status;
    }
    public boolean verifyAccountSignUp(String s, String email, String phone){
            boolean status = false;
            try {
                String query = "SELECT email, phone FROM Account WHERE email = '" + email + "' AND phone = " + phone;
                System.out.print(query);
                stmt = dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.getString("email").isEmpty() && rs.getString("phone").isEmpty()) {
                    status = true;
                } else {
                    System.out.println("acc exists");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

               return status;
    }
    //seeUsers
    //setServiceCost
    //getPrice
    //get
    }

