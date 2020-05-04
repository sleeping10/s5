package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Account;
import sample.Booking;
import sample.DBC;
import sample.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailedBookingViewController implements Initializable {
    @FXML private Button buttonRemoveBooking;
    @FXML private Button buttonSaveBooking;
    @FXML private Button buttonBack;
    @FXML private TextField tfBookingID;
    @FXML private TextField tfName;
    @FXML private TextField tfPhone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfTotalCost;
    @FXML private DatePicker datePicker;
    @FXML private TextArea taServices;
    private Account customerAcc = DBC.getInstance().getAccount();
    private Booking booking;

public DetailedBookingViewController(Booking booking){
    this.booking = booking;
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(customerAcc.getAccessType() == 3){
            tfName.setText(customerAcc.getName());
            tfPhone.setText(customerAcc.getPhone());
            tfEmail.setText(customerAcc.getEmail());
        }

    }


    public void handleButtonBackPressed(ActionEvent actionEvent) {


    }

    public void handleButtonSavePressed(ActionEvent actionEvent) {
    }

    public void handleButtonRemoveBookingPressed(ActionEvent actionEvent) {
    }
}
