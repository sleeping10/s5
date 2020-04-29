package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ResetPass implements Initializable {

    @FXML private Button generateButton;
    @FXML private Label messageLabel;
    @FXML private TextField username;
    @FXML private PasswordField passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void generateButton() {

        if(username.equals(" ") ){ //vill skapa en if-statement som jämför username från db med username som användaren lägger till ??
            Random random = new Random();
            int randomNumber = random.nextInt(999999);
            messageLabel.setText(String.valueOf(randomNumber));
            System.out.println("Generated number : " + randomNumber);
        }else{
            System.out.println("Invalid Username");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(" username not correct ...");
            alert.showAndWait();
        }




    }

    @FXML
    public void resetButton() {

        if (passwordField.equals(messageLabel)){
            //vill ändra det till databasen

        }else{
            System.out.println("Invalid");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("password does not match the given one...");
            alert.showAndWait();

        }


    }
    @FXML
    public void backButton(ActionEvent event) throws IOException {

        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
}

