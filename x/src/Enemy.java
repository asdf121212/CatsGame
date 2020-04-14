public abstract class Enemy extends Entity {

    protected int health = 100;
    //public boolean dead = false;
    //public boolean deadAnimating = false;

    public Enemy() {

    }

    public abstract void entityHit();

}
