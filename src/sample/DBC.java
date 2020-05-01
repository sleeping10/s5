package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBC {
    private PreparedStatement statement = null;
    private Statement stmt = null;
    private Account acc;
    Connection dbConnection = null;

    private static DBC single_instance = null;

    private DBC() {
    }

    public static DBC getInstance() {
        if (single_instance == null) {
            single_instance = new DBC();
        }
        return single_instance;
    }


    public boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String URL = "jdbc:mysql://den1.mysql4.gear.host:3306/projektkurs2hkr?user=projektkurs2hkr&password=Tm3kp8U406~-&useSSL=false";
            dbConnection = DriverManager.getConnection(URL);
            stmt = dbConnection.createStatement();
            System.out.println("DEBUG: Connected to db");
            return true;
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            return false;
        }
    }

    public void disconnect() {
        if (dbConnection != null) {
            try {
                System.out.println("DEBUG: Closing db connection");
                stmt.close();
                dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // addBooking
    public void addBooking(Booking booking) {
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
    public void manageBooking(Booking booking) {

    }

    // seeBookings
    public void seeBookings() {

    }

    // removeBooking
    public void removeBooking(Booking booking) {

    }

    public String getAccountAsString() {
        return acc.toString();
    }

    public Account getAccount() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }


    public void saveAccount() {
        try {
            String query = "INSERT INTO Account (email, password, name, phone, loginStatus, Access_AccessID) VALUES (?, ?, ?, ?, ?, ?)";
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, acc.getEmail());
            statement.setString(2, acc.getPassword());
            statement.setString(3, acc.getName());
            statement.setString(4, acc.getPhone());
            statement.setBoolean(5, false);
            statement.setInt(6, 3);
            statement.execute();
            statement.close();
            System.out.println("DEBUG: Sign up successful, saved in remote DB");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateAccount(String name,String pass,String phone,int accountID) {
        int resultSet;
        String updateQuery = "UPDATE projektkurs2hkr.account set password='"+pass+"', name='"+name+"',phone='"+phone+"'  where accountID='"+accountID+"' ";
        try {
            stmt=dbConnection.createStatement();
            resultSet = stmt.executeUpdate(updateQuery);
            if (resultSet>0){
                System.out.println("gick: "+resultSet);
            }else System.out.println("gick ej: "+resultSet);
            System.out.println(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean verifyAccount(String email, String pass, String phone) {
        boolean statusLogin = false;
        boolean statusSignUp;
        boolean status = false;
        String dbmail = "";
        String dbpass = "";
        String dbname = "";
        String dbphone = "";
        boolean dbLoginStatus = false;
        int accessType = -1;
        int dbAccID = -1;

        String queryLogin = "SELECT * FROM Account WHERE email = '" + email + "' AND password = '" + pass + "'";
        String querySignup = "SELECT * FROM Account WHERE email = '" + email + "' OR phone = '" + phone + "'";
        try {
            stmt = dbConnection.createStatement();
            if (phone != null) {
                System.out.println("DEBUG: Sign up process initiated");
                ResultSet rsSignup = stmt.executeQuery(querySignup);

                if (!rsSignup.next()) {
                    statusSignUp = true;
                } else {
                    statusSignUp = false;
                    System.out.println("DEBUG: Sign up failed");
                }
                rsSignup.close();
                status = statusSignUp;
            } else if (phone == null) {
                System.out.println("DEBUG: Log in process initiated");
                ResultSet rsLogin = stmt.executeQuery(queryLogin);

                if (rsLogin.next()) {
                    dbAccID = rsLogin.getInt(1);
                    dbmail = rsLogin.getString(2);
                    dbpass = rsLogin.getString(3);
                    dbname = rsLogin.getString(4);
                    dbphone = rsLogin.getString(5);
                    dbLoginStatus = rsLogin.getBoolean(6);
                    accessType = rsLogin.getInt(7);
                }

                if (dbmail.matches(email) && dbpass.matches(pass)) {
                    if (dbLoginStatus == false){
                        statusLogin = true;
                        acc = new Account(dbAccID, dbmail, dbpass, dbname, dbphone, true, accessType);
                        setLoginStatus(true);
                    }else{
                        statusLogin = false;
                        System.out.println("DEBUG: User already logged in");
                    }
                }else{
                    System.out.println("DEBUG: Log in failed");
                }
                rsLogin.close();
                status = statusLogin;
            }
            stmt.close();
        } catch (Exception ex) {
            System.out.println("ERROR; " + ex.getMessage());
        }
        return status;
    }

    public void setLoginStatus(boolean toggle) {
        String query = "UPDATE Account SET loginStatus = ? WHERE AccountID = ?";
        try {
            statement = dbConnection.prepareStatement(query);
            statement.setBoolean(1, toggle);
            statement.setInt(2, acc.getAccountID());
            statement.executeUpdate();
            statement.close();
            if (!toggle){
                System.out.println("DEBUG: User logged out");
            }else{
                System.out.println("DEBUG: User logged in");
            }
        } catch (Exception e) {
            System.out.println("ERROR SETLOGINSTATUS: " + e.getMessage());
        }
    }

    public int getAccountIDfromDB(String email, String pass) {
        String queryGetAccID = "SELECT AccountID from Account WHERE email = '" + email + "' AND password = '" + pass + "'";
        int out = -1;
        try {
            stmt = dbConnection.createStatement();
            ResultSet rsGetID = stmt.executeQuery(queryGetAccID);

            if (rsGetID.next()) {
                out = rsGetID.getInt(1);
            }
            stmt.close();
            rsGetID.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return out;
    }


    public boolean checkEmailDB(String email) {
        String dbemail = "";
        boolean status = false;
        try {
            stmt = dbConnection.createStatement();
            ResultSet rsCheckEmail = stmt.executeQuery("SELECT * FROM Account WHERE email = '" + email + "'");

            if (rsCheckEmail.next()) {
                dbemail = rsCheckEmail.getString(2);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (dbemail.matches(email)) {
            status = true;
        }
        return status;
    }

    private String tfEmail = null;
    private String pfPass = null;

    public void setTfUser(String tfUser) {
        this.tfEmail = tfUser;
    }

    public void setPfPass(String pfPass) {
        this.pfPass = pfPass;
    }

    public void getAccountFromDb() {
        String query = "SELECT * FROM Account WHERE email = '" + tfEmail + "' AND password = '" + pfPass + "'";
        try {
            stmt = dbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                acc = new Account(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), true, resultSet.getInt(7));
                System.out.println(acc.toString());
                setAcc(acc);
            }
            stmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Account> seeUsers(){
        ArrayList<Account> allUsers = new ArrayList<>();
        Account tempAcc = null;
        try {
            stmt = dbConnection.createStatement();
            String queryUsers = "SELECT accountID, email, password, name, phone, loginStatus, access_accessID FROM Account";
            ResultSet rsUsers = stmt.executeQuery(queryUsers);
            if (!rsUsers.next()) {

            } else {

                do {
                    tempAcc = new Account(rsUsers.getInt(1),rsUsers.getString(2), rsUsers.getString(3),rsUsers.getString(4),
                            rsUsers.getString(5), rsUsers.getBoolean(6),rsUsers.getInt(7));
                    System.out.println(tempAcc.getName()+ tempAcc.getPassword()+ tempAcc.getPhone()+tempAcc.getAccountID()+ tempAcc.getEmail());
                    allUsers.add(tempAcc);
                } while (rsUsers.next());
            }
            stmt.close();

        }catch(Exception ex){
            System.out.println("DEBUG: see users");
            ex.printStackTrace();
        }
        for (int i = 0; i <allUsers.size() ; i++) {
            System.out.println(allUsers.get(i).toString());
        }
        return allUsers;
    }
    public void setServiceCost(String serviceName, double price){
       String queryPrice = "UPDATE Service SET serviceCost = ? WHERE serviceName = '" + serviceName + "'";
       try {
           PreparedStatement prepstmt = dbConnection.prepareStatement(queryPrice);
           prepstmt.setDouble(1,price);
           prepstmt.executeUpdate();
           prepstmt.close();
       }catch (Exception ex){
           ex.printStackTrace();
       }

    }

    public String getServiceCost(String serviceName){
        String query = "SELECT * FROM Service where serviceName = '" + serviceName + "'";
        double cost = 0;
        double discount = 0;
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        Timestamp discount_startdate = null;
        Timestamp discount_enddate = null;
        try {
            stmt = dbConnection.createStatement();
            ResultSet rsCost = stmt.executeQuery(query);
            if (rsCost.next()) {
                cost = rsCost.getDouble(2);
                discount = rsCost.getDouble(3);
                if (!rsCost.wasNull()){
                    discount_startdate = rsCost.getTimestamp(4);
                    discount_enddate = rsCost.getTimestamp(5);
                }

            }
        }catch (NullPointerException ex){
            System.out.println("DEBUG: " + ex.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("TIME: " + current_time.toString());

        if (discount_startdate != null && discount_enddate != null){
            if (current_time.after(discount_startdate) && current_time.before(discount_enddate)){
                System.out.println("Fucker");
                return "$" + cost + " - " + discount*100 + "% OFF, NOW: $" + Math.round(cost * (1 - discount));
            }else{
                System.out.println("Fucker2");
                return "$" + cost;
            }
        }else{
            return "$" + cost;
        }
    }

    public void setDiscount(String serviceName, double discount, Timestamp startDate, Timestamp endDate){
        discount = discount / 100;

        String queryDiscount = "UPDATE Service SET discount = ?, discountStart = ?, discountEnd = ? WHERE serviceName = '" + serviceName + "'";
        try {
            statement = dbConnection.prepareStatement(queryDiscount);
            statement.setDouble(1, discount);
            statement.setTimestamp(2, startDate);
            statement.setTimestamp(3, endDate);
            statement.executeUpdate();
            statement.close();
            System.out.println("DEBUG: " + discount +", " + startDate);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void getDiscount (){
        String query = "SELECT discountStart FROM Service where serviceName = 'Basic Inspection'";
        Timestamp ts = null;
        try{
            stmt = dbConnection.createStatement();
            ResultSet rsCost = stmt.executeQuery(query);
            if (rsCost.next()) {

                ts = rsCost.getTimestamp(1);

            }
            System.out.println(ts.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

