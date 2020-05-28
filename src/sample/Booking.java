package sample;

import java.util.ArrayList;
import java.util.Date;

public class Booking {
    private int bookingID;
    private Date date;
    private String bookingDesc;
    private int accountID;
    private String licensePlate;
    private ArrayList<Service> services;
    private boolean serviceCompleted;

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

    public ArrayList<Service> getServices(){
        return this.services;
    }

    public Booking(int bookingID, Date date, String bookingDesc, int accountID, String licensePlate, ArrayList<Service> services,
                   boolean serviceCompleted) {
        this.bookingID = bookingID;
        this.date = date;
        this.bookingDesc = bookingDesc;
        this.accountID = accountID;
        this.licensePlate = licensePlate;
        this.services = services;
        this.serviceCompleted = serviceCompleted;
    }
    public void setBookingDesc(String newDesc){
        this.bookingDesc = newDesc;
    }
    public void setDate(Date newDate){
        this.date = newDate;
    }
    public boolean getServiceCompleted(){return this.serviceCompleted;}
    public void setServiceCompleted(boolean serviceCompleted){this.serviceCompleted = serviceCompleted;}


}
