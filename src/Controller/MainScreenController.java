package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Booking;
import sample.DBC;
import sample.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML private Button btnContact;
    @FXML private Button btnAdmin;
    @FXML private AnchorPane anchorInfo;
    @FXML private Label lblSignedInInfo;

    private SceneSwitcher sw = new SceneSwitcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSignedInInfo.setText("Signed in as: " + DBC.getInstance().getCurrentAcc().getEmail());
        checkIfAdmin();
        try {
            sw.mainScreenSceneSwitcher(anchorInfo, "CreateBooking");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkIfAdmin(){
        if (DBC.getInstance().getCurrentAcc().getAccessType() == 1){
            btnAdmin.setVisible(true);
            btnContact.setVisible(false);
        } else if (DBC.getInstance().getCurrentAcc().getAccessType() == 2){
            btnAdmin.setVisible(true);
            btnContact.setVisible(false);
            btnAdmin.setText("Employee Menu");
        }

        else{
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
    }
    @FXML
    private void handleManageBookingsBtn(ActionEvent e) throws IOException {
        sw.mainScreenSceneSwitcher( anchorInfo, "ManageBooking");
    }
    @FXML
    private void handleAccountBtn(ActionEvent e) throws IOException{
        sw.mainScreenSceneSwitcher( anchorInfo, "ManageAccount");
    }
    @FXML
    private void handleNearestStationBtn(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/FindNearestStation.fxml"));
        Parent root1 = fxmlLoader.load();
        FindNearestStationController controller = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Find Us");
        stage.setScene(new Scene(root1));
        stage.show();
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
