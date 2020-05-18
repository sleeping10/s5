package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailedBookingViewController implements Initializable {
    @FXML private TextField tfName;
    @FXML private TextField tfPhone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfBookingID;
    @FXML private TextField tfTotalCost;
    @FXML private ListView<Service> lvServices;
    private Booking selectedBooking;
    private Account acc = null;


    // hämtar booking från förra sidan
    public void initBooking(Booking booking){
        selectedBooking = booking;
        acc =DBC.getInstance().getCompleteAccount(selectedBooking.getAccountID());

        tfName.setText(acc.getName());
        tfPhone.setText(acc.getPhoneNr());
        tfEmail.setText(acc.getEmail());
        tfBookingID.setText(String.valueOf(booking.getBookingID()));
//        tfTotalCost.setText(selectedBooking.get);
//        lvServices.setItems();

          System.out.println(booking.getBookingDesc() +"       " + acc.getEmail());
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
    }
}
