import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ZinzanLife extends Entity {
    private static BufferedImage zinzan = getBufferedImage("sprites/zinzanStill.png", 50, 25);

    public ZinzanLife(int x, int y) {
        this.x = x;
        this.y = y;
        width = 22;
        height = 15;
    }

    @Override
    public void paintComponent(Graphics g) {
        ((Graphics2D)g).drawImage(zinzan, x, y, width, height, null);
    }

    @Override
    public Rectangle2D getHitBox() {
        return null;
    }
}
