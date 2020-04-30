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
import sample.Verification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Verification implements Initializable {

    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPass;
    @FXML private Button btnLogin;
    @FXML private CheckBox chbRemember;
    @FXML private Button btnForgotPass;
    @FXML private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            tfEmail.setStyle("-fx-text-inner-color :#a0a2ab");
            pfPass.setStyle("-fx-text-inner-color :#a0a2ab");



            //   handler = new DBHandler();

        }

        private void SceneChanger(ActionEvent event, String fxml){
            try {

                Node node = (Node)event.getSource();
                Stage stage = (Stage)node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/" + fxml + ".fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);

            } catch (Exception e) {
                System.out.println(" Error signup : " + e);
            }

        }



    @FXML
    public void handleSignUpBtn(ActionEvent event) {

        SceneChanger(event, "SignUp");
    }

    @FXML
    public void handleLoginBtn(ActionEvent event) {
        login(event);
        DBC.getInstance().setTfUser(tfEmail.getText());
        DBC.getInstance().setPfPass(pfPass.getText());

    }
    @FXML
    public void forgotPassButton(ActionEvent event) throws IOException {

        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ResetPass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void login(ActionEvent e){
        switch (verifyAccount(tfEmail.getText(), pfPass.getText(), null)) {
            case 0: lblStatus.setText("Email or Pass is wrong");break;
            case 1: SceneChanger(e, "Main");break;
            case 2: lblStatus.setText("Password must be between 4-8 characters");break;
            case 3: lblStatus.setText("Email or password is empty"); break;
            case 4: lblStatus.setText("Input is not a valid email"); break;
        }
    }

    public void createNewAccObject(){

    }
}
