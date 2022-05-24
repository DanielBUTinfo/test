package application.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environnement {
	private Terrain terrain;
	private int width,height;
	private ObservableList<Acteur> acteurs;

	public Environnement(int width, int height, Terrain terrain) {
		super();
		this.width = width;
		this.height = height;
		this.acteurs = FXCollections.observableArrayList();
		this.terrain = terrain;
	}
	
	public void ajouter(Acteur a) {
		acteurs.add(a);
	}
	
	public boolean dansTerrain(int x, int y){
		return (0 <= x && x<this.width && 0<=y && y< this.height);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ObservableList<Acteur> getActeurs() {
		return acteurs;
	}
	public Terrain getTerrain() {
		return terrain;
	}
}
