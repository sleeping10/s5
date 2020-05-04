package sample;

import java.util.ArrayList;
import java.util.Date;

public class Booking {
    private int bookingID;
    private Date date;
    private String bookingDesc;
    private int accountID;
    private String licensePlate;
    private ArrayList<String> services;

    public int getBookingID() {
        return bookingID;
    }

    public Date getDate() {
        return date;
    }

    public String getBookingDesc() {
        return bookingDesc;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getLicensePlate(){return licensePlate;}

    public ArrayList<String> getServices(){
        return this.services;
    }

    public Booking(int bookingID, Date date, String bookingDesc, int accountID, String licensePlate, ArrayList<String> services) {
        this.bookingID = bookingID;
        this.date = date;
        this.bookingDesc = bookingDesc;
        this.accountID = accountID;
        this.licensePlate = licensePlate;
        this.services = services;
    }


}
