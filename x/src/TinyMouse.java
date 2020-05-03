import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TinyMouse extends Enemy {

    //private static BufferedImage mouseImage = Entity.getBufferedImage("", 30, 30);

    private double xVel;
    private int xBound;
    private static BufferedImage image;

    public TinyMouse(int x, int y, int xBound) {
        this.x = x;
        this.y = y;
        this.xBound = xBound;
        //width =
        //height =
    }


    @Override
    public int getContactDamage() {
        return 10;
    }
    @Override
    public void hitCat() {

    }

    @Override
    public Rectangle2D getHitBox() {
        return null;
    }
    @Override
    public void entityHit(int damage) {
        //do nothing
    }
    @Override
    public void startDying() {

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, width, height, null);
    }


}
