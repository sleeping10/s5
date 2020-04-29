package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Verification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Verification implements Initializable {

    @FXML private TextField tfUser;
    @FXML private PasswordField pfPass;
    @FXML private Button btnLogin;
    @FXML private CheckBox chbRemember;
    @FXML private Button btnForgotPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            tfUser.setStyle("-fx-text-inner-color :#a0a2ab");
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
        if (verifyAccount(tfUser.getText(),pfPass.getText(),null)){
            SceneChanger(event, "Main");
        }
        else{
            System.out.println("wrong user or pw");
        }

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
}
