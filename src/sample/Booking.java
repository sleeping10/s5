package sample;

import java.util.Date;

public class Booking {
    private int bookingID;
    private Date date;
    private String bookingDesc;
    public bookingType bookingType;
    private int accountID;

    enum bookingType {
        Inspection, Service, Repair, Wash
    }

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

    public Booking(int bookingID, Date date, String bookingDesc, bookingType bookingType, int accountID) {
        this.bookingID = bookingID;
        this.date = date;
        this.bookingDesc = bookingDesc;
        this.bookingType = bookingType;
        this.accountID = accountID;
    }


}
