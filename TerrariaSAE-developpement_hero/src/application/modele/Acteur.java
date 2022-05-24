package application.modele;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Acteur {
    private IntegerProperty xProperty;
    private IntegerProperty yProperty;
    public static int compteur = 0;
    private String id;
    private Environnement env;
    private IntegerProperty orientation;
    private boolean deplacement;
    private boolean SAUT;
    private double vitesseY;
    private double vitesseX;
    private Item objetEnMain;
    private int pv;
    private Inventaire inventaire;

    public Acteur(int x, int y, Environnement env) {
        this.xProperty = new SimpleIntegerProperty(x);
        this.yProperty = new SimpleIntegerProperty(y);
        this.id = "A"+compteur;
        compteur++;
        this.env = env;
        this.orientation = new SimpleIntegerProperty(1);
        this.deplacement = false;
        this.SAUT = false;
        this.env.ajouter(this);
        this.objetEnMain = null;
        this.pv=10;
        this.inventaire = new Inventaire();
    }

    public String getId() {
        return id;
    }

    public IntegerProperty getxProperty() {
        return xProperty;
    }

    public IntegerProperty getyProperty() {
        return yProperty;
    }

    public int getxValue() {
        return this.xProperty.getValue();
    }

    public int getyValue() {
        return this.yProperty.getValue();
    }

    public void setxValue(int n) {
        this.xProperty.setValue(n);
    }
    public void setyValue(int n) {
        this.yProperty.setValue(n);
    }

    public Environnement getEnv() {
        return env;
    }

    public IntegerProperty getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation.setValue(orientation);
    }

    public Inventaire getInventaire(){
        return inventaire;
    }

    public void changerObjetEnMain(Item i){
        objetEnMain = i;
    }


    public boolean isDeplacement() {
        return deplacement;
    }

    public void setDeplacement(boolean deplacement) {
        this.deplacement = deplacement;
    }

    public boolean isSAUT() {
        return SAUT;
    }
    public void setSAUT(boolean SAUT) {
        this.SAUT = SAUT;
    }

    public boolean colision() {
        int a = 32;
        if (orientation.getValue() == -1)
            a = -1;
        return (this.env.dansTerrain(this.xProperty.getValue() + this.orientation.getValue(), this.yProperty.getValue()) &&
                this.env.getTerrain().codeTuile((yProperty.getValue()) / 32 * 50 + (xProperty.getValue() + a) / 32) == 178 &&
                this.env.getTerrain().codeTuile((yProperty.getValue() + 31) / 32 * 50 + (xProperty.getValue() + a) / 32) == 178 &&
                this.env.getTerrain().codeTuile((yProperty.getValue() + 15) / 32 * 50 + (xProperty.getValue() + a) / 32) == 178);
    }

    public boolean colisonHaut() {
        return (this.env.dansTerrain(this.xProperty.getValue(), this.yProperty.getValue() - 1) &&
                this.env.getTerrain().codeTuile((yProperty.getValue() - 1) / 32 * 50 + (xProperty.getValue()) / 32) == 178 &&
                this.env.getTerrain().codeTuile((yProperty.getValue() - 1) / 32 * 50 + (xProperty.getValue() + 15) / 32) == 178 &&
                this.env.getTerrain().codeTuile((yProperty.getValue() - 1) / 32 * 50 + (xProperty.getValue() + 31) / 32) == 178);
    }

    public void seDeplacement(){
        this.xProperty.setValue(this.xProperty.getValue()+orientation.getValue());
    }

    public void gravite() {
        vitesseY += 0.09;
        if (env.getTerrain().codeTuile((yProperty.getValue() + 31 + (int) vitesseY) / 32 * 50 + (xProperty.getValue()) / 32) != 178 ||
                env.getTerrain().codeTuile((yProperty.getValue() + 31 + (int) vitesseY) / 32 * 50 + (xProperty.getValue() + 31) / 32) != 178 ||
                env.getTerrain().codeTuile((yProperty.getValue() + 31 + (int) vitesseY) / 32 * 50 + (xProperty.getValue() + 15) / 32) != 178 || (!colisonHaut() && vitesseY<0) )
            vitesseY = 0;

        this.yProperty.setValue(this.yProperty.getValue() + vitesseY);
    }

    public void setVitesseY(double vitesse) {
        this.vitesseY = vitesse;
    }

    public boolean estEnLair() {
        return(env.getTerrain().codeTuile((yProperty.getValue() + 32) / 32 * 50 + (xProperty.getValue()) / 32) == 178 &&
                env.getTerrain().codeTuile((yProperty.getValue() + 32) / 32 * 50 + (xProperty.getValue() + 31) / 32) == 178 &&
                env.getTerrain().codeTuile((yProperty.getValue() + 32) / 32 * 50 + (xProperty.getValue() + 15) / 32) == 178);
    }

    public Item getObjetEnMain(){
        return this.objetEnMain;
    }
}