package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminMenuController extends ServiceHandler implements Initializable {

    @FXML private MenuItem menuItemBasicExtWash;
    @FXML private MenuItem menuItemWheelAlignment;
    @FXML private MenuItem menuItemBatteryChange;
    @FXML private MenuItem menuItemTimingBelt;
    @FXML private MenuItem menuItemWheelChange;
    @FXML private MenuItem menuItemACService;
    @FXML private MenuItem menuItemOilChange;
    @FXML private MenuItem menuItemBasicInspection;
    @FXML private MenuItem menuItemAdvancedInspection;
    @FXML private MenuItem menuItemPremExtWash;
    @FXML private MenuItem menuItemBasicIntWash;
    @FXML private MenuItem menuItemPremIntWash;
    @FXML private MenuItem menuItemComplBasicWash;
    @FXML private MenuItem menuItemComplPremiumWash;

    @FXML private TextField tfStandardCost;

    @FXML private TextField tfDiscount;
    @FXML private TextField tfStartDate;
    @FXML private TextField tfEndDate;
    @FXML private MenuButton menuButtonServices;

    @FXML private TableView<Account> tvUserList;
    @FXML private TableColumn<Integer, Account> tcAccID;
    @FXML private TableColumn<String, Account> tcEmail;
    @FXML private TableColumn<String, Account>  tcName;
    @FXML private TableColumn<String, Account>  tcPhone;
    @FXML private TableColumn<Integer, Account>  tcAccess;

    @FXML private RadioButton chbAdmin;
    @FXML private RadioButton chbEmployee;
    @FXML private RadioButton chbCustomer;
    @FXML private Button btnDeleteAcc;
    @FXML private ToggleGroup tGroup;

    private ArrayList<Account> userList = new ArrayList<>();
    private String selectedService;

    @FXML private Label lblStatus;

    private ObservableList<Account> view (){
        ObservableList<Account> views = FXCollections.observableArrayList();
        userList=DBC.getInstance().getAllUsers();
        views.addAll(userList);
        return views;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateUserTableView();

        if (DBC.getInstance().getCurrentAcc().getAccessType() == 2 ||
                DBC.getInstance().getCurrentAcc().getAccessType() == 3){
            btnDeleteAcc.setDisable(true);
        }
        tGroup = new ToggleGroup();
        chbAdmin.setToggleGroup(tGroup);
        chbEmployee.setToggleGroup(tGroup);
        chbCustomer.setToggleGroup(tGroup);

    }

    private void updateUserTableView(){
        tcAccID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNr"));
        tcAccess.setCellValueFactory(new PropertyValueFactory<>("accessType"));
        tvUserList.setItems(view());
    }

    @FXML
    private void handleMenuItemBasicInspection(){
        menuButtonServices.setText(menuItemBasicInspection.getText());
        selectedService = "inspection_basic";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemAdvancedInspection(){
        menuButtonServices.setText(menuItemAdvancedInspection.getText());
        selectedService = "inspection_advanced";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemOilChange(){
        menuButtonServices.setText(menuItemOilChange.getText());
        selectedService = "service_oilchange";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemACService(){
        menuButtonServices.setText(menuItemACService.getText());
        selectedService = "service_ac";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemBatteryChange(){
        menuButtonServices.setText(menuItemBatteryChange.getText());
        selectedService = "service_battery";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemWheelChange(){
        menuButtonServices.setText(menuItemWheelChange.getText());
        selectedService = "service_wheelchange";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemWheelAlignment(){
        menuButtonServices.setText(menuItemWheelAlignment.getText());
        selectedService = "service_wheelalignment";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemTimingBelt(){
        menuButtonServices.setText(menuItemTimingBelt.getText());
        selectedService = "service_timingbelt";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemBasicExtWash(){
        menuButtonServices.setText(menuItemBasicExtWash.getText());
        selectedService = "wash_basic_exterior";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemPremiumExtWash(){
        menuButtonServices.setText(menuItemPremExtWash.getText());
        selectedService = "wash_premium_exterior";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemBasicIntWash(){
        menuButtonServices.setText(menuItemBasicIntWash.getText());
        selectedService = "wash_basic_interior";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemPremiumIntWash(){
        menuButtonServices.setText(menuItemPremIntWash.getText());
        selectedService = "wash_premium_interior";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemComplBasicWash(){
        menuButtonServices.setText(menuItemComplBasicWash.getText());
        selectedService = "wash_compl_basic";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleMenuItemComplPremiumWash(){
        menuButtonServices.setText(menuItemComplPremiumWash.getText());
        selectedService = "wash_compl_premium";
        tfStandardCost.setPromptText(String.valueOf(getDefaultServiceCost(selectedService)));
    }

    @FXML
    private void handleApplyBtnCost(){
        double cost = 0;

        try {
            cost = Double.parseDouble(tfStandardCost.getText());
        }catch (NumberFormatException e){
            System.out.println("Not a number");
            lblStatus.setText("Status: Error, only numbers allowed");
        }

        if (cost != 0 && selectedService != null){
                DBC.getInstance().setServiceCost(selectedService, cost);
                lblStatus.setText("Status: Service Cost changed successfully");
                tfStandardCost.setPromptText(String.valueOf(cost));
        }
        tfStandardCost.setText("");

    }

    @FXML
    private void handleBtnApplyDiscount(){
        double discount;
        try {
            discount = Double.parseDouble(tfDiscount.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (!tfStartDate.getText().isEmpty() && !tfEndDate.getText().isEmpty() && selectedService != null){
                Date parsedStartDate = dateFormat.parse(tfStartDate.getText());
                Date parsedEndDate = dateFormat.parse(tfEndDate.getText());
                Timestamp timestampStart = new java.sql.Timestamp(parsedStartDate.getTime());
                Timestamp timestampEnd = new java.sql.Timestamp(parsedEndDate.getTime());

                DBC.getInstance().setDiscount(selectedService, discount, timestampStart, timestampEnd);
                lblStatus.setText("Status: Discount Cost changed successfully");
            }else if (selectedService != null){
                DBC.getInstance().setDiscount(selectedService, 0, null, null);
            }

        } catch(Exception e) {
            lblStatus.setText("Status: Date format wrong, YYYY-MM-DD HH:MM");
        }

    }

    @FXML
    private void handleDeleteAccBtn(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the user and" +
                " all of their bookings?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.YES) {
            Account tempacc;
            tempacc = tvUserList.getSelectionModel().getSelectedItem();
            DBC.getInstance().removeAllBookings(tempacc);
            DBC.getInstance().deleteUser(tempacc.getAccountID());
            lblStatus.setText("Status: User removed successfully");
        }
        updateUserTableView();
    }

    @FXML
    private void handlePrivilegiesBtn(){
        Account tempacc;
        tempacc = tvUserList.getSelectionModel().getSelectedItem();

        if (chbAdmin.isSelected()){
            tempacc.setAccessType(1);
        }else if(chbEmployee.isSelected()){
            tempacc.setAccessType(2);
        }else if(chbCustomer.isSelected()){
            tempacc.setAccessType(3);
        }
        DBC.getInstance().updateAccount(tempacc);
        updateUserTableView();
    }
    @FXML
    private void handleButtonRemoveBookingsPressed(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove " +
                "all of the selected user's bookings?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.YES) {
            Account tempacc;
            tempacc = tvUserList.getSelectionModel().getSelectedItem();
            DBC.getInstance().removeAllBookings(tempacc);
            lblStatus.setText("Status: All bookings removed successfully");
        }
        updateUserTableView();
    }
}
