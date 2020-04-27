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
    @FXML private MenuItem mbInspection;
    @FXML private MenuItem mbRepair;
    @FXML private MenuItem mbWash;
    @FXML private MenuButton mbService;

    @FXML private CheckBox chbInspectionBasic;
    @FXML private CheckBox chbInspectionAdvanced;

    @FXML private CheckBox chbRepairOil;
    @FXML private CheckBox chbRepairAC;
    @FXML private CheckBox chbRepairWheel;
    @FXML private CheckBox chbRepairTimingBelt;

    @FXML private CheckBox chbWashBasicExt;
    @FXML private CheckBox chbWashPremiumExt;
    @FXML private CheckBox chbWashInterior;
    @FXML private CheckBox chbWashComplete;





    ArrayList<String> subs = new ArrayList<>();
    ArrayList<Double> subsCost = new ArrayList<>();

    int price = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private void handleMbInspection(ActionEvent e){
        mbService.setText(mbInspection.getText());

        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(true);
        toggleWashCheckBoxes(false);
    }
    @FXML private void handleMbRepair(ActionEvent e){
        mbService.setText(mbRepair.getText());

        toggleRepairCheckBoxes(true);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(false);
    }
    @FXML private void handleMbWash (ActionEvent e){
        mbService.setText(mbWash.getText());

        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(true);
    }

    @FXML private void handleButton(){

        if (chbOil.isSelected()){
            price += 50;
            subsCost.add((double) 50);
            subs.add("Oil change");
        }
        if (chbAC.isSelected()){
            price += 100;
            subsCost.add((double) 50);
            subs.add("AC fix");
        }

        //Service serv = new Service(Service.serviceType.valueOf(rbService.getText()), price, subs, subsCost);
        lblA.setText(String.valueOf(price));


        //lblB.setText(serv.getNameOfService() + " + , " + String.valueOf(serv.getCost()) + ", " + serv.getSubCategories());
    }

    private void toggleInspectionCheckBoxes(boolean toggle){
        chbInspectionBasic.setVisible(toggle);
        chbInspectionAdvanced.setVisible(toggle);
    }



    private void toggleRepairCheckBoxes(boolean toggle){
        chbRepairOil.setVisible(toggle);
        chbRepairAC.setVisible(toggle);
        chbRepairWheel.setVisible(toggle);
        chbRepairTimingBelt.setVisible(toggle);
    }

    private void toggleWashCheckBoxes(boolean toggle){
        chbWashBasicExt.setVisible(toggle);
        chbWashPremiumExt.setVisible(toggle);
        chbWashInterior.setVisible(toggle);
        chbWashComplete.setVisible(toggle);
    }



}

