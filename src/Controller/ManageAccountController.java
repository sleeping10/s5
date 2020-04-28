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
    TextField nameT;
    @FXML
    TextField emailT;
    @FXML
    TextField passwordT;
    @FXML
    TextField phoneT;
    @FXML
    Text text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrent();

    }

    public void setCurrent() {
        DBC.getInstance().connect();

        nameT.setPromptText("current");
        emailT.setPromptText("current");
        phoneT.setPromptText("current");

        /* her we are going to get current account information and place then on the textField
         * so that the user can see in PromptText*/

        DBC.getInstance().disconnect();

    }
    @FXML
    public void verifyInfo(){
        DBC.getInstance().connect();
        if (DBC.getInstance().verifyAccount("", emailT.getText(), phoneT.getText())){
            System.out.println("finns");
        }else System.out.println("finns inte ");
        DBC.getInstance().disconnect();

    }
}
