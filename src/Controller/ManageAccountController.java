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
    TextField textFieldName;
    @FXML
    TextField textFieldPhone;
    @FXML
    TextField textFieldPassword;
    @FXML
    TextField textFieldNPassword;
    @FXML
    TextField textFieldRNPassword;
    @FXML
    Text text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrent();

    }

    public void setCurrent() {
        textFieldName.setPromptText(DBC.getInstance().getAccount().getName());
        textFieldPhone.setPromptText(DBC.getInstance().getAccount().getPhone());
    }


    @FXML
    public String verifyNewPassword() {
        final String regex = "^([a-öA-Ö0-9@*#]{8,15})$";
        if (!textFieldNPassword.getText().isEmpty() && !textFieldRNPassword.getText().isEmpty()) {
            if (textFieldPassword.getText().matches(DBC.getInstance().getAccount().getPassword())) { //fix so that this check current match:
                if (textFieldNPassword.getText().matches(regex) && textFieldNPassword.getText().matches(textFieldRNPassword.getText())) {
                    System.out.println(textFieldNPassword.getText());
                    return textFieldNPassword.getText();
                }
                System.out.println("fel");
            } else System.out.println("password dosent match");
        }
        return null;


    }
}
