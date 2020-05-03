package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    @FXML private TableView<Booking> tvBookings = new TableView<>();
    @FXML private TextField tfPhone;
    @FXML private TextField tfBookingID;
    @FXML private TableColumn tcBookingID;
    @FXML private TableColumn tcStartDate;
    @FXML private TableColumn tcEndDate;
    @FXML private TableColumn tcBookingDesc;
    @FXML private TableView tvField;
    @FXML private Button buttonFilter;
    private ArrayList<Booking> list = new ArrayList<>();


    // för table view kanske flytta?
    public ObservableList<Booking> view (){
        ObservableList<Booking> views = FXCollections.observableArrayList();
        list=DBC.getInstance().getBooking();
        for (int i = 0; i <list.size() ; i++) {
            views.add(list.get(i));
        }
        return views;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(acc.getAccessType()==3) {
            tfPhone.setVisible(false);
            tfBookingID.setVisible(false);
            buttonFilter.setVisible(false);
        }

        tcBookingID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("bookingID"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcEndDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcBookingDesc.setCellValueFactory(new PropertyValueFactory<String, Booking>("bookingDesc"));
        tvField.setItems(view());
        if(list.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                tvBookings.getItems().add(list.get(i));
            }
        } else {
            System.out.println("list size 0");
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
