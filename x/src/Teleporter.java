import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Teleporter extends Entity {

    public Teleporter(double x, double y) {
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
    }

    @Override
    public void Dispose() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void paintComponent(Graphics g) {

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
