package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    public void saveB() {
        setCurrent();
        update();
    }

    public void update() {

        String name = "", pass = "", phone = "";
        boolean checkPass = false;
        if (textFieldName.getText().isEmpty()) {
            name = DBC.getInstance().getAccount().getName();
        } else name = textFieldName.getText();
        if (textFieldPassword.getText().matches(DBC.getInstance().getAccount().getPassword())) {
            pass = verifyNewPassword();
        } else {
            System.out.println("Wrong password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong password");
            alert.setContentText("please input your current password\n on password field. ");
            alert.showAndWait();
            checkPass = true;
        }
        if ((textFieldPhone.getText().isEmpty() && !checkPass)) {
            phone = DBC.getInstance().getAccount().getPhone();
        } else phone = textFieldPhone.getText();

        if (!pass.matches("1") && !checkPass) {
            DBC.getInstance().updateAccount(name, pass, phone, DBC.getInstance().getAccount().getAccountID());
            text.setText("saved!");
        }
    }


    @FXML
    public String verifyNewPassword() {
        final String regex = "^([a-öA-Ö0-9@*#]{8,15})$";
        if (!textFieldNPassword.getText().isEmpty() && !textFieldRNPassword.getText().isEmpty()) { // kollar om du vill ändara lösenord
            if (textFieldNPassword.getText().matches(textFieldRNPassword.getText())) {
                if (textFieldNPassword.getText().matches(regex) && textFieldRNPassword.getText().matches(regex)) {//kolla om det nya lösenordet matchar regex och andra gången du skriver in lösenordet
                    System.out.println(textFieldNPassword.getText());
                    System.out.println("gick igenom");
                    return textFieldNPassword.getText();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("new password dont match requirements.");
                    alert.setContentText("password must be four to fifteen characters or numbers.");
                    alert.showAndWait();

                }
            } else {
                System.out.println("Wrong password.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("new password filed do not match.");
                alert.setContentText("check new password and repeat  new password.");
                alert.showAndWait();
                return "1";
            }

        } else {
            if (textFieldNPassword.getText().isEmpty()) {
                if (textFieldRNPassword.getText().isEmpty()) {
                    return DBC.getInstance().getAccount().getPassword();
                } else {
                    System.out.println("Wrong password.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("repeat new password do not match new password.");
                    alert.setContentText("check new password and repeat  new password .");
                    alert.showAndWait();
                    return "1";
                }
            } else {
                System.out.println("Wrong password");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("new password  do not match repeat new password.");
                alert.setContentText("check new password and repeat  new password. ");
                alert.showAndWait();
                return "1";
            }

        }


        return "1";
    }


}