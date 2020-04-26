package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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

    PaneNavigator pn = new PaneNavigator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //hboxContainer.setBackground();
    }

    @FXML
    private void handleCreateBookingBtn(ActionEvent e) throws IOException {
        sceneSwitcher("CreateBooking");
    }
    @FXML
    private void handleManageBookingsBtn(ActionEvent e) throws IOException {
        sceneSwitcher("ManageBooking");
    }

    private void handleManageAccountBtn(ActionEvent e){

    }

    private void handleNearestStationBtn(ActionEvent e){

    }

    private void handleContactUsBtn(ActionEvent e){

    }

    public void setPane(Node node) {
        //mainPane.getChildren().setAll(node);
    }


    private void sceneSwitcher(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/" + fxml + ".fxml"));
        Pane newPane = fxmlLoader.load();
        try {
            anchorInfo.getChildren().clear();
            anchorInfo.getChildren().add(newPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
