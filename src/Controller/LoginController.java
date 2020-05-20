package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Verification implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPass;
    @FXML
    private CheckBox chbRemember;
    @FXML
    private Label lblStatus;

    private SceneSwitcher sw = new SceneSwitcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkRememberMe();
        tfEmail.setStyle("-fx-text-inner-color :#a0a2ab");
        pfPass.setStyle("-fx-text-inner-color :#a0a2ab");

        final Tooltip tooltipEmail = new Tooltip();
        tooltipEmail.setText("Enter Email");
        tfEmail.setTooltip(tooltipEmail);

        final Tooltip tooltipPass = new Tooltip();
        tooltipPass.setText("Enter Password");
        pfPass.setTooltip(tooltipPass);
    }

    @FXML
    public void handleSignUpBtn(ActionEvent event) {
        sw.loginSignupSceneSwitcher(event, "SignUp");
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        handleLoginBtn(ae);
    }

    @FXML
    public void handleLoginBtn(ActionEvent event) {
        login(event);
        if (chbRemember.isSelected()) {
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("../s5/rememberme.bin");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                if (DBC.getInstance().getAccount() != null) {
                    out.writeObject(new Account(0,
                            DBC.getInstance().getAccount().getEmail(),
                            pfPass.getText(), null, null, 0));
                }
                out.close();
                fileOut.close();
                System.out.println("DEBUG: Saved Remember Me Data");
            } catch (IOException i) {
                i.printStackTrace();
            }
        } else {
            try {
                File f = new File("../s5/rememberme.bin");
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void forgotPassButton(ActionEvent event) throws IOException {
        //Change this...
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ResetPass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void handleRememberMeChb() {
        if (chbRemember.isSelected()) {
            lblStatus.setText("Email & pw will be saved for next session");
        } else {
            lblStatus.setText("Saved Email & pw will be deleted for next session");
        }
    }

    private void login(ActionEvent e) {
        switch (verifyAccount(tfEmail.getText(), pfPass.getText(), null)) {
            case 0:
                lblStatus.setText("Email or Pass is wrong or user already logged in");
                break;
            case 1:
                sw.loginSignupSceneSwitcher(e, "Main");
                break;
            case 2:
                lblStatus.setText("Password must be between 4-15 characters");
                break;
            case 3:
                lblStatus.setText("Email or password is empty");
                break;
            case 4:
                lblStatus.setText("Input is not a valid email");
                break;
        }
    }

    private void checkRememberMe() {
        Account tmpAcc;
        try {
            FileInputStream fileIn =
                    new FileInputStream("../s5/rememberme.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tmpAcc = (Account) in.readObject();
            in.close();
            fileIn.close();
            tfEmail.setText(tmpAcc.getEmail());
            pfPass.setText(tmpAcc.getPassword());
            chbRemember.setSelected(true);
            System.out.println("DEBUG: Remember me info loaded");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
