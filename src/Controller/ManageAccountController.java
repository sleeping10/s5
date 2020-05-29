package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import sample.Account;
import sample.DBC;
import sample.PasswordEncryption;
import sample.Verification;

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

        final Tooltip tooltipName = new Tooltip();
        tooltipName.setText("Edit your name");
        textFieldName.setTooltip(tooltipName);

        final Tooltip tooltipPhone = new Tooltip();
        tooltipPhone.setText("Number needs to be 10 digits");
        textFieldPhone.setTooltip(tooltipPhone);

        final Tooltip tooltipCurrentPass = new Tooltip();
        tooltipCurrentPass.setText("Enter current password");
        textFieldPassword.setTooltip(tooltipCurrentPass);

        final Tooltip tooltipPass = new Tooltip();
        tooltipPass.setText("Finalize your booking");
        textFieldNPassword.setTooltip(tooltipPass);

        final Tooltip tooltipPassRepeat = new Tooltip();
        tooltipPassRepeat.setText("Enter your new password again");
        textFieldRNPassword.setTooltip(tooltipPassRepeat);

    }

    public void setCurrent() {
        textFieldName.setPromptText(DBC.getInstance().getCurrentAcc().getName());
        textFieldPhone.setPromptText(DBC.getInstance().getCurrentAcc().getPhoneNr());
    }

    public void saveB() {
        setCurrent();
        update();

    }

    public void update() {

        String part1 = "", part2 = "";
        try {
            String[] parts = DBC.getInstance().getCurrentAcc().getPassword().split("-");
            part1 = parts[0];
            part2 = parts[1];
        }catch (Exception e){
            System.out.println("nothing");
        }

        String name = "", pass = "", phone = "";
        boolean checkPass = false;
        if (textFieldName.getText().isEmpty()) {
            name = DBC.getInstance().getCurrentAcc().getName();
        } else name = textFieldName.getText();
        if (PasswordEncryption.verifyPassword(textFieldPassword.getText(), part1, part2)){
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
            phone = DBC.getInstance().getCurrentAcc().getPhoneNr();
        } else {
            if (verifyPhone()) {
                if (DBC.getInstance().checkPhoneNumber(textFieldPhone.getText())) {
                    phone=textFieldPhone.getText();

                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Phone number already taken");
                    alert.setContentText("please try again");
                    alert.showAndWait();
                    checkPass=true;


                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("wrong phone format.");
                alert.setContentText("the right (XXXXXXXXXX)\n please try again.");
                alert.showAndWait();
                checkPass=true;
            }
        }

        if (!pass.matches("1") && !checkPass) {
            String salt = PasswordEncryption.generateSalt(5);
            String hashed = PasswordEncryption.hashPassword(pass, salt) + "-" + salt;
            DBC.getInstance().updateAccount(new Account(DBC.getInstance().getCurrentAcc().getAccountID(),
                    DBC.getInstance().getCurrentAcc().getEmail(), hashed,
                    name, phone, DBC.getInstance().getCurrentAcc().getAccessType()));
            text.setText("saved!");
        }
    }


    @FXML
    public String verifyNewPassword() {
        final String regex = "^([a-öA-Ö0-9@*#]{4,15})$";
        if (!textFieldNPassword.getText().isEmpty() && !textFieldRNPassword.getText().isEmpty()) { // kollar om du vill ändara lösenord
            if (textFieldNPassword.getText().matches(textFieldRNPassword.getText())) {
                if (textFieldNPassword.getText().matches(regex) && textFieldRNPassword.getText().matches(regex)) {//kolla om det nya lösenordet matchar regex och andra gången du skriver in lösenordet
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
                    return DBC.getInstance().getCurrentAcc().getPassword();
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

    public boolean verifyPhone() {
        //verifies if the phone number you are putting in is in the right format
        return Verification.validatePhone(textFieldPhone.getText());

    }


}