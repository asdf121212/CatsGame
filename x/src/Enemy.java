public abstract class Enemy extends Entity {

    protected int health = 100;

    public Enemy() {

    }

    public abstract int getContactDamage();

    public abstract void entityHit();


}
