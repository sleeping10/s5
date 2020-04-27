package sample;

import java.util.ArrayList;
import java.util.Date;

public class Booking {
    private int bookingID;
    private Date date;
    private String bookingDesc;
    private int accountID;


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

    public Booking(int bookingID, Date date, String bookingDesc, int accountID, ArrayList<String> subCategories) {
        this.bookingID = bookingID;
        this.date = date;
        this.bookingDesc = bookingDesc;
        this.accountID = accountID;
    }


}
