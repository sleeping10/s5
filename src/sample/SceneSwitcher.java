package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import Controller.MainScreenController;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public void mainScreenSceneSwitcher(AnchorPane anchorInfo, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/" + fxml + ".fxml"));
        Pane newPane = fxmlLoader.load();
        try {
            anchorInfo.getChildren().clear();
            anchorInfo.getChildren().add(newPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginSignupSceneSwitcher(ActionEvent event, String fxml){
        try{
            Node node = (Node)event.getSource();
            Stage stage = (Stage)node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/" + fxml + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e){

        }
    }
}
