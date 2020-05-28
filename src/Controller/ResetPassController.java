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


        // JavaFX Tooltip-klass visar en liten popup med förklarande text när användaren svävar musen över en JavaFX-kontroll
        // Skapar Tooltip objektet
        final Tooltip tooltipEmail = new Tooltip();
        // Texten som ska visas när musen svävas över JavaFX-kontrollen
        tooltipEmail.setText("Enter the email used when signing up");
        // Här Specifieras det över vilken kontroll som texten skall visas
        tfEmail.setTooltip(tooltipEmail);


        final Tooltip tooltipRecoveryPass = new Tooltip();
        tooltipRecoveryPass.setText("Enter the recovery code provided on your email");
        pfPass.setTooltip(tooltipRecoveryPass);


        final Tooltip tooltipNewPass = new Tooltip();
        tooltipNewPass.setText("Create a new password");
        tfNewPassword.setTooltip(tooltipNewPass);

    }

    @FXML
    private void generateButton() {

        // Först och främst fick jag ladda ner Java Mail API och activation framework på datorn och sen lägga till den i libray.
        // Sen så skapa jag ett Gmail konto endast för projektets skull.För att kunna skicka mail från java
        // var jag tvungen att att ändra säkerhetsintällningen på Gmail till att tillåta åtkomst till mindre säkra appar.

        System.out.println("Preparing to send Email...");

        // Avsändarens e-post-ID
        final String userName = "projektkurs2hkr@gmail.com";
        // Avsändarens e-post lösen
        final String password = "Swagyolo123";

        // Vi måste använda Property systemet/objektet för att konfigurera e-postservern
        Properties prop = new Properties();
        // Det finns olika sätt att skicka e-post med JavaMail API.
        // För just det här sättet måste vi ha en SMTP-server som är ansvarig för att skicka e-post (gratis-server från google som hämtas från nätet)
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "587");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Här skapar vi standardobjektet Session.
        // Kollar om eposten och lösenordet är korrekt (skriver du in fel så kraschar programmet bara)
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        // ERROR HANDLING
        try {
            // Här skapar jag ett standard MimeMessage-objekt som tar Session som parameter
            Message message = new MimeMessage(session);
            // Avsändarens epost
            message.setFrom(new InternetAddress(userName));
            // Mottagarens epost, vilket då är den användaren anger
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tfEmail.getText()));
            // Här skriver vi ämnet på mailet
            message.setSubject("HKR Car Wash & Service - Forgot password");

            // If-Statement för att kontrollera om e-postmeddelandet från användaren är samma som det som angivits när kontot skapats
            if (DBC.getInstance().checkEmailDB(tfEmail.getText())) {
                // Här skapar jag ett Random objekt för att skapa ett slumpmässigt återställningsnummer åt användaren
                Random random = new Random();
                int randomNumber = random.nextInt(999999);
                System.out.println("Generated number : " + randomNumber);
                // Själva innehållet på mailet som användaren får
                message.setText("Your new recovery code is = " + randomNumber);

                // För att kunna skicka mailet behöver vi Transport klassen
                Transport.send(message);
                System.out.println("Email sent successfully...");
                // Skriver den unika återställningskoden till databasen
                DBC.getInstance().setRecoveryCode(Integer.parseInt(String.valueOf(randomNumber)), tfEmail.getText());
            } else {
                System.out.println("Invalid Email");
                // Enkelt varningsmeddelande om villkoren för If-statments inte är uppfyllda
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

        // Hämtar återställningskoden från databasen
        String recoveryCode = DBC.getInstance().getRecoveryCode(tfEmail.getText());
        System.out.println(recoveryCode);
        // If-statement för att se till att återställningskoden skriven av användaren är densamma som den som
        // angivits på mail och om det nya lösenordsfältet inte är tomt
        if (!tfNewPassword.getText().isEmpty() && pfPass.getText().equals(recoveryCode)) {
            // Krypterar det nya lösenordet som skapats
            String salt = PasswordEncryption.generateSalt(5);
            String hash = PasswordEncryption.hashPassword(tfNewPassword.getText(), salt) + "-" + salt;
            // Ställer in det nya lösenordet i databasen
            DBC.getInstance().setRecoveryPassword(hash, tfEmail.getText());
        } else {
            System.out.println("Invalid");
            // Enkelt varningsmeddelande om villkoren för If-statments inte är uppfyllda
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The recovery code does not match the one provided...");
            alert.showAndWait();

        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {

        // Koden för att återvända till inloggningssidan
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
}

