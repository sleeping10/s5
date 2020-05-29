package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;

import javax.swing.*;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetailedBookingViewController implements Initializable {
    @FXML private TextField tfName;
    @FXML private TextField tfPhone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfBookingID;
    @FXML private TextField tfTotalCost;
    @FXML private TableColumn tcServices;
    @FXML private TableColumn tcPrice;
    @FXML private TableView<Service> tvServices;
    @FXML private DatePicker dp;
    @FXML private TextArea taDesc;
    @FXML private CheckBox chbCompleted;
    private Booking selectedBooking;
    private Account acc = null;


    // hämtar booking från förra sidan
    public void initBooking(Booking booking){
        selectedBooking = booking;
        tcServices.setCellValueFactory(new PropertyValueFactory<String, Service>("serviceName"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<Double, Service>("cost"));
        tvServices.setItems(view());

        acc =DBC.getInstance().getAccount(selectedBooking.getAccountID());

        tfName.setText(acc.getName());
        tfPhone.setText(acc.getPhoneNr());
        tfEmail.setText(acc.getEmail());
        chbCompleted.setSelected(selectedBooking.getServiceCompleted());

        if (DBC.getInstance().getCurrentAcc().getAccessType() == 3){
            chbCompleted.setDisable(true);
        }

        ArrayList<Service> listicle = booking.getServices();
        double cost = 0;
        for (int i = 0; i <listicle.size() ; i++) {
            cost+= listicle.get(i).getCurrentCost();
        }
        cost = Math.round(cost);
        tfTotalCost.setText(String.valueOf(cost));
        tfBookingID.setText(String.valueOf(selectedBooking.getBookingID()));
        Instant instant = Instant.ofEpochMilli(selectedBooking.getDate().getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate ld = ldt.toLocalDate();
        dp.setValue(ld);
        taDesc.setText(selectedBooking.getBookingDesc());

        }

    private ObservableList<Service> view (){
        Service service;
        ArrayList<Service> list = new ArrayList<>();
        ObservableList<Service> views = FXCollections.observableArrayList();
        for (int i = 0; i < selectedBooking.getServices().size(); i++) {
            service = new Service(selectedBooking.getServices().get(i).getServiceName(), selectedBooking.getServices().get(i).getCost(),
                    selectedBooking.getServices().get(i).getDiscount(), selectedBooking.getServices().get(i).getDiscountStart(),
                    selectedBooking.getServices().get(i).getDiscountEnd(), selectedBooking.getServices().get(i).getEstimatedTime());
            list.add(service);
        }
        for (int i = 0; i <list.size() ; i++) {
            views.add(list.get(i));
        }
        return views;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setEditable(true);
        tfPhone.setEditable(false);
        tfEmail.setEditable(false);
        tfBookingID.setEditable(false);
        tfTotalCost.setEditable(false);

    }

    public void handleButtonSavePressed(ActionEvent actionEvent) {
        java.util.Date date;
        selectedBooking.setBookingDesc(taDesc.getText());
        java.sql.Date dpDate = java.sql.Date.valueOf(dp.getValue());
        date = new java.util.Date(dpDate.getTime());
        selectedBooking.setDate(date);

        if (chbCompleted.isSelected()) {
            selectedBooking.setServiceCompleted(true);
        }else{
            selectedBooking.setServiceCompleted(false);
        }
        DBC.getInstance().updateBooking(selectedBooking);
    }

    public void handleButtonRemoveBookingPressed(ActionEvent actionEvent) {
        DBC.getInstance().removeBooking(selectedBooking);

    }
}
