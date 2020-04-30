package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Account;
import sample.DBC;
import sample.Verification;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Verification implements Initializable {

    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPass;
    @FXML private Button btnLogin;
    @FXML private CheckBox chbRemember;
    @FXML private Button btnForgotPass;
    @FXML private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Account tmpAcc = null;
        try{
            FileInputStream fileIn =
                    new FileInputStream("../s5/rememberme.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tmpAcc = (Account)in.readObject();
            in.close();
            fileIn.close();
            tfEmail.setText(tmpAcc.getEmail());
            pfPass.setText(tmpAcc.getPassword());
            chbRemember.setSelected(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("DEBUG: Remember me file not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(tmpAcc.getEmail());

        tfEmail.setStyle("-fx-text-inner-color :#a0a2ab");
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
        login(event);
        if (chbRemember.isSelected()){
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("../s5/rememberme.bin");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(DBC.getInstance().getAccount());
                out.close();
                fileOut.close();
                System.out.println("DEBUG: Saved Remember Me Data");
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        DBC.getInstance().setTfUser(tfEmail.getText());
        DBC.getInstance().setPfPass(pfPass.getText());

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

    @FXML
    public void handleRememberMeChb(ActionEvent event){
        lblStatus.setText("Email & pw will be saved for next session");
    }

    private void login(ActionEvent e){
        switch (verifyAccount(tfEmail.getText(), pfPass.getText(), null)) {
            case 0: lblStatus.setText("Email or Pass is wrong");break;
            case 1: SceneChanger(e, "Main");break;
            case 2: lblStatus.setText("Password must be between 4-8 characters");break;
            case 3: lblStatus.setText("Email or password is empty"); break;
            case 4: lblStatus.setText("Input is not a valid email"); break;
        }
    }
}
