package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Terrain {

	private ObservableList<Integer> tuiles;
    String emplacementFichier;

    public Terrain(String emplacementFichier) {
        this.emplacementFichier = emplacementFichier;
        this.tuiles= FXCollections.observableArrayList();
        this.lireMap(25, 50);
//        this.tuiles = lireMap(20, 25);
    }
//dsqgfqsdfqsf
    public int codeTuile(int indiceTuile) {
        return tuiles.get(indiceTuile);
    }

    public int nbTuiles() {
        return tuiles.size();
    }


    public void lireMap(int longueur, int largeur) {
        File map = new File(this.emplacementFichier);
        try {
            FileReader fileReader = new FileReader(map);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int compteur = 0;
            for (int i = 0; i < longueur; i++) {
                String line = bufferedReader.readLine();
                String[] numbers = line.split(",");
                for (int j = 0; j < largeur; j++) {
                    String number = numbers[j];
                    this.tuiles.add(Integer.parseInt(number));
                    compteur++;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.printf("fichier pas trouvé");
        } catch (IOException e) {
            System.err.printf("Une ligne n'a pas peut être lu");
        }
    }

    public ObservableList<Integer> getTuiles() {
        return tuiles;
    }


    public void enleveTuile(int indiceTuile) {
        this.tuiles.set(indiceTuile,178);
    }

    public void ajoutTuile(int indiceTuile) {
        this.tuiles.set(indiceTuile,2);
    }



}
