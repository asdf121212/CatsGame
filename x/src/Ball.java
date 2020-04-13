import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends Entity {

    private static BufferedImage ballImage = getBufferedImage("sprites/ball.png", 50, 50);;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 9, y + 4, 20, 30);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(ballImage, x, y, 40, 40, null);
        g2.draw(getHitBox());
    }

}
