package tott.pendu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BSTInterfaceController {
    @FXML
    private TextArea treeDisplay;
    @FXML
    private TextField addWordField;
    @FXML
    private TextField searchWordField;
    @FXML
    private TextField deleteWordField;

    private BinarySearchTree binarySearchTree = new BinarySearchTree();

    @FXML
    public void initialize() {
        // Optionally, initialize your tree or UI here if needed
    }

    @FXML
    protected void handleAddAction(ActionEvent event) {
        String wordToAdd = addWordField.getText();
        binarySearchTree.ajouterMot(wordToAdd);
        updateTreeDisplay();
        addWordField.clear(); // Clear the input field after adding
    }

    @FXML
    protected void handleSearchAction(ActionEvent event) {
        // Implement search logic if needed
    }

    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        String wordToDelete = deleteWordField.getText();
        try {
            binarySearchTree.supprimerMot(wordToDelete);
            updateTreeDisplay();
            deleteWordField.clear(); // Clear the input field after deleting
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, maybe show an error message to the user
        }
    }

    private void updateTreeDisplay() {
        // Use the afficher method that returns the String representation of the tree
        String treeStructure = binarySearchTree.afficher();
        treeDisplay.setText(treeStructure);
    }
}
