package application.controleur;

import java.net.URL;
import java.util.ResourceBundle;


import application.modele.*;
import application.vue.*;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
	private Terrain terrain;
	private Environnement env;
	private TerrainVue vue;
	private Acteur joueur;
	private JoueurVue affichage;
	private InventaireVue inventaireVue;
	private Loup loup;
	private LoupVue affichageLoup;
	private Zombie zombie;
	private ZombieVue affichageZombie;


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
		joueur = new Acteur(0, 544,32,32, env);
		affichage = new JoueurVue(border, joueur);
		inventaireVue = new InventaireVue(stackpane);
		loup = new Loup(100, 544,32 ,64,env);
		affichageLoup = new LoupVue(border, loup);
		zombie = new Zombie(150, 524,50 ,32,env);
		affichageZombie = new ZombieVue(border, zombie);
		env.ajouter(joueur);
		env.ajouter(loup);
		env.ajouter(zombie);

		for (int i = 1; i < pane.getChildren().size(); i++) {
			Rectangle objet = (Rectangle) ((Pane) stackpane.getChildren().get(0)).getChildren().get(i);
			objet.setOnMouseClicked(event -> {
				System.out.println("Objet " + ((Pane) objet.getParent()).getChildren().indexOf(objet) + " choisi");
				objet.setOpacity(1);
			});

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
					for(Acteur a: env.getActeurs()) {
						a.gravite();
						if (a.isSAUT()) {
							a.setyValue(a.getyValue() - 1);
							a.setVitesseY(-2);
							a.setSAUT(false);
						}
						if (a.isDeplacement() && a.colision())
							a.seDeplacement();
					}
					})
		);
		this.gameLoop.getKeyFrames().add(kf);
	}
}