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
    @FXML private TextField tfPhone;
    @FXML private TextField tfBookingID;
    @FXML private TableColumn tcBookingID;
    @FXML private TableColumn tcStartDate;
    @FXML private TableColumn tcAccID;
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
            tcAccID.setVisible(false);
        }

        tcBookingID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("bookingID"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcAccID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("accountID"));
        tcBookingDesc.setCellValueFactory(new PropertyValueFactory<String, Booking>("bookingDesc"));
        tvField.setItems(view());



    }



    public void filterButtonPressed(ActionEvent actionEvent) {
       tvField.getItems().clear();
        if (!tfBookingID.getText().isEmpty()) {
            for (Booking b : list) {
                if (tfBookingID.getText().matches(String.valueOf(b.getBookingID()))) {
                    tvField.getItems().add(b);
                }
            }
        } else if (!tfPhone.getText().isEmpty()){
            for(Booking b : list) {
                if (tfPhone.getText().matches(DBC.getInstance().getPhoneFilter(b.getAccountID()))) {
                    tvField.getItems().add(b);
                }
            }

        } else if (tfBookingID.getText().isEmpty() && tfPhone.getText().isEmpty()){
            tvField.setItems(view());
        }

    }

    public void handleButtonManagePressed(ActionEvent actionEvent) {
        //open new scene
        // ObservableList<Booking> allList, selectedList;
        //        allList=tvField.getItems();
        //        selectedList = tvField.getSelectionModel().getSelectedItems();
    }

    public void handleButtonCancelAppointmentPressed(ActionEvent actionEvent) {
        //Delete selected row
    }
}
