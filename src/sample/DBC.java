package sample;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//This class is a Singleton class
public class DBC {
    private PreparedStatement statement = null;
    private Statement stmt = null;
    Connection dbConnection = null;
    private final String database_url = "jdbc:mysql://den1.mysql4.gear.host:3306/projektkurs2hkr?user=projektkurs2hkr&password=Rj7jS-ahBhu-&useSSL=false";

    private static DBC single_instance = null;

    private Account acc;

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
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(database_url);
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

    public void addBooking(Booking booking) {
        java.util.Date utilDate = booking.getDate();
        java.sql.Timestamp sq = new java.sql.Timestamp(utilDate.getTime());
        System.out.println("DEBUG: TIMESTAMP : " + sq.toString());
        System.out.println("DEBUG BOOKING: " + booking.getBookingDesc());
        int totalBookings = 0;

        try {
            String queryAddBooking = "INSERT INTO Booking (date, bookingDesc, Account_AccountID, serviceCompleted, licenseID)" +
                    "VALUES (?, ?, ?, ?, ?)";
            String queryGetTotalBookings = "select count(bookingID) from Booking;";

            stmt = dbConnection.createStatement();


            statement = dbConnection.prepareStatement(queryAddBooking);
            statement.setTimestamp(1, sq);
            statement.setString(2, booking.getBookingDesc());
            statement.setInt(3, booking.getAccountID());
            statement.setBoolean(4, false);
            statement.setString(5, booking.getLicensePlate());
            statement.execute();
            statement.close();
            System.out.println("DEBUG: Booking added");


            ResultSet rsid = stmt.executeQuery(queryGetTotalBookings);
            if (rsid.next()) {
                totalBookings = rsid.getInt(1);
            }
            stmt.close();

            System.out.println("DEBUG total booking: " + totalBookings);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String queryAddServicesToBooking = "INSERT INTO Booking_has_Service (Booking_bookingID, Service_serviceName)" +
                    "VALUES (?, ?)";


            statement = dbConnection.prepareStatement(queryAddServicesToBooking);

            for (int i = 0; i < booking.getServices().size(); i++) {
                statement.setInt(1, totalBookings);
                statement.setString(2, booking.getServices().get(i).getServiceName());
                statement.execute();
                System.out.println("Service ADD: " + booking.getServices().get(i).getServiceName());
            }
            statement.close();
            System.out.println("DEBUG: Booking connected to Services");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeBooking(Booking booking) {
        String query1 = "DELETE FROM booking_has_service WHERE booking_bookingid ='"+booking.getBookingID()+"'";
        String query2 = "DELETE FROM Booking WHERE bookingID ='" +booking.getBookingID()+"'";
        try {
            statement = dbConnection.prepareStatement(query1);
            statement.executeUpdate();
            statement.close();
            statement=dbConnection.prepareStatement(query2);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Service getService(String name) {

        String query = "SELECT * From Service where serviceName = '" + name + "'";
        Service tempService = null;
        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                tempService = new Service(rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getInt(6));
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return tempService;
    }


    // gets a complete account from accountID (booking -> getAccountID -> this method
    public Account getAccount(int accountID) {
        Account acc = null;
        int id = 0;
        String email = "";
        String password = "";
        String name = "";
        String phone = "";
        int access = 0;
        String query = "SELECT * FROM Account WHERE accountID = '" + accountID + "'";
        try {
            stmt = dbConnection.createStatement();
            ResultSet rsDetailedAcc = stmt.executeQuery(query);
            while (rsDetailedAcc.next()) {
                id = rsDetailedAcc.getInt(1);
                email = rsDetailedAcc.getString(2);
                password = rsDetailedAcc.getString(3);
                name = rsDetailedAcc.getString(4);
                phone = rsDetailedAcc.getString(5);
                access = rsDetailedAcc.getInt(7);
            }
            acc = new Account(id, email, password, name, phone, access);
            rsDetailedAcc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return acc;
    }

    // used in detailed booking view to change the date and description of booking
    public void updateBooking(Booking booking) {
        java.util.Date utilDate = booking.getDate();
        java.sql.Timestamp sq = new java.sql.Timestamp(utilDate.getTime());
        System.out.println(sq);
        String query = "UPDATE Booking SET bookingDesc = '" + booking.getBookingDesc() + "', date = '" + sq + "' WHERE bookingID = " + booking.getBookingID();
        try {
            statement = dbConnection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    //Gets all the bookings from the Database
    public ArrayList<Booking> getAllBookings() {
        ResultSet rs;
        ArrayList<Booking> bookings = new ArrayList<>();
        ArrayList<Service> tempServices = new ArrayList<>();
        Booking tempBooking = null;
        String queryAdmin = "SELECT bookingID, date, bookingDesc, account_accountID, serviceCompleted, " +
                "licenseID, service_ServiceName, serviceCost" +
                ", discount, discountStart, discountEnd, estimatedTime " +
                "FROM booking INNER join booking_has_service " +
                "ON bookingID = booking_bookingID INNER JOIN service ON serviceName = service_serviceName " +
                "ORDER BY BookingID";
        String queryCustomer = "SELECT bookingID, date, bookingDesc, account_accountID, serviceCompleted, " +
                "licenseID, service_ServiceName, serviceCost" +
                ", discount, discountStart, discountEnd, estimatedTime " +
                "FROM booking INNER JOIN booking_has_service " +
                "ON bookingID = booking_bookingID INNER JOIN service ON serviceName" +
                " = service_serviceName WHERE account_accountid = '" + acc.getAccountID() + "' " +
                "ORDER BY BookingID";
        int lastId = -1;

        try {
            stmt = dbConnection.createStatement();
            if (DBC.getInstance().getCurrentAcc().getAccessType() == 1 || DBC.getInstance().getCurrentAcc().getAccessType() == 2) {
                rs = stmt.executeQuery(queryAdmin);
            } else if (DBC.getInstance().getCurrentAcc().getAccessType() == 3){
                rs = stmt.executeQuery(queryCustomer);
            }else{
                rs = null;
            }

            while (rs.next()) {
                if (lastId != -1 && lastId != rs.getInt(1)) {
                    bookings.add(tempBooking);
                    tempServices.clear();
                }
                tempServices.add(new Service(rs.getString(7), rs.getDouble(8), rs.getDouble(9),
                        rs.getTimestamp(10), rs.getTimestamp(11), rs.getInt(12)));
                tempBooking = new Booking(rs.getInt(1), rs.getTimestamp(2), rs.getString(3),
                        rs.getInt(4), rs.getString(6), new ArrayList(tempServices));
                lastId = rs.getInt(1);
            }
            bookings.add(tempBooking);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public ArrayList<Service> getAvailableServices() {
        String query = "SELECT * From Service";
        ArrayList<Service> services = new ArrayList<>();
        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                services.add(new Service(rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getInt(6)));
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return services;
    }

    public Account getCurrentAcc() {
        return acc;
    }

    public void setCurrentAcc(Account acc) {
        this.acc = acc;
    }


    public void saveAccount() {
        try {
            String query = "INSERT INTO Account (email, password, name, phone, loginStatus, Access_AccessID) VALUES (?, ?, ?, ?, ?, ?)";
            statement = dbConnection.prepareStatement(query);
            statement.setString(1, acc.getEmail());
            statement.setString(2, acc.getPassword());
            statement.setString(3, acc.getName());
            statement.setString(4, acc.getPhoneNr());
            statement.setBoolean(5, false);
            statement.setInt(6, 3);
            statement.execute();
            statement.close();
            System.out.println("DEBUG: Sign up successful, saved in remote DB");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //update the information in the DB
    public void updateAccount(String name, String pass, String phone, int accountID) {
        int resultSet;
        String updateQuery = "UPDATE projektkurs2hkr.account set password='" + pass + "', name='" + name + "',phone='" + phone + "'  where accountID='" + accountID + "' ";
        try {
            stmt = dbConnection.createStatement();
            resultSet = stmt.executeUpdate(updateQuery);
            if (resultSet > 0) {
                //System.out.println("gick: "+resultSet);
            } else //System.out.println("gick ej: "+resultSet);
                System.out.println(resultSet);
        } catch (Exception e) {
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

        String queryLogin = "SELECT * FROM Account WHERE email = '" + email + "'";
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
            } else {
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
                rsLogin.close();
                status = statusLogin;

                String part1 = "", part2 = "";
                try {
                    String[] parts = dbpass.split("-");
                    part1 = parts[0];
                    part2 = parts[1];
                } catch (Exception e) {
                    System.out.println("nothing");
                }

                if (dbmail.equals(email) && PasswordEncryption.verifyPassword(pass, part1, part2)) {

                    if (!dbLoginStatus) {
                        status = true;
                        acc = new Account(dbAccID, dbmail, dbpass, dbname, dbphone, accessType);
                        setLoginStatus(true);
                    } else {
                        status = false;
                        System.out.println("DEBUG: User already logged in");
                    }
                } else if (dbmail.matches(email)) {
                    System.out.println("DEBUG: EMAIL MATCH, PW failed");
                } else {
                    System.out.println("debug fuck off");
                }
            }
            stmt.close();
        } catch (Exception ex) {
            System.out.println("ERROR; " + ex.getMessage());
            ex.printStackTrace();
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
            if (!toggle) {
                System.out.println("DEBUG: User logged out");
            } else {
                System.out.println("DEBUG: User logged in");
            }
        } catch (Exception e) {
            System.out.println("ERROR SETLOGINSTATUS: " + e.getMessage());
        }
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

    //felix
    public ArrayList<Account> getAllUsers() {
        ArrayList<Account> allUsers = new ArrayList<>();
        Account tempAcc = null;
        try {
            stmt = dbConnection.createStatement();
            String queryUsers = "SELECT accountID, email, password, name, phone, loginStatus, access_accessID FROM Account";
            ResultSet rsUsers = stmt.executeQuery(queryUsers);
            if (!rsUsers.next()) {
            } else {
                do {
                    tempAcc = new Account(rsUsers.getInt(1), rsUsers.getString(2), rsUsers.getString(3), rsUsers.getString(4),
                            rsUsers.getString(5), rsUsers.getInt(7));
                    allUsers.add(tempAcc);
                } while (rsUsers.next());
            }
            stmt.close();

        } catch (Exception ex) {
            System.out.println("DEBUG: see users");
            ex.printStackTrace();
        }
        return allUsers;
    }

    public boolean deleteUser(int accountID) {
        String query = "DELETE FROM Account WHERE accountID =" + accountID;
        try {
            PreparedStatement prepstmt = dbConnection.prepareStatement(query);
            ;
            prepstmt.executeUpdate();
            prepstmt.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    //Admin user can change the cost of a service.
    public void setServiceCost(String serviceName, double price) {
        String queryPrice = "UPDATE Service SET serviceCost = ? WHERE serviceName = '" + serviceName + "'";
        try {
            PreparedStatement prepstmt = dbConnection.prepareStatement(queryPrice);
            prepstmt.setDouble(1, price);
            prepstmt.executeUpdate();
            prepstmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //This method is not implemented yet, should be used by Admin user
    public void setDiscount(String serviceName, double discount, Timestamp startDate, Timestamp endDate) {
        discount = discount / 100;

        String queryDiscount = "UPDATE Service SET discount = ?, discountStart = ?, discountEnd = ? WHERE serviceName = '" + serviceName + "'";
        try {
            statement = dbConnection.prepareStatement(queryDiscount);
            statement.setDouble(1, discount);
            statement.setTimestamp(2, startDate);
            statement.setTimestamp(3, endDate);
            statement.executeUpdate();
            statement.close();
            System.out.println("DEBUG: " + discount + ", " + startDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkPhoneNumber(String phone) {
        String query = "Select phone from Account where phone= '" + phone + "' ";
        try {
            stmt = dbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next()) {// check if the phone number is in the DB already if it is returns false otherwise true
                return false;
            } else return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setRecoveryCode(int recoveryCode, String email) {
        int accID = 0;
        String queryAccID = "SELECT accountID FROM Account WHERE email = '" + email + "'";

        try {
            stmt = dbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(queryAccID);
            while (resultSet.next()) {
                accID = resultSet.getInt(1);
                System.out.println(accID + "   set recoverycode");
            }
            String query = "UPDATE Account SET recoveryCode= '" + recoveryCode + "' WHERE accountID='" + accID + "'";

            statement = dbConnection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRecoveryCode(String email) {
        String recoveryCode = "";
        String query = "SELECT recoveryCode FROM Account WHERE email = '" + email + "'";

        try {
            stmt = dbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                recoveryCode = resultSet.getString(1);
                System.out.println(recoveryCode + "   get recoverycode");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recoveryCode;
    }

    public void setRecoveryPassword(String hash, String email) {
        int accID = 0;
        String queryAccID = "SELECT accountID FROM Account WHERE email = '" + email + "'";
        try {
            stmt = dbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(queryAccID);
            accID = resultSet.getInt(1);
            System.out.println(accID + "   set recoveryPassword");
            stmt.close();
            resultSet.close();

            String query = "UPDATE Account SET password='" + hash + "' WHERE accountID='" + accID + "'";
            statement = dbConnection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

