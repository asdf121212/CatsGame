import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ZinzanLife extends Entity {
    private BufferedImage zinzan = getBufferedImage("sprites/zinzan/zinzanStill.png", 50, 25);

    public ZinzanLife(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public ZinzanLife(int x, int y) {
        this(x, y, 22, 15);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Dispose() {
    }

    @Override
    public void paintComponent(Graphics g) {
        ((Graphics2D)g).drawImage(zinzan, (int)Math.round(x), (int)Math.round(y), width, height, null);
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
