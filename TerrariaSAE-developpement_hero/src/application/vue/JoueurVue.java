package application.vue;

import application.modele.Acteur;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class JoueurVue {
    private BorderPane map;
    private Acteur joueur;
    private Image perso;
    private ImageView imageView;
    private DoubleProperty orientation;
    public JoueurVue(BorderPane map, Acteur joueur) {
        this.map = map;
        this.joueur = joueur;
        this.perso = new Image("images/perso.png");
        this.imageView = new ImageView(perso);
        imageView.translateXProperty().bind(joueur.getxProperty());
        imageView.translateYProperty().bind(joueur.getyProperty());
        imageView.setId(joueur.getId());
        this.imageView.scaleXProperty().bind(joueur.getOrientation());
        this.afficherJoueur();
    }


    private void afficherJoueur() {
    	int largeurImage = (int)perso.getHeight();
        int hauteurImage = (int)perso.getWidth();
        Rectangle2D tuile = new Rectangle2D(0,0,32,32);
        this.imageView.setViewport(tuile);
        map.getChildren().add(this.imageView);

        
    }

    public void supprimerJoueur() {
        map.getChildren().remove(map.lookup('#'+joueur.getId()));

    }
}