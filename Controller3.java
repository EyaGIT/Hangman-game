package tott.pendu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller3 {
    @FXML ImageView img;
    Image image5 = new Image(getClass().getResourceAsStream("images/1111.jpg"));
    Image image6 = new Image(getClass().getResourceAsStream("images/111.jpg"));
    Image image7 = new Image(getClass().getResourceAsStream("images/7.png"));

    @FXML TextField tf1;
    @FXML TextField tf2;
    @FXML TextField tf3;
    @FXML TextField tf4;
    @FXML TextField tf5;
    @FXML TextField tf6;
    @FXML TextField tf7;
    @FXML TextField tf8;
    @FXML TextField input;
    @FXML Label hint;
    @FXML Label letter_count;

    List<String> words = new ArrayList<>();
    int random;
    int letter_size;

    private int life = 3; // Change life to 3

    public Controller3() {
        readWordsFromFile();
        random = new Random().nextInt(words.size());
        String word_hint = words.get(random);
        String[] split = word_hint.split(" ", 2);
        letter_size = split[0].length();
    }

    private void readWordsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\IdeaProjects\\Pendu\\src\\main\\java\\tott\\pendu\\dictarbre.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        setHint();
    }

    public void setHint() {
        hint.setText(words.get(random));
        letter_count.setText(letter_size + " Letters");
        // Adjust visibility based on the letter size
        tf8.setVisible(letter_size >= 8);
        tf7.setVisible(letter_size >= 7);
        tf6.setVisible(letter_size >= 6);
        tf5.setVisible(letter_size >= 5);
    }

    public void CheckInput() {
        String userInput = input.getText();
        String targetWord = words.get(random);

        List<Integer> positions = findCharacterPositions(targetWord, userInput);

        for (int index : positions) {
            setLetter(index, userInput);
        }

        if (positions.isEmpty()) {
            setImage();
            System.out.println("La lettre " + userInput + " n'existe pas dans le mot.");
        }

        input.clear();
    }

    private List<Integer> findCharacterPositions(String targetWord, String userInput) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < targetWord.length(); i++) {
            if (targetWord.charAt(i) == userInput.charAt(0)) {
                positions.add(i);
            }
        }
        return positions;
    }

    public void setLetter(int index, String str) {
        switch (index) {
            case 0: tf1.setText(str); break;
            case 1: tf2.setText(str); break;
            case 2: tf3.setText(str); break;
            case 3: tf4.setText(str); break;
            case 4: tf5.setText(str); break;
            case 5: tf6.setText(str); break;
            case 6: tf7.setText(str); break;
            case 7: tf8.setText(str); break;
        }
    }

    public void setImage() {
        switch (life) {
            case 3: img.setImage(image5); break;
            case 2: img.setImage(image6); break;
            case 1: img.setImage(image7); break;
        }
        life--;
        if(life == 0) {
            // Handle game over
        }
    }

    public void changeScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameScene2.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Hangman Game");
        window.setScene(new Scene(parent, 800, 650));
        window.show();
    }
}
