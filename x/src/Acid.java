import javafx.scene.input.SwipeEvent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Acid extends Enemy {

    private BufferedImage image = Entity.getBufferedImage("sprites/acid/acid.png", 100, 100);

    public Acid(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Acid(double x, double y) {
        this(x, y, 50, 50);
    }

    @Override
    public int getContactDamage() {
        return 200;
    }

    @Override
    public void Update() {

    }

    @Override
    public void hitCat() {

    }

    @Override
    public void Dispose() {

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void entityHit(int damage) {

    }

    @Override
    public void startDying() {

    }
}
