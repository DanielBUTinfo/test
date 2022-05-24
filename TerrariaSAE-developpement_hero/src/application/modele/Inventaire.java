package application.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventaire {
    private ObservableList<Item> items;

    public Inventaire(){
        this.items = FXCollections.observableArrayList();
    }

    public ObservableList<Item> getInventaire(){
        return this.items;
    }

    public int nbItem(){
        return this.items.size();
    }

    public void ajouterItem(Item i){
        items.add(i);
    }

    public void supprimerItem(Item i){
        items.remove(i);
    }
}
