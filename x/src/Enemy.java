public abstract class Enemy extends Entity {

    public boolean hitCoolingDown = false;
    public boolean hittable = true;

    public Enemy() {

    }

    public abstract int getContactDamage();

    public abstract void hitCat();


}
