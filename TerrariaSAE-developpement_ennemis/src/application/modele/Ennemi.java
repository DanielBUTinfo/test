package application.modele;

public abstract class Ennemi extends Acteur {
    private int pv;

    public Ennemi(int x, int y, int hauteur,int largeur,Environnement env) {
        super(x, y,hauteur,largeur, env);
        pv = 10;
    }

    public void decrementerPv(int nb) {
        pv -= nb;
    }

    public abstract void deplacement();

//    public Boolean attaquer() {
//        for (Acteur m : super.getEnvironement().getActeurs()) {
//            if (m instanceof Mouton) {
//                if (Math.abs(getX() - m.getX()) <= 5 || Math.abs(getY() - m.getY()) <= 5) {
//                    m.meurt();
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}



