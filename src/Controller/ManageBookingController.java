package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {
    Account acc = DBC.getInstance().getAccount();
    @FXML private TextField tfPhone;
    @FXML private TextField tfBookingID;
    @FXML private TableColumn tcBookingID;
    @FXML private TableColumn tcStartDate;
    @FXML private TableColumn tcAccID;
    @FXML private TableColumn tcBookingDesc;
    @FXML private TableView tvField;
    @FXML private Button buttonFilter;
    @FXML private TableColumn tcRegNR;

    private ArrayList<Booking> list = new ArrayList<>();


    // för table view kanske flytta?
    private ObservableList<Booking> view (){
        String s ="";
        ObservableList<Booking> views = FXCollections.observableArrayList();
        list=DBC.getInstance().getBooking();
        for (int i = 0; i <list.size() ; i++) {
            for (int j = 0; j < list.get(i).getServices().size() ; j++) {
                s = list.get(i).getServices().get(j).getServiceName().replace("_", " ");
                list.get(i).getServices().get(j).setServiceName(s);
            }

            views.add(list.get(i));

        }
        return views;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(acc.getAccessType()==3) {
            tfPhone.setVisible(false);
            tfBookingID.setVisible(false);
            buttonFilter.setVisible(false);
            tcAccID.setVisible(false);
        }

        tcBookingID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("bookingID"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<Date, Booking>("date"));
        tcAccID.setCellValueFactory(new PropertyValueFactory<Integer, Booking>("accountID"));
        tcBookingDesc.setCellValueFactory(new PropertyValueFactory<String, Booking>("bookingDesc"));
        tcRegNR.setCellValueFactory(new PropertyValueFactory<String, Booking>("licensePlate"));
        tvField.setItems(view());


    }



    public void filterButtonPressed(ActionEvent actionEvent) {
       tvField.getItems().clear();
        if (!tfBookingID.getText().isEmpty()) {
            for (Booking b : list) {
                if (tfBookingID.getText().matches(String.valueOf(b.getBookingID()))) {
                    tvField.getItems().add(b);
                }
            }
        } else if (!tfPhone.getText().isEmpty()){
            for(Booking b : list) {
                if (tfPhone.getText().matches(DBC.getInstance().getPhoneFilter(b.getAccountID()))) {
                    tvField.getItems().add(b);
                }
            }

        } else if (tfBookingID.getText().isEmpty() && tfPhone.getText().isEmpty()){
            tvField.setItems(view());
        }

    }

    // changes to detailed view
    @FXML
    public void handleButtonManagePressed(ActionEvent actionEvent) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/DetailedBookingView.fxml"));
                Parent root1 = fxmlLoader.load();
                DetailedBookingViewController controller = fxmlLoader.getController();
                controller.initBooking((Booking)tvField.getSelectionModel().getSelectedItem());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Manage Booking");
                stage.setScene(new Scene(root1));
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }




    }

    public void handleButtonCancelAppointmentPressed(ActionEvent actionEvent) {
        //Delete selected row




        //för google maps ta inte bort
//    @FXML private WebView wv;
//        WebEngine we = wv.getEngine();
//        we.load("https://goo.gl/maps/38arBYM6DH9aEyYh7");
    }
}
