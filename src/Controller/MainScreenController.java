package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.SceneSwitcher;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML private Button btnCreateBooking;
    @FXML private Button btnManageBookings;
    @FXML private Button btnManageAccount;
    @FXML private Button btnNearestStation;
    @FXML private Button btnContactUs;
    @FXML private AnchorPane anchorInfo;
    @FXML private GridPane gridpaneMenu;
    @FXML private Label LblSignout;
    private Class MainScreenController;

    SceneSwitcher sw = new SceneSwitcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //hboxContainer.setBackground();


    }


    @FXML
    private void handleCreateBookingBtn(ActionEvent e) throws IOException {

        sw.mainScreenSceneSwitcher( anchorInfo, "CreateBooking");


        //sceneSwitcher("CreateBooking");
        //btnCreateBooking.setStyle("-fx-background-color: #ff0000;");
    }
    @FXML
    private void handleManageBookingsBtn(ActionEvent e) throws IOException {
        sw.mainScreenSceneSwitcher( anchorInfo, "ManageBooking");
        //sceneSwitcher("ManageBooking");
    }
    @FXML
    private void handleAccountBtn(ActionEvent e) throws IOException{
        sw.mainScreenSceneSwitcher( anchorInfo, "ManageAccount");
    }

    private void handleNearestStationBtn(ActionEvent e){

    }

    private void handleContactUsBtn(ActionEvent e){

    }

    @FXML
    private void handleLblSignout(ActionEvent e){
        sw.loginSignupSceneSwitcher(e, "Login");
    }

}
