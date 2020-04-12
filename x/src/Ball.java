import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends Entity {

    private static BufferedImage ballImage = getBufferedImage("sprites/ball.png", 50, 50);;

    public Ball() {
        x = 300;
        y = 300;
    }

    public Rectangle2D getHitBox() {
        return null;
    }

    public void paintComponent(Graphics g) {

    }

}
