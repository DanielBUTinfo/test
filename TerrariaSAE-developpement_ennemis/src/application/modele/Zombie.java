package application.modele;

public class Zombie extends Ennemi {

    public Zombie(int x, int y, int hauteur,int largeur,Environnement env) {
        super(x, y,hauteur,largeur, env);
        this.setDeplacement(true);
        this.setOrientation(1);

    }

    @Override
    public void deplacement() {

    }


}


