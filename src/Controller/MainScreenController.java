package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.Account;
import sample.DBC;
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
    @FXML private Button btnContact;
    @FXML private Button btnAdmin;
    @FXML private AnchorPane anchorInfo;
    @FXML private GridPane gridpaneMenu;
    @FXML private Label LblSignout;
    @FXML private Label lblSignedInInfo;
    //private Class MainScreenController;

    SceneSwitcher sw = new SceneSwitcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSignedInInfo.setText("Signed in as " + DBC.getInstance().getAccount().getEmail());
        checkIfAdmin();
    }

    private void checkIfAdmin(){
        if (DBC.getInstance().getAccount().getAccessType() == 1){
            btnAdmin.setVisible(true);
            btnContact.setVisible(false);
        }else{
            btnAdmin.setVisible(false);
        }
    }

    @FXML
    private void handleAdminBtn(ActionEvent e) throws IOException{
        sw.mainScreenSceneSwitcher(anchorInfo, "AdminMenu");
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
    @FXML
    private void handleNearestStationBtn(ActionEvent e) throws IOException {
        sw.mainScreenSceneSwitcher( anchorInfo, "FindNearestStation");
    }
    @FXML
    private void handleContactUsBtn(ActionEvent e) throws IOException {
        sw.mainScreenSceneSwitcher( anchorInfo, "ContactUs");
    }

    @FXML
    private void handleLinkSignout(ActionEvent e){
        DBC.getInstance().setLoginStatus(false);
        sw.loginSignupSceneSwitcher(e, "Login");
    }

}
