package application.vue;

import application.modele.Terrain;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class TerrainVue {
    private Terrain terrain;
    private TilePane map;

    public TerrainVue(Terrain terrain, TilePane map) {
        this.terrain = terrain;
        this.map = map;
        this.afficherTerrain();
    }

    private void afficherTerrain() {
        Image tileSet = new Image("images/tileset32.png");
//        Image tileSet = new Image("images/test.png");
//        int largeurImage = (int)tileSet.getHeight();
//        int hauteurImage = (int)tileSet.getWidth();
        int nbTuiles = terrain.nbTuiles();
        int x,y;
        for (int i =0; i < nbTuiles; i++) {
            x = (terrain.codeTuile(i)%16)*32;
            y = ((terrain.codeTuile(i)/16)*32);
            ImageView imageView = new ImageView(tileSet);
            Rectangle2D tuile = new Rectangle2D(x,y,32,32);
            imageView.setViewport(tuile);

            map.getChildren().add(imageView);
        }
    }
    
    public void modifierTuile(int indiceTuile) {
    	Image tileSet = new Image("images/tileset32.png");
    	ImageView imageView = new ImageView(tileSet);
        int x = (terrain.codeTuile(indiceTuile)%16)*32;
        int y = ((terrain.codeTuile(indiceTuile)/16)*32);
    	imageView.setViewport(new Rectangle2D(x,y,32,32));
        map.getChildren().set(indiceTuile,imageView);
    }
}