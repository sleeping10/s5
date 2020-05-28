package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.*;
//import sample.editDocument;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateBookingController extends ServiceHandler implements Initializable {

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
    private CheckBox chbRepairWheelChange;
    @FXML
    private CheckBox chbRepairWheelAlignment;
    @FXML
    private CheckBox chbRepairTimingBelt;
    @FXML
    private CheckBox chbRepairChangeBattery;


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
    @FXML
    private TextField tfLicense;
    @FXML
    private Button btnClearSelections;
    @FXML
    private TextArea taDesc;
    @FXML
    private Button btnCreateBooking;

    @FXML
    private Label lblCostOne;
    @FXML
    private Label lblCostTwo;
    @FXML
    private Label lblCostThree;
    @FXML
    private Label lblCostFour;
    @FXML
    private Label lblCostFive;
    @FXML
    private Label lblCostSix;
    @FXML
    private Label lblTotalCost;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblInfo;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnGoBack;

    @FXML
    private GridPane gridPaneMain;
    @FXML
    private GridPane gridPaneTwo;

    @FXML
    private ListView lwTimes;
    @FXML
    private RadioButton rdB;

    @FXML
    private ProgressBar progressBar;

    private ArrayList<Service> services = new ArrayList<>();
    private Booking tempB;
    private CreatePdf editD = new CreatePdf();
    File source = new File("./s5/src/FXML/booking_confirmation.docx");
    File dest = new File("./s5/src//FXML/test.docx");


    private double totalCost = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        services.clear();

        progressBar.setProgress(0.0);
        lwTimes.setVisible(false);
        taDesc.setVisible(false);
        gridPaneMain.setVisible(true);
        tfLicense.setVisible(false);
        lblTotalCost.setVisible(true);
        txtATotal.setEditable(false);
        btnCreateBooking.setVisible(false);

        final Tooltip tooltipMbService = new Tooltip();
        tooltipMbService.setText("Please select a service that you would like us to help you with");
        mbService.setTooltip(tooltipMbService);


        final Tooltip tooltipClear = new Tooltip();
        tooltipClear.setText("This will erase all your selected services");
        btnClearSelections.setTooltip(tooltipClear);


        final Tooltip tooltipCreateBooking = new Tooltip();
        tooltipCreateBooking.setText("Finalize your booking");
        btnNext.setTooltip(tooltipCreateBooking);

    }

    @FXML
    private void handleMbInspection(ActionEvent e) {
        mbService.setText(mbInspection.getText());

        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(true);
        toggleWashCheckBoxes(false);
        toggleCostLabels(1);
    }

    @FXML
    private void handleMbRepair(ActionEvent e) {
        mbService.setText(mbRepair.getText());

        toggleRepairCheckBoxes(true);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(false);
        toggleCostLabels(2);
    }

    @FXML
    private void handleMbWash(ActionEvent e) {
        mbService.setText(mbWash.getText());
        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(true);
        toggleCostLabels(3);
    }

    @FXML
    private void handleDatePicker(){
        progressBar.setProgress(0.8);
    }

    private void toggleCostLabels(int toggle) {
        if (toggle == 1) {
            lblCostOne.setVisible(true);
            lblCostTwo.setVisible(true);
            lblCostThree.setVisible(false);
            lblCostFour.setVisible(false);
            lblCostFive.setVisible(false);
            lblCostSix.setVisible(false);
        } else if (toggle == 2 || toggle == 3) {
            lblCostOne.setVisible(true);
            lblCostTwo.setVisible(true);
            lblCostThree.setVisible(true);
            lblCostFour.setVisible(true);
            lblCostFive.setVisible(true);
            lblCostSix.setVisible(true);
        } else {
            lblCostOne.setVisible(false);
            lblCostTwo.setVisible(false);
            lblCostThree.setVisible(false);
            lblCostFour.setVisible(false);
            lblCostFive.setVisible(false);
            lblCostSix.setVisible(false);
        }
    }


    @FXML
    private void handleCreateBookingBtn() {

        Calendar cal = Calendar.getInstance();

        java.util.Date date = null;
        txtATotal.clear();
        try {
            date = java.sql.Date.valueOf(datePicker.getValue());
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(lwTimes.getSelectionModel().getSelectedItem().toString().substring(0, 2)));
            cal.set(Calendar.MINUTE, Integer.parseInt(lwTimes.getSelectionModel().getSelectedItem().toString().substring(3, 5)));
            date = cal.getTime();
            System.out.println("date: "+ date);
        } catch (NullPointerException e) {
            System.out.println("ERROR: Not able to get date");
        }

        if (datePicker.getValue() == null || !Verification.validateLicensePlate(tfLicense.getText())) {
            lblStatus.setText("Status: Please select a date and Registration ID");
        } else if (services.isEmpty()) {
            lblStatus.setText("Status: Please select at least 1 service");
        } else {
            tempB = new Booking( 0, date, taDesc.getText(), DBC.getInstance().getCurrentAcc().getAccountID(),
                    tfLicense.getText(), services, false);
            System.out.println(tempB);
            DBC.getInstance().addBooking(tempB);
            progressBar.setProgress(1.0);
            lblTotalCost.setText("Total cost: $" + totalCost);
            lblStatus.setText("Status: Booking successfully added");
            btnCreateBooking.setDisable(true);
            startCreatePdf();
        }
    }


    @FXML
    private void handleNextBtn() {
        progressBar.setProgress(0.3);
        toggleRepairCheckBoxes(false);
        toggleInspectionCheckBoxes(false);
        toggleWashCheckBoxes(false);
        mbService.setVisible(false);
        btnClearSelections.setVisible(false);
        btnGoBack.setVisible(true);
        taDesc.setVisible(true);
        btnCreateBooking.setVisible(true);
        btnNext.setVisible(false);
        datePicker.setVisible(true);
        tfLicense.setVisible(true);
        lblStatus.setVisible(true);
        lblInfo.setVisible(true);
        lblStatus.setText("Status: ");
        toggleCostLabels(0);
        gridPaneTwo.setVisible(true);
        lwTimes.setVisible(true);
        fillTimesListView();
        rdB.setVisible(true);
    }

    private void fillTimesListView() {
        progressBar.setProgress(0.6);
        ArrayList<Booking> bookingsToCheck = DBC.getInstance().getAllBookings();
        String timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
        ArrayList<String> times = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        System.out.println(sdf.format(cal.getTime()));
        Date first, second;

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
        try {
            first = format.parse("08:00");
            second = format.parse("20:00");

            Date next = first;
            do {
                times.add(format.format(next));
            } while ((next = new Date(next.getTime() + 30*60*1000)).before(second));


        }catch (Exception e){
            e.printStackTrace();
        }
        ObservableList<String> items = FXCollections.observableArrayList(times);
        lwTimes.setItems(items);

    }

    @FXML
    private void handleGoBackBtn() {
        mbService.setVisible(true);
        btnClearSelections.setVisible(true);
        btnGoBack.setVisible(false);
        taDesc.setVisible(false);
        btnCreateBooking.setVisible(false);
        btnNext.setVisible(true);
        datePicker.setVisible(false);
        tfLicense.setVisible(false);
        lblStatus.setVisible(true);
        lblInfo.setVisible(false);
        lblStatus.setText("Status: ");
        gridPaneTwo.setVisible(false);
        rdB.setVisible(false);
        lwTimes.setVisible(false);
    }

    private void toggleInspectionCheckBoxes(boolean toggle) {
        chbInspectionBasic.setVisible(toggle);
        chbInspectionAdvanced.setVisible(toggle);
        if (toggle) {
            lblCostOne.setText(getServiceCostAsString("inspection_basic"));
            lblCostTwo.setText(getServiceCostAsString("inspection_advanced"));
        }
    }

    private void addServiceToCurrentBooking(boolean toggle, String service) {
        progressBar.setProgress(0.2);
        Service tempService = DBC.getInstance().getService(service);
        double cost = getServiceCost(service);
        if (toggle) {
            totalCost += cost;
            txtATotal.appendText(tempService.getServiceName() + ", $" + (tempService.getCost()) + "\n");
            services.add(tempService);
        } else {
            txtATotal.clear();
            totalCost -= cost;
            for (int i = 0; i < services.size(); i++) {
                if (services.get(i).getServiceName().equals(service)){
                    services.remove(i);
                    break;
                }
                txtATotal.appendText(services.get(i).getServiceName() + ", $" + (services.get(i).getCost()) + "\n");
            }
        }
        lblTotalCost.setText("Total cost: $" + Math.round(totalCost));
    }


    @FXML
    private void handleInspectionBasicChbox() {
        if (chbInspectionBasic.isSelected()) {
            addServiceToCurrentBooking(true, "inspection_basic");
        } else {
            addServiceToCurrentBooking(false, "inspection_basic");
        }
    }

    @FXML
    private void handleInspectionAdvancedChbox() {
        if (chbInspectionAdvanced.isSelected()) {
            addServiceToCurrentBooking(true, "inspection_advanced");
        } else {
            addServiceToCurrentBooking(false, "inspection_advanced");
        }
    }

    @FXML
    private void handleServiceOilChbox() {
        if (chbRepairOil.isSelected()) {
            addServiceToCurrentBooking(true, "service_oilchange");
        } else {
            addServiceToCurrentBooking(false, "service_oilchange");
        }
    }

    @FXML
    private void handleServiceACFixChbox() {
        if (chbRepairAC.isSelected()) {
            addServiceToCurrentBooking(true, "service_ac");
        } else {
            addServiceToCurrentBooking(false, "service_ac");
        }
    }

    @FXML
    private void handleServiceTimingBeltChbox() {
        if (chbRepairTimingBelt.isSelected()) {
            addServiceToCurrentBooking(true, "service_timingbelt");
        } else {
            addServiceToCurrentBooking(false, "service_timingbelt");
        }
    }

    @FXML
    private void handleServiceWheelAlignChbox() {
        if (chbRepairWheelAlignment.isSelected()) {
            addServiceToCurrentBooking(true, "service_wheelalignment");
        } else {
            addServiceToCurrentBooking(false, "service_wheelalignment");
        }
    }

    @FXML
    private void handleServiceWheelChangeChbox() {
        if (chbRepairWheelChange.isSelected()) {
            addServiceToCurrentBooking(true, "service_wheelchange");
        } else {
            addServiceToCurrentBooking(false, "service_wheelchange");
        }
    }

    @FXML
    private void handleServiceChangeBatteryChbox() {
        if (chbRepairChangeBattery.isSelected()) {
            addServiceToCurrentBooking(true, "service_battery");
        } else {
            addServiceToCurrentBooking(false, "service_battery");
        }
    }

    @FXML
    private void handleWashBasicExtChb() {
        if (chbWashBasicExt.isSelected()) {
            addServiceToCurrentBooking(true, "wash_basic_exterior");
        } else {
            addServiceToCurrentBooking(false, "wash_basic_exterior");
        }
    }

    @FXML
    private void handleWashPremiumExtChb() {
        if (chbWashPremiumExt.isSelected()) {
            addServiceToCurrentBooking(true, "wash_premium_exterior");
        } else {
            addServiceToCurrentBooking(false, "wash_premium_exterior");
        }
    }

    @FXML
    private void handleWashBasicInteriorChb() {
        if (chbWashInterior.isSelected()) {
            addServiceToCurrentBooking(true, "wash_basic_interior");
        } else {
            addServiceToCurrentBooking(false, "wash_basic_interior");
        }
    }

    @FXML
    private void handleWashInteriorPremiumChb() {
        if (chbWashInteriorPremium.isSelected()) {
            addServiceToCurrentBooking(true, "wash_premium_interior");
        } else {
            addServiceToCurrentBooking(false, "wash_premium_interior");
        }
    }

    @FXML
    private void handleWashBasicCompleteChb() {
        if (chbWashComplete.isSelected()) {
            addServiceToCurrentBooking(true, "wash_compl_basic");
        } else {
            addServiceToCurrentBooking(false, "wash_compl_basic");
        }
    }

    @FXML
    private void handleWashPremiumCompleteChb() {
        if (chbWashCompletePremium.isSelected()) {
            addServiceToCurrentBooking(true, "wash_compl_premium");
        } else {
            addServiceToCurrentBooking(false, "wash_compl_premium");
        }
    }


    private void toggleRepairCheckBoxes(boolean toggle) {
        chbRepairOil.setVisible(toggle);
        chbRepairAC.setVisible(toggle);
        chbRepairWheelChange.setVisible(toggle);
        chbRepairTimingBelt.setVisible(toggle);
        chbRepairWheelAlignment.setVisible(toggle);
        chbRepairChangeBattery.setVisible(toggle);

        if (toggle) {
            lblCostOne.setText(getServiceCostAsString("service_oilchange"));
            lblCostTwo.setText(getServiceCostAsString("service_ac"));
            lblCostThree.setText(getServiceCostAsString("service_wheelchange"));
            lblCostFour.setText(getServiceCostAsString("service_timingbelt"));
            lblCostFive.setText(getServiceCostAsString("service_battery"));
            lblCostSix.setText(getServiceCostAsString("service_wheelalignment"));
        }

    }

    private void toggleWashCheckBoxes(boolean toggle) {
        chbWashBasicExt.setVisible(toggle);
        chbWashPremiumExt.setVisible(toggle);
        chbWashInterior.setVisible(toggle);
        chbWashComplete.setVisible(toggle);
        chbWashCompletePremium.setVisible(toggle);
        chbWashInteriorPremium.setVisible(toggle);

        if (toggle) {
            lblCostOne.setText(getServiceCostAsString("wash_basic_exterior"));
            lblCostTwo.setText(getServiceCostAsString("wash_premium_exterior"));
            lblCostThree.setText(getServiceCostAsString("wash_basic_interior"));
            lblCostFour.setText(getServiceCostAsString("wash_premium_interior"));
            lblCostFive.setText(getServiceCostAsString("wash_compl_basic"));
            lblCostSix.setText(getServiceCostAsString("wash_compl_premium"));
        }

    }

    @FXML
    public void handleClearSelectionsBtn() {
        progressBar.setProgress(0.1);
        txtATotal.setText(null);
        totalCost = 0;
        services.clear();
        chbRepairOil.setSelected(false);
        chbRepairAC.setSelected(false);
        chbRepairWheelChange.setSelected(false);
        chbRepairTimingBelt.setSelected(false);
        chbRepairWheelAlignment.setSelected(false);
        chbRepairChangeBattery.setSelected(false);

        chbInspectionBasic.setSelected(false);
        chbInspectionAdvanced.setSelected(false);

        chbWashBasicExt.setSelected(false);
        chbWashPremiumExt.setSelected(false);
        chbWashInterior.setSelected(false);
        chbWashInteriorPremium.setSelected(false);
        chbWashComplete.setSelected(false);
        chbWashCompletePremium.setSelected(false);
    }

    @FXML
    public void resetTemplate() {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel(); FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void startCreatePdf()  {
        if (rdB.isSelected()) {
            CreatePdf CreatePdf = new CreatePdf();
            try {
                resetTemplate();
                CreatePdf.createDocument(tempB);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}