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
import sample.Booking;
import sample.DBC;
import sample.Service;
import sample.Verification;

import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateBookingController implements Initializable {

    @FXML
    private CheckBox chbOil;
    @FXML
    private CheckBox chbAC;
    @FXML
    private Label lblA;
    @FXML
    private Label lblB;
    @FXML
    private MenuItem mbInspection;
    @FXML
    private MenuItem mbRepair;
    @FXML
    private MenuItem mbWash;
    @FXML
    private MenuButton mbService;

    @FXML
    private CheckBox chbInspectionBasic;
    @FXML
    private CheckBox chbInspectionAdvanced;

    @FXML
    private CheckBox chbRepairOil;
    @FXML
    private CheckBox chbRepairAC;
    @FXML
    private CheckBox chbRepairWheel;
    @FXML
    private CheckBox chbRepairTimingBelt;

    @FXML
    private CheckBox chbWashBasicExt;
    @FXML
    private CheckBox chbWashPremiumExt;
    @FXML
    private CheckBox chbWashInterior;
    @FXML
    private CheckBox chbWashComplete;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea txtATotal;
    @FXML
    private TextField dateField;


    ArrayList<String> subs = new ArrayList<>();
    ArrayList<Double> subsCost = new ArrayList<>();
    ArrayList<String> services = new ArrayList<>();


    int price = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleMbInspection(ActionEvent e) {
        mbService.setText(mbInspection.getText());

        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(true);
        toggleWashCheckBoxes(false);
    }

    @FXML
    private void handleMbRepair(ActionEvent e) {
        mbService.setText(mbRepair.getText());

        toggleRepairCheckBoxes(true);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(false);
    }

    @FXML
    private void handleMbWash(ActionEvent e) {
        mbService.setText(mbWash.getText());

        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(true);
    }

    @FXML
    private void handleButton() {

        if (chbRepairOil.isSelected()) {
            price += 50;
            subsCost.add((double) 50);
            subs.add("Oil change");
            txtATotal.appendText(chbRepairOil.getText() + "$99.99 \n");
            services.add(String.valueOf(chbRepairOil));
            System.out.println(services);
        }
        if (chbRepairAC.isSelected()) {
            price += 100;
            subsCost.add((double) 50);
            subs.add("AC fix");
            txtATotal.appendText(chbRepairAC.getText() + " $149 \n");
            services.add(String.valueOf(chbRepairAC));
            System.out.println(services);
        }
        if (chbRepairWheel.isSelected()) {
            price += 100;
            subsCost.add((double) 50);
            subs.add("Wheel Fix");
            txtATotal.appendText(chbRepairWheel.getText() + " $49.99 \n");
            services.add(String.valueOf(chbRepairWheel));
            System.out.println(services);
        }
        if (chbRepairTimingBelt.isSelected()) {
            price += 100;
            subsCost.add((double) 50);
            subs.add("Timing belt fix");
            txtATotal.appendText(chbRepairTimingBelt.getText() + " $499.99 \n");
            services.add(String.valueOf(chbRepairTimingBelt));
            System.out.println(services);
        }
        if (chbInspectionBasic.isSelected()) {
            price += 100;
            subsCost.add((double) 50);
            subs.add("Basic Inspection");
            txtATotal.appendText(chbInspectionBasic.getText() + " $39.99 \n");
            services.add(String.valueOf(chbInspectionBasic));
            System.out.println(services);
        }
        if (chbInspectionAdvanced.isSelected()) {
            price += 100;
            subsCost.add((double) 50);
            subs.add("Advanced Inspection");
            txtATotal.appendText(chbInspectionAdvanced.getText() + " $59.99 \n");
            services.add(String.valueOf(chbInspectionAdvanced));
            System.out.println(services);
        }
        if (chbWashBasicExt.isSelected()) {
            price += 50;
            subsCost.add((double) 50);
            subs.add("Basic exterior wash");
            txtATotal.appendText(chbWashBasicExt.getText() + " $19.99 \n");
            services.add(String.valueOf(chbRepairTimingBelt));
            System.out.println(services);
        }
        if (chbWashPremiumExt.isSelected()) {
            price += 50;
            subsCost.add((double) 50);
            subs.add("Premium Exterior Wash");
            txtATotal.appendText(chbWashPremiumExt.getText() + " $59.99\n");
            services.add(String.valueOf(chbRepairTimingBelt));
            System.out.println(services);
        }
        if (chbWashInterior.isSelected()) {
            price += 50;
            subsCost.add((double) 50);
            subs.add("Interior Wash");
            txtATotal.appendText(chbWashInterior.getText() + " $19.99 \n");
            services.add(String.valueOf(chbWashInterior));
            System.out.println(services);
        }
        if (chbWashComplete.isSelected()) {
            price += 50;
            subsCost.add((double) 50);
            subs.add("Complete Wash");
            txtATotal.appendText( chbWashComplete.getText() + " 49.99\n");
            services.add(String.valueOf(chbWashComplete));
            System.out.println(services);
        }

    }

    private void toggleInspectionCheckBoxes(boolean toggle) {
        chbInspectionBasic.setVisible(toggle);
        chbInspectionAdvanced.setVisible(toggle);
    }


    private void toggleRepairCheckBoxes(boolean toggle) {
        chbRepairOil.setVisible(toggle);
        chbRepairAC.setVisible(toggle);
        chbRepairWheel.setVisible(toggle);
        chbRepairTimingBelt.setVisible(toggle);
    }

    private void toggleWashCheckBoxes(boolean toggle) {
        chbWashBasicExt.setVisible(toggle);
        chbWashPremiumExt.setVisible(toggle);
        chbWashInterior.setVisible(toggle);
        chbWashComplete.setVisible(toggle);
    }

    @FXML
    public void clearSelectionsButton() {

        txtATotal.setText(null);
        services.clear();
        System.out.println(services);
    }

    @FXML
    public void Backbutton(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateBooking.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
}

