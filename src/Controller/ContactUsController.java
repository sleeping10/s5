package Controller;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

//This class is not done/implemented yet
public class ContactUsController implements Initializable {
    @FXML private Label labelAddress;
    @FXML private Label labelEmail;
    @FXML private Label labelPhone;
    @FXML private Label labelHours;
    @FXML private Hyperlink hlWebsite;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hlWebsite.setText("Visit our website");
        labelAddress.setText("Elmetorpsvägen 15, 291 39 Kristianstad");
        labelEmail.setText("projektkurs2HKR@hotmail.com");
        labelHours.setText("Open 8:00 - 20:00 Every day");
        labelPhone.setText("Phone: 133769420");

    }


    public void linkPressed(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://hkr.se").toURI());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
