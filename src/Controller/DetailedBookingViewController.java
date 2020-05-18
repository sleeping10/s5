package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.ls.LSOutput;
import sample.*;

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
    private Booking selectedBooking;
    private Account acc = null;


    // hämtar booking från förra sidan
    public void initBooking(Booking booking){
        selectedBooking = booking;
        System.out.println("init booking 1");
        tcServices.setCellValueFactory(new PropertyValueFactory<String, Service>("serviceName"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<Double, Service>("cost"));
        System.out.println("init booking 2");
        tvServices.setItems(view());

        acc =DBC.getInstance().getCompleteAccount(selectedBooking.getAccountID());

        tfName.setText(acc.getName());
        tfPhone.setText(acc.getPhoneNr());
        tfEmail.setText(acc.getEmail());
        tfBookingID.setText(String.valueOf(selectedBooking.getBookingID()));
        Instant instant = Instant.ofEpochMilli(selectedBooking.getDate().getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate ld = ldt.toLocalDate();
        dp.setValue(ld);
        System.out.println("init booking 3");

        }

    private ObservableList<Service> view (){
        Service service;
        System.out.println("view 1");
        ArrayList<Service> list = new ArrayList<>();
        ObservableList<Service> views = FXCollections.observableArrayList();
        System.out.println("view 2");
        for (int i = 0; i < selectedBooking.getServices().size(); i++) {
            service = new Service(selectedBooking.getServices().get(i).getserviceName(), selectedBooking.getServices().get(i).getCost(),
                    selectedBooking.getServices().get(i).getDiscount(), selectedBooking.getServices().get(i).getDiscountStart(),
                    selectedBooking.getServices().get(i).getDiscountEnd(), Integer.parseInt(selectedBooking.getServices().get(i).getEstimatedTime()));
            list.add(service);
        }
        System.out.println("view 3");
        for (int i = 0; i <list.size() ; i++) {
            views.add(list.get(i));
        }
        return views;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setEditable(false);
        tfPhone.setEditable(false);
        tfEmail.setEditable(false);
        tfBookingID.setEditable(false);
        tfTotalCost.setEditable(false);

    }


    public void handleButtonBackPressed(ActionEvent actionEvent) {
        SceneSwitcher ss = new SceneSwitcher();
        ss.loginSignupSceneSwitcher(actionEvent,"ManageBooking");


    }

    public void handleButtonSavePressed(ActionEvent actionEvent) {

        DBC.getInstance().updateBooking(selectedBooking);
    }

    public void handleButtonRemoveBookingPressed(ActionEvent actionEvent) {
        DBC.getInstance().removeBooking(selectedBooking);
    }
}
