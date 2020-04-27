package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Account;
import sample.DBC;
import sample.Verification;

import java.io.IOException;

public class SignupController extends Verification {

    @FXML private TextField tfName;
    @FXML private TextField tfEmail;
    @FXML private PasswordField tfPass;
    @FXML private TextField tfPhone;
    @FXML private Button btnSignup;
    @FXML private CheckBox remember;
    @FXML private Button forgetPassword;
    @FXML private Label lblStatus;
    private DBC dbc = new DBC();
    private Account acc = null;




    @FXML private void handleButtonSignUp(ActionEvent event){
        acc = new Account(tfEmail.getText(),tfPass.getText(),tfName.getText(),tfPhone.getText(),0, Account.access.Customer);
        dbc.saveAccount(acc);
        SignUp();

    }

    private void SignUp(){
     if (verifyEmail(tfEmail.getText())){
         lblStatus.setText("Email already exists");
     }else {

     }
    }



    @FXML private void handleButtonGoBack(ActionEvent event) {
        changeToLoginScene(event);
    }





    private void changeToLoginScene(ActionEvent event){
        try{
            Node node = (Node)event.getSource();
            Stage stage = (Stage)node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e){

        }
    }

}
