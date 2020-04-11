import java.awt.*;
import java.awt.image.BufferedImage;

public class Ball extends Entity {

    private static BufferedImage ballImage = getBufferedImage("sprites/ball.png", 50, 50);;

    public Ball() {
        x = 300;
        y = 300;
    }

    public void paintComponent(Graphics g) {

    }

}
