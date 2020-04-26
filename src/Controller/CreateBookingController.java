package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Service;
import sample.Verification;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateBookingController implements Initializable {

    @FXML private CheckBox chbOil;
    @FXML private CheckBox chbAC;
    @FXML private Label lblA;
    @FXML private Label lblB;
    @FXML private RadioButton rbService;
    @FXML private RadioButton rbInspection;
    @FXML private RadioButton rbWash;
    @FXML private CheckBox chbBasic;
    @FXML private CheckBox chbAdvanced;


    ArrayList<String> subs = new ArrayList<>();

    int price = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private void handleButton(){

        if (chbOil.isSelected()){
            price += 50;
            subs.add("Oil change");
        }
        if (chbAC.isSelected()){
            price += 100;
            subs.add("AC fix");
        }

        Service serv = new Service(Service.serviceType.valueOf(rbService.getText()), price, subs);
        lblA.setText(String.valueOf(price));


        lblB.setText(serv.getNameOfService() + " + , " + String.valueOf(serv.getCost()) + ", " + serv.getSubCategories());
    }

    @FXML private void handleRbService(){
        chbOil.setVisible(true);
        chbAC.setVisible(true);
        chbBasic.setVisible(false);
        chbAdvanced.setVisible(false);
    }

    @FXML private void handleRbInspection(){
        chbOil.setVisible(false);
        chbAC.setVisible(false);

        chbBasic.setVisible(true);
        chbAdvanced.setVisible(true);
    }




}

