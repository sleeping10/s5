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


        //primaryStage.setOnCloseRequest(DBC.getInstance().disconnect());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
