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
    private final String database_url = "jdbc:mysql://den1.mysql4.gear.host:3306/projektkurs2hkr?user=projektkurs2hkr&password=Vt5YI?qGI~dj&useSSL=false";

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

            System.out.println("DEBUG total booking: "+ totalBookings);

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
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void removeBooking(Booking booking){
        String query = "DELETE FROM Booking,Booking_has_service USING Booking INNER JOIN Booking_has_service WHERE bookingID = '"+booking.getBookingID()+"' AND Booking_bookingID = bookingID";
        try {
            statement = dbConnection.prepareStatement(query);
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
    public Account getCompleteAccount(int accountID) {
        Account acc = null;
        int id = 0;
        String email = "";
        String password = "";
        String name = "";
        String phone = "";
        boolean status = false;
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
                status = rsDetailedAcc.getBoolean(6);
                access = rsDetailedAcc.getInt(7);
            }
            acc = new Account(id, email, password, name, phone, status, access);
            rsDetailedAcc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return acc;
    }

    // used in detailed booking view to change the date and description of booking
    public void updateBooking(Booking booking){
        java.util.Date utilDate = booking.getDate();
        java.sql.Timestamp sq = new java.sql.Timestamp(utilDate.getTime());
        System.out.println(sq);
        String query = "UPDATE Booking SET bookingDesc = '" + booking.getBookingDesc()+"', date = '"+sq+"' WHERE bookingID = "+booking.getBookingID();
        try {
            statement = dbConnection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    //public Booking getBookingDetails(){}


    //Gets all the bookings from the Database
    public ArrayList<Booking> getBooking() {
        String queryAdmin = "SELECT * From Booking";
        String queryCustomer = "SELECT * From Booking where Account_accountID = '" + acc.getAccountID() + "'";
        String queryBookingHasService = "SELECT * From Booking_has_service";
        String queryService = "SELECT * from Service";
        ArrayList<Booking> bookings = new ArrayList<>();
        ArrayList<Service> services = new ArrayList<>();
        ArrayList<String> servicesString = new ArrayList<>();
        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<Service> tempArray = new ArrayList<>();
        int totalBookingHasServices = 0;
        int selectedBookingID = 0;
        int lastId = -1;
        ArrayList<Integer> bookingHasService = new ArrayList<>();
        try {


                String query = "SELECT * FROM Booking INNER JOIN booking_has_service on bookingID = booking_bookingID " +
                        "INNER JOIN service on Service_serviceName = serviceName";

                String querytest = "select * " +
                        "from booking_has_service " +
                        "INNER JOIN service " +
                        "on service_serviceName = serviceName; ";

                String querytest2 = "select bookingID, date, bookingDesc, account_accountID, serviceCompleted, licenseID, service_ServiceName, serviceCost" +
                        ", discount, discountStart, discountEnd, estimatedTime " +
                        "from booking INNER join booking_has_service " +
                        "on bookingID = booking_bookingID inner join service on serviceName = service_serviceName";

                String querytest3 = "select bookingID, date, bookingDesc, account_accountID, serviceCompleted, licenseID, service_ServiceName, serviceCost" +
                        ", discount, discountStart, discountEnd, estimatedTime " +
                        "from booking INNER join booking_has_service " +
                        "on bookingID = booking_bookingID inner join service on serviceName = service_serviceName WHERE account_accountid = '" + acc.getAccountID() + "'";

                String query2 = "SELECT * FROM Booking";
                String countBookingHasService = "SELECT COUNT(bookingID) FROM Booking";
                int total = 0;
                if (DBC.getInstance().getAccount().getAccessType() == 1 || DBC.getInstance().getAccount().getAccessType() == 2) {
                    stmt = dbConnection.createStatement();

                    ResultSet rs3 = stmt.executeQuery(querytest2);
                    lastId = -1;

                    while (rs3.next()) {

                        if (lastId != -1 && lastId != rs3.getInt(1)) {
                            //System.out.println("last id: " + lastId);
                            if (rs3.getInt(1) != 1) {
                                bookings.add(new Booking(lastId, rs3.getDate(2), rs3.getString(3), rs3.getInt(4), rs3.getString(6), new ArrayList(tempArray)));
                            } else if (rs3.getInt(1) == 1) {
                                bookings.add(new Booking(rs3.getInt(1), rs3.getDate(2), rs3.getString(3), rs3.getInt(4), rs3.getString(6), new ArrayList(tempArray)));
                            }
                            tempArray.clear();

                        } else if (lastId == -1 || lastId == rs3.getInt(1)) {
                            tempArray.add(new Service(rs3.getString(7), rs3.getDouble(8), rs3.getDouble(9),
                                    rs3.getTimestamp(10), rs3.getTimestamp(11), rs3.getInt(12)));

                        } else if(rs3.isLast()){
                            bookings.add(new Booking(lastId, rs3.getDate(2), "", 5, "FEG123", (ArrayList) tempArray.clone()));
                        }

                        lastId = rs3.getInt(1);

                    }
                    stmt.close();
                    //Add last booking to list

                }else{
                            stmt = dbConnection.createStatement();
                            lastId = -1;
                            System.out.println("DEBUG: entered service getter");

                            try {
                                ResultSet rs4 = stmt.executeQuery(querytest3);
                                while (rs4.next()) {


                                    // -1 Först gör denna
                                    if (lastId == -1){
                                        System.out.println("Creating first service to first booking, " + rs4.getString(7));
                                        tempArray.add(new Service(rs4.getString(7), rs4.getDouble(8), rs4.getDouble(9),
                                                rs4.getTimestamp(10), rs4.getTimestamp(11), rs4.getInt(12)));
                                        Booking tempbooking = new Booking(rs4.getInt(1), rs4.getDate(2), rs4.getString(3), rs4.getInt(4), rs4.getString(6), new ArrayList(tempArray));
                                        bookings.add(tempbooking);
                                        tempArray.clear();
                                        System.out.println(tempbooking.getBookingID());
                                    }


                                    else {
                                        System.out.println("DEBUG: Adding service to temparray, " + rs4.getString(7));
                                        tempArray.add(new Service(rs4.getString(7), rs4.getDouble(8), rs4.getDouble(9),
                                                rs4.getTimestamp(10), rs4.getTimestamp(11), rs4.getInt(12)));
                                        System.out.println("SERVICES TO BE ADDED: ");
                                        for (int i = 0; i < tempArray.size(); i++){

                                            System.out.print(tempArray.get(i).getServiceName() + ", ");
                                            System.out.println();
                                        }

                                    }


                                    // efter första steg, la = 1 gör, denna 2
                                   if (lastId != -1 && lastId == rs4.getInt(1) && !rs4.isLast()) {
                                        System.out.println("last id: " + lastId);
                                        System.out.println("rs4 id: " + rs4.getInt(1));
                                        Booking tempbooking = new Booking(rs4.getInt(1), rs4.getDate(2), rs4.getString(3), rs4.getInt(4), rs4.getString(6), new ArrayList(tempArray));
                                            bookings.add(tempbooking);
                                            tempArray.clear();
                                        System.out.println(tempbooking.getBookingID());

                                        }

                                    lastId = rs4.getInt(1);
                                    System.out.println("DEBUG: new ID/last ---> " + lastId);
                                    }

                                stmt.close();

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            //Add last booking to list
                        }


                }catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }


//
//
//
//
//
//
//                stmt = dbConnection.createStatement();
//
//                ResultSet rsCount = stmt.executeQuery(countBookingHasService);
//
//                while(rsCount.next()){
//                    totalBookingHasServices = rsCount.getInt(1);
//                }
//
//                System.out.println("DEBUG: total bookign has service: " + totalBookingHasServices);
//
//                ResultSet rsCust = stmt.executeQuery(queryCustomer);
//
//                while (rsCust.next()) {
//                    bookingIds.add(rsCust.getInt(1));
//                    selectedBookingID = rsCust.getInt(1);
//                }
//
//                System.out.println("DEBUG: Selected Booking: " + selectedBookingID);
//
//                ResultSet rsBookingHasService = stmt.executeQuery(queryBookingHasService);
//
//                while (rsBookingHasService.next()){
//                    for (int i = 0; i < bookingIds.size(); i++){
//                        if (rsBookingHasService.getInt(1) == bookingIds.get(i)){
//                            servicesString.add(rsBookingHasService.getString(2));
//                            bookingHasService.add(rsBookingHasService.getInt(1));
//                        }
//                    }
//                }
//                rsBookingHasService.close();
//
//                ResultSet rsService = stmt.executeQuery(queryService);
//
//                while(rsService.next()){
//                    for (int i = 0; i < bookingIds.size(); i++){
//                        if (servicesString.get(i).equals(rsService.getString(1)) && bookingHasService.get(i) == bookingIds.get(i)){
//                            services.add(new Service(
//                                    rsService.getString(1),
//                                    rsService.getDouble(2),
//                                    rsService.getDouble(3),
//                                    rsService.getTimestamp(4),
//                                    rsService.getTimestamp(5),
//                                    rsService.getInt(6)));
//                            break;
//                        }
//                    }
//                }
//                rsService.close();
//
//                ResultSet rs = stmt.executeQuery(queryCustomer);
//
//                while (rs.next()){
//                    for(int i = 0; i < bookingIds.size(); i++){
//                        if (rs.getInt(1 ) == bookingIds.get(i)){
//                            bookings.add(new Booking(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getString(6), services));
//                        }
//                    }
//                }
//                rs.close();

//                stmt = dbConnection.createStatement();
//                ResultSet rsService = stmt.executeQuery(queryService);
//
//                while (rsService.next()) {
//                    bookingIds.add(rsService.getInt(1));
//                }
//                rsService.close();
//
//                ResultSet resultSet = stmt.executeQuery(queryAdmin);
//
//                while (resultSet.next()) {
//                    bookings.add(new Booking(resultSet.getInt(1), resultSet.getDate(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(6), services));
//                }
//                resultSet.close();


    // removeBooking
    //public void removeBooking(Booking booking) {
//
  //  }


    public ArrayList<Service> getAvailableServices() {
        String query = "SELECT * From Service";
        ArrayList<Service> services = new ArrayList<>();
        try {
                stmt = dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                            services.add(new Service(rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getInt(6)));
                        }
                rs.close();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return services;
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
    public void updateAccount(String name,String pass,String phone,int accountID) {
        int resultSet;
        String updateQuery = "UPDATE projektkurs2hkr.account set password='"+pass+"', name='"+name+"',phone='"+phone+"'  where accountID='"+accountID+"' ";
        try {
            stmt=dbConnection.createStatement();
            resultSet = stmt.executeUpdate(updateQuery);
            if (resultSet>0){
                //System.out.println("gick: "+resultSet);
            }else //System.out.println("gick ej: "+resultSet);
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
                }catch (Exception e){
                    System.out.println("nothing");
                }

                if (dbmail.equals(email) && PasswordEncryption.verifyPassword(pass, part1, part2)) {

                    if (!dbLoginStatus){
                        status = true;
                        acc = new Account(dbAccID, dbmail, dbpass, dbname, dbphone, true, accessType);
                        setLoginStatus(true);
                    }else{
                        status = false;
                        System.out.println("DEBUG: User already logged in");
                    }
                }else if (dbmail.matches(email)){
                    System.out.println("DEBUG: EMAIL MATCH, PW failed");
                }else{
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
            if (!toggle){
                System.out.println("DEBUG: User logged out");
            }else{
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

    public String getPhoneFilter(int accID){
        String queryPhone = "SELECT phone FROM Account WHERE accountID = '" + accID + "'";
        String phone = "";
        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(queryPhone);
            if(rs.next()) {
                phone = rs.getString(1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return phone;
    }

    //This method is not implemented yet, should be used by Admin user
    public ArrayList<Account> getAllUsers(){
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
                    allUsers.add(tempAcc);
                } while (rsUsers.next());
            }
            stmt.close();

        }catch(Exception ex){
            System.out.println("DEBUG: see users");
            ex.printStackTrace();
        }
        return allUsers;
    }

    public boolean deleteUser(int accountID){
        String query = "DELETE FROM Account WHERE accountID =" + accountID;
        try {
            PreparedStatement prepstmt = dbConnection.prepareStatement(query);;
            prepstmt.executeUpdate();
            prepstmt.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    //Admin user can change the cost of a service.
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

    //This method is not implemented yet, should be used by Admin user
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

    public boolean checkPhoneNumber(String phone){
        String query="Select phone from Account where phone= '"+phone+"' ";
        try {
            stmt=dbConnection.createStatement();
            ResultSet resultSet=stmt.executeQuery(query);

            if (resultSet.next()){// check if the phone number is in the DB already if it is returns false otherwise true
                return false;
            }else return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

