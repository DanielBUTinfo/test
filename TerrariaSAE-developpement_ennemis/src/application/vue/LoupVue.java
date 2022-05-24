package application.vue;

import application.modele.Loup;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class LoupVue {
    private BorderPane map;
    private Loup loup;
    private Image imgLoup;
    private ImageView imageView;
    private DoubleProperty orientation;


    public LoupVue(BorderPane map, Loup loup) {
        this.map = map;
        this.loup = loup;
        this.imgLoup = new Image("images/ennemis/loup.png");
        this.imageView = new ImageView(imgLoup);
        imageView.translateXProperty().bind(loup.getxProperty());
        imageView.translateYProperty().bind(loup.getyProperty());
        imageView.setId(loup.getId());
        this.imageView.scaleXProperty().bind(loup.getOrientation());
        this.afficherLoup();
    }

    private void afficherLoup() {

        int largeurImage = (int)imgLoup.getHeight();
        int hauteurImage = (int)imgLoup.getWidth();
        Rectangle2D tuile = new Rectangle2D(192,32,64,32);
        this.imageView.setViewport(tuile);
        map.getChildren().add(this.imageView);


    }
}
