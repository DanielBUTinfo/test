package application.modele;

public class Loup extends Ennemi{
    public Loup(int x, int y, int hauteur,int largeur,Environnement env) {
        super(x, y,hauteur,largeur, env);
        this.setDeplacement(true);
        this.setOrientation(1);

    }


    @Override
    public void deplacement() {
    }

}
