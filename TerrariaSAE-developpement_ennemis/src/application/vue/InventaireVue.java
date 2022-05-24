package application.vue;


import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class InventaireVue{

    private StackPane stackpane;
    private Image arme;
    private ImageView imageView;

    private Pane pane;
    public InventaireVue(StackPane stackpane) {
        this.stackpane = stackpane;
        for(int i = 1;i<6;i++){
            String image = "images/armes/Sword "+i+".png";
            afficherArme(image,i);
        }

    }

    public void afficherInventaire(){
        ObservableList<Node> childs = stackpane.getChildren();

        if (childs.size() > 1) {
            // Top Component
            Node topNode = childs.get(childs.size() - 1);
            System.out.println(childs.get(1));
            topNode.toBack();

        }
    }

    public void afficherArme(String emplacementImage,int indexArme){
        Image armeTest = new Image(emplacementImage);
        //Image armeTest = new Image("images/Sword 1.png");
        Rectangle rectangle = (Rectangle) ((Pane)stackpane.getChildren().get(0)).getChildren().get(indexArme);
        rectangle.setFill(new ImagePattern(armeTest));
        rectangle.setOpacity(0.5);
    }
}


