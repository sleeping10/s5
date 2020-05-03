package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML private TableView<Booking> twBookings;
    @FXML private TextField tfPhone;
    @FXML private TextField tfBookingID;
    @FXML private TableColumn tcBookingID;
    @FXML private TableColumn tcStartDate;
    @FXML private TableColumn tcEndDate;
    @FXML private TableColumn tcBookingDesc;
    @FXML private TableView twField;
    @FXML private Button buttonFilter;
    private ArrayList<Booking> list = DBC.getInstance().getBooking();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcBookingID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("bookingID"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcBookingDesc.setCellValueFactory(new PropertyValueFactory<String, Booking>("bookingDesc"));
        for (int i = 0; i <list.size() ; i++) {
            twBookings.getItems().add(list.get(i));
        }


    }


    public void filterButtonPressed(ActionEvent actionEvent) {
        twBookings.getItems().clear();
        if (!tfBookingID.getText().isEmpty()) {
            for (Booking b : list) {
                if (tfBookingID.getText().matches(String.valueOf(b.getBookingID()))) {
                    twBookings.getItems().add(b);
                }
            }
        } else if (!tfPhone.getText().isEmpty()){
            for(Booking b : list){
            if(tfPhone.getText().matches( DBC.getInstance().swag(b.getAccountID()))){
            twBookings.getItems().add(b);
            }
        }
        }

    }

}
