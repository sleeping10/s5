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
import sample.Account;
import sample.DBC;
import sample.SceneSwitcher;
import sample.Verification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController extends Verification implements Initializable {

    @FXML private TextField tfName;
    @FXML private TextField tfEmail;
    @FXML private PasswordField tfPass;
    @FXML private TextField tfPhone;
    @FXML private Button btnSignup;
    @FXML private CheckBox remember;
    @FXML private Button forgetPassword;
    @FXML private Label lblStatus;
    private Account acc = null;

    SceneSwitcher sw = new SceneSwitcher();

    @FXML private void handleButtonSignUp(ActionEvent event){
        SignUp();

    }

    private void SignUp(){
     if (verifyAccount(tfEmail.getText(),tfPass.getText(), tfPhone.getText())){
         acc = new Account(tfEmail.getText(),tfPass.getText(),tfName.getText(),tfPhone.getText(),0, Account.access.Customer);
         DBC.getInstance().saveAccount(acc);
         lblStatus.setText("Account created");
     }else {
        lblStatus.setText("Email or phone already connected to an account");
     }
    }



    @FXML private void handleButtonGoBack(ActionEvent event) {

        //changeToLoginScene(event);
        sw.loginSignupSceneSwitcher(event, "Login");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
