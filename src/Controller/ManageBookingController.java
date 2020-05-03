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
    @FXML private TableView<Booking> tvBookings;
    @FXML private TextField tfPhone;
    @FXML private TextField tfBookingID;
    @FXML private TableColumn tcBookingID;
    @FXML private TableColumn tcStartDate;
    @FXML private TableColumn tcEndDate;
    @FXML private TableColumn tcBookingDesc;
    @FXML private TableView tvField;
    @FXML private Button buttonFilter;
    private ArrayList<Booking> list = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = DBC.getInstance().getBooking();
        if(acc.getAccessType()==3) {
            tfPhone.setVisible(false);
            tfBookingID.setVisible(false);
            buttonFilter.setVisible(false);
        }

        tcBookingID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("bookingID"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcEndDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcBookingDesc.setCellValueFactory(new PropertyValueFactory<String, Booking>("bookingDesc"));

        for (int i = 0; i <list.size() ; i++) {
            tvBookings.getItems().add(list.get(i));
        }


    }



    public void filterButtonPressed(ActionEvent actionEvent) {
        tvBookings.getItems().clear();
        if (!tfBookingID.getText().isEmpty()) {
            for (Booking b : list) {
                if (tfBookingID.getText().matches(String.valueOf(b.getBookingID()))) {
                    tvBookings.getItems().add(b);
                }
            }
        } else if (!tfPhone.getText().isEmpty()){
            for(Booking b : list){
            if(tfPhone.getText().matches( DBC.getInstance().swag(b.getAccountID()))){
            tvBookings.getItems().add(b);
            }
        }
        }

    }

}
