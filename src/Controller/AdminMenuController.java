package Controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

//This class is not done/implemented yet
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
    @FXML private Button btnApplyCost;
    @FXML private Button btnApplyDiscount;
    @FXML private MenuButton menuButtonServices;

    @FXML private TableView tvUserList;
    @FXML private TableColumn tcAccID;
    @FXML private TableColumn tcEmail;
    @FXML private TableColumn tcName;
    @FXML private TableColumn tcPhone;
    @FXML private TableColumn tcAccess;

    @FXML private RadioButton chbAdmin;
    @FXML private RadioButton chbEmployee;
    @FXML private RadioButton chbCustomer;
    @FXML private Button btnApplyPermissions;

    @FXML private Button btnDeleteAcc;

    private ArrayList<Account> userList = new ArrayList<>();
    private String selectedService;

    @FXML private Label lblStatus;

    public ObservableList<Account> view (){
        ObservableList<Account> views = FXCollections.observableArrayList();
        userList=DBC.getInstance().getAllUsers();
        for (int i = 0; i <userList.size() ; i++) {
            views.add(userList.get(i));
        }
        return views;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateUserTableView();

        if (DBC.getInstance().getCurrentAcc().getAccessType() == 2 ||
                DBC.getInstance().getCurrentAcc().getAccessType() == 3){
            btnDeleteAcc.setDisable(true);
        }

        //tvUserList.getSelectionModel().selectedIndexProperty().addListener((num) -> setCheckBoxes((Account)tvUserList.getSelectionModel().getSelectedItem()));


    }

    private void updateUserTableView(){
        tcAccID.setCellValueFactory(new PropertyValueFactory<Integer, Account>("accountID"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<String, Account>("email"));
        tcName.setCellValueFactory(new PropertyValueFactory<String, Account>("name"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<String, Account>("phoneNr"));
        tcAccess.setCellValueFactory(new PropertyValueFactory<Integer, Account>("accessType"));
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
    private void handleDeleteAccBtn(){
        Account tempacc;
        tempacc = (Account) tvUserList.getSelectionModel().getSelectedItem();
        if(!DBC.getInstance().deleteUser(tempacc.getAccountID())){
        lblStatus.setText("Status: Error, user has bookings cannot delete");
        }else{
            lblStatus.setText("Status: User sucessfully deleted");
        }
        updateUserTableView();

    }
}
