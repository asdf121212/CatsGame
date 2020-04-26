public abstract class Enemy extends Entity {

    protected int health = 100;
    protected int ticksBetweenAttacks;
    //public boolean dead = false;
    //public boolean deadAnimating = false;

    public Enemy() {

    }

    public abstract void entityHit();


}
