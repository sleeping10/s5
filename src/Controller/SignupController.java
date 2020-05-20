package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController extends Verification implements Initializable {

    @FXML private TextField tfName;
    @FXML private TextField tfEmail;
    @FXML private PasswordField tfPass;
    @FXML private TextField tfPhone;
    @FXML private Label lblStatus;

    private SceneSwitcher sw = new SceneSwitcher();

    @FXML private void handleButtonSignUp(ActionEvent event){
        signUp();
    }

    private void signUp(){

        String salt = PasswordEncryption.generateSalt(5);
        String hashedPass = PasswordEncryption.hashPassword(tfPass.getText(), salt) + "-" + salt;

        switch (verifyAccount(tfEmail.getText(), tfPass.getText(), tfPhone.getText())) {
            case 0: lblStatus.setText("Email or Phone number already in use");break;
            case 1: DBC.getInstance().setCurrentAcc(new Account(0,tfEmail.getText(), hashedPass, tfName.getText(), tfPhone.getText(), 3));DBC.getInstance().saveAccount();lblStatus.setText("Account created");break;
            case 2: lblStatus.setText("Password must be between 4-15 characters");break;
            case 3: lblStatus.setText("Type something into email"); break;
            case 4: lblStatus.setText("Not an valid email"); break;
            case 5: lblStatus.setText("Not a valid phone number"); break;
        }
    }

    @FXML private void handleButtonGoBack(ActionEvent event) {
        sw.loginSignupSceneSwitcher(event, "Login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
