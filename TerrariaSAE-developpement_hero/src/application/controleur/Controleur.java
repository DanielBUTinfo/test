package application.controleur;

import java.net.URL;
import java.util.ResourceBundle;


import application.modele.Acteur;
import application.modele.Environnement;
import application.modele.Epee;
import application.modele.Terrain;
import application.vue.InventaireVue;
import application.vue.JoueurVue;
import application.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

public class Controleur implements Initializable {

	@FXML
	private TilePane map;

	@FXML
	private BorderPane border;

	@FXML
	private Pane pane;

	// Un objet qui permet de definir les animations
	private Timeline gameLoop;

	@FXML
	private StackPane stackpane;
	Terrain terrain;
	Environnement env;
	TerrainVue vue;
	Acteur joueur;
	JoueurVue affichage;
	InventaireVue inventaireVue;

	@FXML
	void click(MouseEvent event) {
		System.out.println("objet 1 choisi");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		terrain = new Terrain("application/ressources/map.csv");
		terrain = new Terrain("src/application/ressources/maps32x32.csv");
		env = new Environnement(1600, 800, terrain);
		vue = new TerrainVue(terrain, map);
		joueur = new Acteur(0, 544, env);
		affichage = new JoueurVue(border, joueur);
		inventaireVue = new InventaireVue(stackpane);

		Epee epeeTest = new Epee();
		for (int i = 1; i < pane.getChildren().size(); i++) {
			Rectangle objet = (Rectangle) ((Pane) stackpane.getChildren().get(0)).getChildren().get(i);
			objet.setOnMouseClicked(event -> {
				System.out.println("Objet " + ((Pane) objet.getParent()).getChildren().indexOf(objet) + " choisi");
				objet.setOpacity(1);
				int indice = ((Pane) objet.getParent()).getChildren().indexOf(objet);
				Image image = new Image("images/armes/Sword "+indice+".png");
				map.getScene().setCursor(new ImageCursor(image));
				if (indice == 1){
					joueur.changerObjetEnMain(epeeTest);
					System.out.println("Epée en main");
				}
//				Image image = new Image("images/armes/Sword 1.png");
//				map.getScene().setCursor(new ImageCursor(image));
			});

			// Affichage image pv
			ImageView pv = new ImageView(new Image("images/pv.png",32,32,false,false));
			pv.setX(10);
			pv.setY(10);
			border.getChildren().add(pv);
		}

		map.setOnMouseEntered(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Image image = new Image("images/armes/Sword 1.png");
				map.getScene().setCursor(new ImageCursor(image));
			}
		});


		border.setOnKeyPressed(event -> {
			if (stackpane.getChildren().get(1) == map && (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D)) {
				joueur.setDeplacement(true);
				joueur.setOrientation(1);
			} else if (stackpane.getChildren().get(1) == map && (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.Q)) {
				joueur.setDeplacement(true);
				joueur.setOrientation(-1);
			} else if (stackpane.getChildren().get(1) == map && (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.Z)) {
				if (!joueur.estEnLair())
					joueur.setSAUT(true);
			} else if (event.getCode() == KeyCode.I) {
				ObservableList<Node> childs = stackpane.getChildren();
				if (childs.size() > 1) {
					inventaireVue.afficherInventaire();

				}
			}
		});

		border.setOnKeyReleased(event -> {
			if (stackpane.getChildren().get(1) == map && (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D))
				joueur.setDeplacement(false);
			else if (stackpane.getChildren().get(1) == map && (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.Q))
				joueur.setDeplacement(false);
		});

		initAnimation();
		gameLoop.play();


		border.setOnMouseClicked(event -> {
			if (stackpane.getChildren().get(1) == map) {
				int indiceTuile = ((int) event.getY()) / 32 * 50 + ((int) event.getX()) / 32;
				int tuileJoueur = ((int) joueur.getyValue()) / 32 * 50 + ((int) joueur.getxValue()) / 32;
				if (tuileJoueur != indiceTuile) {
					if (terrain.codeTuile(indiceTuile) != 178)
						terrain.enleveTuile(indiceTuile);
					else
						terrain.ajoutTuile(indiceTuile);
//					vue.modifierTuile(indiceTuile);
				} else
					System.out.println("Impossible de modifier la tuile " + indiceTuile + " car le joueur est placé dessus");
			}
		});

		this.terrain.getTuiles().addListener((Change<? extends Integer> c) -> {
			while (c.next()) {
				int indiceTuile = c.getFrom();
				vue.modifierTuile(indiceTuile);
//				System.out.println("Changement de tuile");
//				System.out.println("est-ce des ajouts ? " + c.wasAdded());
//				System.out.println("est-ce des suppressions ? " + c.wasRemoved());
//				System.out.println("les ajouts : " + c.getAddedSubList());
//				System.out.println("les suppressions : " + c.getRemoved());
//				System.out.println("les changements : " + c.wasPermutated());
			}
		});


	}


	void initAnimation() {
		System.out.println(map.getPrefHeight());
		this.gameLoop = new Timeline();
		this.gameLoop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(
				Duration.seconds(0.009),
				(ev -> {
					if(joueur.getyValue() < joueur.getEnv().getHeight()-32)
						joueur.gravite();
					if (joueur.isSAUT()) {
						joueur.setyValue(joueur.getyValue() - 1);
						joueur.setVitesseY(-2);
						joueur.setSAUT(false);
					}
					if (joueur.isDeplacement() && joueur.colision())
						joueur.seDeplacement();
				})
		);
		this.gameLoop.getKeyFrames().add(kf);
	}
}