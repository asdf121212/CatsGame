import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Squirrel extends Enemy {

    private BufferedImage squirrelImage;
    private BufferedImage squirrelFlashImage;
    private BufferedImage image;
    private Timer flashTimer;

    private ArrayList<Ball> BallList;

    public Squirrel() {
        BallList = new ArrayList<>();
        squirrelImage = getBufferedImage("sprites/squirrel.png", 100, 100);
        squirrelFlashImage = getBufferedImage("sprites/squirrelFlash.png", 100, 100);
        image = squirrelImage;
        x = 100;
        y = 100;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 15, y + 56, 90, 50);
    }


    public void entityHit() {
        health -= 10;
        flashTimer = new Timer(0, flash);
        flashTimer.setInitialDelay(75);
        image = squirrelFlashImage;
        flashTimer.start();
        if (health <= 0) {

        }
    }

    private ActionListener flash = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            image = squirrelImage;
            flashTimer.stop();
        }
    };


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, 150, 120, null);
        for (Ball ball : BallList) {
            ball.paintComponent(g2);
        }

    }

}
