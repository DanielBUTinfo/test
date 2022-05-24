package application.modele;

public class Epee extends Arme {

    public Epee(String nom,int degatsEnnemi, int degatsBlocs) {
        super(nom, degatsEnnemi, degatsBlocs);
    }
    public Epee() {
        super("Epée", 10, 5);
    }


}
