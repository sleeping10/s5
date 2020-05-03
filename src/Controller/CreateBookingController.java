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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private CheckBox chbWheelAlignment;
    @FXML
    private CheckBox chbRepairTimingBelt;
    @FXML
    private CheckBox chbChangeBattery;


    @FXML
    private CheckBox chbWashBasicExt;
    @FXML
    private CheckBox chbWashPremiumExt;
    @FXML
    private CheckBox chbWashInterior;
    @FXML
    private CheckBox chbWashInteriorPremium;
    @FXML
    private CheckBox chbWashComplete;
    @FXML
    private CheckBox chbWashCompletePremium;

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
        txtATotal.clear();
        Date date = new Date();

        if (chbRepairOil.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("service_oilchange");
            price += cost;
            //subsCost.add((double) 50);
            subs.add("Oil change");
            txtATotal.appendText(chbRepairOil.getText() + "$99.99 \n");
            services.add("service_oilchange");
            System.out.println("service_oilchange");
        }
        if (chbRepairAC.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("service_ac");
            price += cost;
            //subsCost.add((double) 50);
            subs.add("AC fix");
            txtATotal.appendText(chbRepairAC.getText() + " $149 \n");
            services.add("service_ac");
            System.out.println(services);
        }
        if (chbRepairWheel.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("service_wheelchange");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Wheel change");
            txtATotal.appendText(chbRepairWheel.getText() + " $49.99 \n");
            services.add("service_wheelchange");
            System.out.println("service_wheelchange");
        }
        if (chbWheelAlignment.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("service_wheelalignment");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Wheel Alignment");
            txtATotal.appendText(chbWheelAlignment.getText() + " $109.99 \n");
            services.add("service_wheelalignment");
            System.out.println("service_wheelalignment");
        }
        if (chbChangeBattery.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("service_battery");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Battery Change");
            txtATotal.appendText(chbWheelAlignment.getText() + " $89.99 \n");
            services.add("service_battery");
            System.out.println("service_battery");
        }
        if (chbRepairTimingBelt.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("service_timingbelt");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Timing belt change");
            txtATotal.appendText(chbRepairTimingBelt.getText() + " $499.99 \n");
            services.add("service_timingbelt");
            System.out.println("service_timingbelt");
        }
        if (chbInspectionBasic.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("inspection_basic");
            price += cost;
            // subsCost.add((double) 50);
            subs.add("Basic Inspection");
            txtATotal.appendText(chbInspectionBasic.getText() + " $39.99 \n");
            services.add("inspection_basic");
            System.out.println("inspection_basic");
        }
        if (chbInspectionAdvanced.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("inspection_advanced");
            price += cost;
            // subsCost.add((double) 50);
            subs.add("Advanced Inspection");
            txtATotal.appendText(chbInspectionAdvanced.getText() + " $59.99 \n");
            services.add("inspection_advanced");
            System.out.println("inspection_advanced");
        }
        if (chbWashBasicExt.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("wash_basic_exterior");
            price += cost;
            //   subsCost.add((double) 50);
            subs.add("Basic exterior wash");
            txtATotal.appendText(chbWashBasicExt.getText() + " $29.99 \n");
            services.add("wash_basic_exterior");
            System.out.println("wash_basic_exterior");
        }
        if (chbWashPremiumExt.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("wash_premium_exterior");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Premium Exterior Wash");
            txtATotal.appendText(chbWashPremiumExt.getText() + " $59.99\n");
            services.add("wash_premium_exterior");
            System.out.println("wash_premium_exterior");
        }
        if (chbWashInterior.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("wash_basic_interior");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Interior Wash");
            txtATotal.appendText(chbWashInterior.getText() + " $19.99 \n");
            services.add("wash_basic_interior");
            System.out.println("wash_basic_interior");
        }
        if (chbWashInteriorPremium.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("wash_premium_interior");
            price += cost;
            //  subsCost.add((double) 50);
            subs.add("Interior Wash Premium");
            txtATotal.appendText(chbWashInterior.getText() + " $79.99 \n");
            services.add("wash_premium_interior");
            System.out.println("wash_premium_interior");
        }
        if (chbWashComplete.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("wash_compl_basic");
            price += cost;
            // subsCost.add((double) 50);
            subs.add("Complete Wash");
            txtATotal.appendText(chbWashComplete.getText() + " 49.99\n");
            services.add("wash_compl_basic");
            System.out.println("wash_compl_basic");
        }
        if (chbWashCompletePremium.isSelected()) {
            double cost = DBC.getInstance().getServiceCost("wash_compl_premium");
            price += cost;
            // subsCost.add((double) 50)
            subs.add("complete wash premium");
            txtATotal.appendText(chbWashCompletePremium.getText() + "79.99\n");
        }


        DBC.getInstance().addBooking(new Booking(5, date, "test", DBC.getInstance().getAccount().getAccountID(), services));


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
        chbWheelAlignment.setVisible(toggle);
        chbChangeBattery.setVisible(toggle);

    }

    private void toggleWashCheckBoxes(boolean toggle) {
        chbWashBasicExt.setVisible(toggle);
        chbWashPremiumExt.setVisible(toggle);
        chbWashInterior.setVisible(toggle);
        chbWashComplete.setVisible(toggle);
        chbWashCompletePremium.setVisible(toggle);
        chbWashInteriorPremium.setVisible(toggle);
    }

    @FXML
    public void clearSelectionsButton() {

        txtATotal.setText(null);
        services.clear();
        System.out.println(services);
        chbRepairOil.setSelected(false);
        chbRepairAC.setSelected(false);
        chbRepairWheel.setSelected(false);
        chbRepairTimingBelt.setSelected(false);
        chbInspectionBasic.setSelected(false);
        chbInspectionAdvanced.setSelected(false);
        chbWashBasicExt.setSelected(false);
        chbWashInterior.setSelected(false);
        chbWashPremiumExt.setSelected(false);
        chbWashComplete.setSelected(false);
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

