package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private SceneSwitcher ss = new SceneSwitcher();


    // för table view kanske flytta?
    public ObservableList<Booking> view (){
        ObservableList<Booking> views = FXCollections.observableArrayList();
        list=DBC.getInstance().getBooking();
        for (int i = 0; i <list.size() ; i++) {
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

        tvField.getSelectionModel().getSelectedItem();


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
//            Node node = (Node)actionEvent.getSource();
//            Stage stage = (Stage)node.getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/DetailedBookingView.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML/DetailedBookingView.fxml"));
            Parent parent = loader.load();
            Scene detailedScene = new Scene(parent);

            DetailedBookingViewController controller = loader.getController();
            controller.initBooking((Booking)tvField.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(detailedScene);
        }catch (Exception ex){
            ex.printStackTrace();
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
