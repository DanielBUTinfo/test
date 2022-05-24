package application.modele;

public abstract class Arme extends Item{
    private int degatsEnnemi;
    private int degatsBlocs;
    public Arme(String nom, int degatsEnnemi, int degatsBlocs) {
        super(nom);
        degatsEnnemi = degatsEnnemi;
        degatsBlocs = degatsBlocs;
    }

    public int infligerDegatsEnnemi(){
        return degatsEnnemi;
    }
}
