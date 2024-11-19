package tott.pendu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeController {

    @FXML
    public void chooseEasyMode(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Game Mode");
        window.setScene(new Scene(parent, 800, 650));
        window.show();
    }

    @FXML
    public void chooseHardMode(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameScene2.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Game Mode");
        window.setScene(new Scene(parent, 800, 650));
        window.show();
    }
    @FXML
    public void checkBinaryTree(ActionEvent event) throws IOException {
        // Load the Binary Search Tree Interface from FXML file
        Parent bstInterfaceRoot = FXMLLoader.load(getClass().getResource("BSTInterface.fxml")); // replace with your actual path
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Binary Search Tree Interface");
        window.setScene(new Scene(bstInterfaceRoot, 800, 650)); // you can adjust the size as needed
        window.show();
    }



}
