import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Vacuum extends Enemy{

    @Override
    public int getContactDamage() {
        return 0;
    }

    @Override
    public void hitCat() {

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public Rectangle2D getHitBox() {
        return null;
    }

    @Override
    public void entityHit(int damage) {

    }

    @Override
    public void startDying() {

    }
}
