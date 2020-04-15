package sample;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUp extends Verification implements Initializable {

    public void signup(String email, String pass, String phoneNr, String name){

        if (verifyUser(email, pass) && verifyDetails(phoneNr, name)){
            //create new account here
        }else{
            //Error, either wrong input or already exist
        }

    }

    private boolean verifyDetails(String phoneNr, String name){
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //sign up class
}
