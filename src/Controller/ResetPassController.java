package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBC;
import sample.PasswordEncryption;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ResetPassController implements Initializable {


    @FXML
    private TextField tfEmail;
    @FXML
    private TextField pfPass;
    @FXML
    private TextField tfNewPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    private void generateButton() {

        System.out.println("Preparing to send Email...");

        final String userName = "projektkurs2hkr@gmail.com";
        final String password = "Swagyolo123";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "587");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tfEmail.getText()));
            message.setSubject("HKR Car Wash & Service - Forgot password");

            if (DBC.getInstance().checkEmailDB(tfEmail.getText())) {
                Random random = new Random();
                int randomNumber = random.nextInt(999999);
                System.out.println("Generated number : " + randomNumber);
                message.setText("Your new recovery code is = " + randomNumber);

                Transport.send(message);
                System.out.println("Email sent successfully...");
                DBC.getInstance().setRecoveryCode(randomNumber, tfEmail.getText());
            } else {
                System.out.println("Invalid Email");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Email not correct ...");
                alert.showAndWait();

            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void resetButton() {


        String recoveryCode = DBC.getInstance().getRecoveryCode(tfEmail.getText());
        System.out.println(recoveryCode);
        if (!tfNewPassword.getText().isEmpty() && pfPass.getText().equals(recoveryCode)) {
            String salt = PasswordEncryption.generateSalt(5);
            String hash = PasswordEncryption.hashPassword(tfNewPassword.getText(), salt) + "-" + salt;
            DBC.getInstance().setRecoveryPassword(hash, tfEmail.getText());

        } else {
            System.out.println("Invalid");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The recovery code does not match the one provided...");
            alert.showAndWait();

        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
}

