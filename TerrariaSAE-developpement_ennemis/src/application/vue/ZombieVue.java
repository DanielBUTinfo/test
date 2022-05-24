package application.vue;

import application.modele.Loup;
import application.modele.Zombie;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ZombieVue {
    private BorderPane map;
    private Zombie zombie;
    private Image imgZombie;
    private ImageView imageView;
    private DoubleProperty orientation;


    public ZombieVue(BorderPane map, Zombie zombie) {
        this.map = map;
        this.zombie = zombie;
        this.imgZombie = new Image("images/ennemis/zombies.png");
        this.imageView = new ImageView(imgZombie);
        imageView.translateXProperty().bind(zombie.getxProperty());
        imageView.translateYProperty().bind(zombie.getyProperty());
        imageView.setId(zombie.getId());
        this.imageView.scaleXProperty().bind(zombie.getOrientation());
        this.afficherLoup();
    }

    private void afficherLoup() {

        int largeurImage = (int)imgZombie.getHeight();
        int hauteurImage = (int)imgZombie.getWidth();
        Rectangle2D tuile = new Rectangle2D(0,0,32,50);
        this.imageView.setViewport(tuile);
        map.getChildren().add(this.imageView);


    }
}
