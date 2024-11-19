package tott.pendu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BinarySearchTree {
    private BSTNode racine;

    public BinarySearchTree() {
        this.racine = new BSTNode('\0');
    }

    // Add a word to the tree:
    public void ajouterMot(String mot) {
        ajouterMotRecursively(racine, mot, 0);
    }
    private BSTNode findChild(BSTNode parent, char targetChar) {
        BSTNode current = parent.gauche;

        while (current != null) {
            if (current.lettre == targetChar) {
                // Le nœud avec le caractère cible a été trouvé
                return current;
            }
            current = current.droit;
        }

        // Aucun enfant correspondant n'a été trouvé
        return null;
    }


    private void ajouterMotRecursively(BSTNode noeud, String mot, int index) {

        if (index == mot.length()) {
            // Mark the last character of a word with '\0'
            BSTNode endOfWordMarker = new BSTNode('\0');
            noeud.gauche = endOfWordMarker;
            return;
        }

        char currentChar = mot.charAt(index);
        BSTNode child = findChild(noeud, currentChar);

        if (child == null) {
            // Si un nœud pour le caractère actuel n'existe pas, le créer
            child = new BSTNode(currentChar);

            // Insérer le nouveau nœud dans l'ordre
            if (noeud.gauche == null || currentChar < noeud.gauche.lettre) {
                child.droit = noeud.gauche;
                noeud.gauche = child;
            } else {
                BSTNode current = noeud.gauche;

                while (current.droit != null && current.droit.lettre < currentChar) {
                    current = current.droit;
                }

                child.droit = current.droit;
                current.droit = child;
            }
        }

        // Continue with the existing or newly created child
        ajouterMotRecursively(child, mot, index + 1);
    }
    public void construireArbreDepuisFichier(String nomFichier) {


        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String mot;
            while ((mot = br.readLine()) != null) {
                ajouterMot(mot.trim()); // Ajouter chaque mot à l'arbre
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception selon vos besoins
        }
    }
    // Display the tree
    public String afficher() {
        StringBuilder sb = new StringBuilder();
        afficherArbreString(racine, 0, sb);
        return sb.toString();
    }

    // New method to build string representation
    private void afficherArbreString(BSTNode noeud, int niveau, StringBuilder sb) {
        if (noeud != null) {
            afficherArbreString(noeud.gauche, niveau + 1, sb);
            for (int i = 0; i < niveau; i++) {
                sb.append("   ");
            }
            sb.append(noeud.lettre).append("\n");
            afficherArbreString(noeud.droit, niveau, sb);
        }
    }




    public List<Integer> findcaracterposistion2(String word, String str) {
        List<Integer> positions = new ArrayList<>();
        BSTNode current = racine.gauche;
        int index = 0;

        for (char lettre : word.toCharArray()) {
            while (current != null && current.lettre != lettre) {
                current = current.droit;
            }
            if (lettre == str.charAt(0)) {
                positions.add(index);
            }
            index++;
            // reset current to the start of the tree for the next letter.
            current =current.gauche;
        }
        return positions;
    }

    // Search if a word exists in the tree
    public boolean motExiste(String mot) {
        BSTNode current = racine.gauche;
        for (char lettre : mot.toCharArray()) {
            while (current != null && current.lettre != lettre) {
                current = current.droit;
            }
            if (current == null) {
                return false;
            }
            current = current.gauche;
        }
        return current != null && current.lettre == '\0';
    }

    public void rechercherMot(String mot) {
        boolean existe = motExiste(mot);
        if (existe) {
            System.out.println("Le mot '" + mot + "' existe dans le dictionnaire.");
        } else {
            System.out.println("Le mot '" + mot + "' n'existe pas dans le dictionnaire.");
        }
    }

    // Inside your Arbre class
    public boolean supprimerMot(String mot) throws IOException {
        // Commencez par un indicateur qui vérifie si la suppression est réussie
        boolean[] isDeleted = new boolean[1];
        isDeleted[0] = false; // La valeur par défaut est false

        racine.gauche = supprimerMotRecursively(racine.gauche, mot, 0, isDeleted);

        if (isDeleted[0]) {
            // Si le mot a été supprimé de l'arbre, supprimez-le également du fichier
            supprimerMotDuFichier(mot);
        }

        return isDeleted[0]; // Cet indicateur sera défini sur true si le mot est supprimé avec succès
    }

    private BSTNode supprimerMotRecursively(BSTNode current, String word, int index, boolean[] isDeleted) {
        if (current == null) {
            // We've reached a leaf, and since we're still looking for the word, we didn't find it
            return null;
        }

        if (index == word.length()) {
            // We're at the end of the word. Now we need to find the end-of-word marker node.
            if (current.lettre == '\0') { // Found the end of the word
                isDeleted[0] = true; // Indicate that we have successfully deleted the word
                return current.droit; // Delete this node by returning its right sibling
            } else {
                // If we haven't found the end of word marker, we just return the current node
                return current;
            }
        }

        char charAtIdx = word.charAt(index);

        if (charAtIdx == current.lettre) {
            // Move to the next character in the word
            current.gauche = supprimerMotRecursively(current.gauche, word, index + 1, isDeleted);

            // If deleted something and the current node is no longer part of another word, delete this node too
            if (isDeleted[0] && current.gauche == null && current.lettre != '\0') {
                return current.droit;
            }
        } else if (charAtIdx < current.lettre) {
            // This branch indicates our word might be in the left subtree (if such structure exists)
            current.gauche = supprimerMotRecursively(current.gauche, word, index, isDeleted);
        } else {
            // This branch indicates our word might be in the right subtree
            current.droit = supprimerMotRecursively(current.droit, word, index, isDeleted);
        }

        // Return the possibly updated current node
        return current;
    }








    public void ajouterMotAuFichier(String mot) throws IOException {

        Files.write(Paths.get("C:\\Users\\user\\IdeaProjects\\Pendu\\src\\main\\java\\tott\\pendu\\dictarbre.txt"),
                (mot + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    // Remove a word from the tree
    public void supprimerMotDuFichier(String mot) throws IOException {
        Path path = Paths.get("C:\\Users\\user\\IdeaProjects\\Pendu\\src\\main\\java\\tott\\pendu\\dictarbre.txt");
        List<String> lines = Files.lines(path).filter(l -> !l.trim().equals(mot)).collect(Collectors.toList());
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public int findLetterPosition(String letter) {
        BSTNode current = racine.gauche;
        int position = -1; // Initialiser à -1 pour commencer à 0
        boolean found = false;

        for (char lettre : letter.toCharArray()) {
            while (current != null) {
                if (current.lettre == lettre) {
                    found = true;
                    break;
                } else if (current.lettre < lettre) {
                    current = current.droit;
                } else {
                    current = current.gauche;
                }
                position++; // Incrémenter la position à chaque itération
            }

            if (!found) {
                return -1; // La lettre n'a pas été trouvée
            }

            found = false;
            current = current.gauche;
        }

        return position;
    }








    public BSTNode getRacine() {
        return racine;
    }

    public void setRacine(BSTNode racine) {
        this.racine = racine;
    }


}