package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login extends Verification {

    //This class must be a controller
    //för att det här ska fungera så som jag tänkt behöver jag skapa klasserna :
    // DBHandler, DBConnection och Configs

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private CheckBox remember;

    @FXML
    private Button forgetPassword;


    private Connection connection;

    // a class named DBHandler:
    //private DBHandler handler;
    private PreparedStatement pst;

    //detta är fel då klassen just nu inte är en Controller

    //@Override
//    public void initialize(URL url, ResourceBundle rb) {
//
//        username.setStyle("-fx-text-inner-color :#a0a2ab");
//        password.setStyle("-fx-text-inner-color :#a0a2ab");

     //   handler = new DBHandler();

//    }

    @FXML
    public void loginAction(ActionEvent event) throws SQLException {


        // Detta här under ska vi försöka få in i DBC klassen, den ska innehålla alla databas connections
        //Login Database
        try {

          //  connection = handler.getConnection();
            String st = "select * from carusers where name = ? and password =? ";
            pst = connection.prepareStatement(st);
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());
            ResultSet rs = pst.executeQuery();

            int count = 0;
            while(rs.next()){
                count = count +1;

            }
            if(count == 1){
                System.out.println("login is ok ");
                login.getScene().getWindow().hide();
                Stage home = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/HomePage.fxml")); //ba skrev ngt här sålänge
                Scene scene = new Scene(root);
                home.setScene(scene);
                home.show();


            }else {
                System.out.println("Error username or password not correct");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(" username or password not correct ...");
                alert.showAndWait();

            }

        } catch (Exception e) {
        }
        finally{
            connection.close();
        }

    }

    @FXML
    public void signUpAction(ActionEvent event) {
        try {

            Node node = (Node)event.getSource();
            Stage stage = (Stage)node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/SignUp.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (Exception e) {
            System.out.println(" Error signup : " + e);
        }
    }
}


