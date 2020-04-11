import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Squirrel extends Entity {

    private BufferedImage squirrelImage;

    private ArrayList<Ball> BallList;

    public Squirrel() {
        BallList = new ArrayList<>();
        squirrelImage = getBufferedImage("sprites/squirrel.png", 100, 100);
        x = 100;
        y = 100;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(squirrelImage, x, y, 150, 120, null);

        for (Ball ball : BallList) {
            ball.paintComponent(g2);
        }

    }

}
