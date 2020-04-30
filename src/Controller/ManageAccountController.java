package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.DBC;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageAccountController implements Initializable {

    @FXML
    TextField name;
    @FXML
    TextField phone;
    @FXML
    TextField password;
    @FXML
    TextField npassword;
    @FXML
    TextField rnpassword;
    @FXML
    Text text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrent();

    }

    public void setCurrent() {
        DBC.getInstance().connect();

        name.setPromptText("current");
        phone.setPromptText("current");

        /* her we are going to get current account information and place then on the textField
         * so that the user can see in PromptText*/

        DBC.getInstance().disconnect();

    }


    @FXML
    public String verifyNewPassword() {
        final String regex = "^([a-öA-Ö0-9@*#]{8,15})$";
        if (!npassword.getText().isEmpty() && !rnpassword.getText().isEmpty()) {
            if (password.getText().matches(DBC.getInstance().getAccountObject().getPassword())) { //fix so that this check current match:
                if (npassword.getText().matches(regex) && npassword.getText().matches(rnpassword.getText())) {
                    System.out.println(npassword.getText());
                    return npassword.getText();
                }
                System.out.println("fel");
            } else System.out.println("password dosent match");
        }
        return null;


    }
}
