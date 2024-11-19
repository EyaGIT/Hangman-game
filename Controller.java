package tott.pendu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {

    @FXML
    public void changeScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Game Scene");
        window.setScene(new Scene(parent, 800, 650));
        window.show();
    }
    @FXML
    public void changeMode(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Mode.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Game Mode");
        window.setScene(new Scene(parent, 800, 650));
        window.show();
    }



}
