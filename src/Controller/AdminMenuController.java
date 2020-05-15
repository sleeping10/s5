package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import sample.DBC;
import sample.Service;
import sample.ServiceHandler;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML private TextField tfStandardCost;
    @FXML private TextField tfDiscount;
    @FXML private TextField tfStartDate;
    @FXML private TextField tfEndDate;
    @FXML private Button btnApplyCost;
    @FXML private Button btnApplyDiscount;



    private ArrayList<Service> services;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        services = DBC.getInstance().getAvailableServices();
    }

    @FXML
    private void handleMenuItemBasicInspection(){
        tfStandardCost.setText(String.valueOf(getServiceCost("inspection_basic")));
    }
}
