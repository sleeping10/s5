package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Account;
import sample.Booking;
import sample.DBC;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {
    Account acc = DBC.getInstance().getAccount();
    @FXML private TableView<ArrayList<Booking>> twBookings;
    @FXML private TextField tfPhone;
    @FXML private TextField tfBookingID;
    @FXML private TableColumn tcBookingID;
    @FXML private TableColumn tcStartDate;
    @FXML private TableColumn tcEndDate;
    @FXML private TableColumn tcBookingDesc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcBookingID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("bookingID"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcBookingDesc.setCellValueFactory(new PropertyValueFactory<String, Booking>("bookingDesc"));
        if(acc.getAccessType()==3){
//          twBookings.getItems().add(DBC.getInstance().getBooking());


        } else {
//            DBC.getInstance().getBooking();
//            twBookings.getItems().add(DBC.getInstance().getBooking());



        }
    }

    private void hello(ActionEvent e){

    }

}
