package Controller;

import com.mysql.cj.jdbc.JdbcConnection;
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
import sample.DBC;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class ResetPassController implements Initializable {

    @FXML private Button btnGenerate;
    @FXML private Label lblMessage;
    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPass;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void generateButton() {

        if (DBC.getInstance().checkEmailDB(tfEmail.getText())){
            Random random = new Random();
            int randomNumber = random.nextInt(999999);
            lblMessage.setText(String.valueOf(randomNumber));
            System.out.println("Generated number : " + randomNumber);
        }
        else{
            System.out.println("Invalid Email");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Email not correct ...");
            alert.showAndWait();

        }




    }

    @FXML
    public void resetButton() {

        if (pfPass.equals(lblMessage)){
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

