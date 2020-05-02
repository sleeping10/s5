package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DBC.getInstance().connect();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Login.fxml"));
        primaryStage.setTitle("HKR Wash & Service");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        primaryStage.show();





    }




    public static void main(String[] args) {

        PasswordEncryption pw = new PasswordEncryption();
        pw.main(args);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (DBC.getInstance().getAccount() != null){
                    DBC.getInstance().setLoginStatus(false);
                }
                DBC.getInstance().disconnect();
            }
        });

    launch(args);
    }
}
