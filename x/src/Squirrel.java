import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

public class Squirrel extends Enemy {

    private static BufferedImage squirrelImage = getBufferedImage("sprites/squirrel.png", 100, 100);
    private static BufferedImage squirrelFlashImage = getBufferedImage("sprites/squirrelFlash.png", 100, 100);
    private static BufferedImage squirrelExp1 = getBufferedImage("sprites/squirrelExp1.png", 100, 100);
    private static BufferedImage squirrelExp2 = getBufferedImage("sprites/squirrelExp2.png", 100, 100);
    private static BufferedImage squirrelExp3 = getBufferedImage("sprites/squirrelExp3.png", 100, 100);
    private BufferedImage image;
    private Timer flashTimer;
    private Timer explodeTimer;
    private static AutoResetSound clip = new AutoResetSound("SoundFiles/Explosion.wav");

    private ArrayList<Ball> BallList;


    public Squirrel() {
        BallList = new ArrayList<>();

        image = squirrelImage;
        x = 100;
        y = 100;
        width = 150;
        height = 120;

    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 15, y + 56, 90, 50);
    }


    public void entityHit() {
        health -= 10;
        if (health <= 0) {
            try {
               //clip.start();
                clip.Start();
            }
            catch (Exception ex) {

            }
            Dying = true;
            explodeTimer = new Timer(150, explode);
            explodeTimer.setInitialDelay(90);
            image = squirrelExp1;
            explodeTimer.start();
            if (flashTimer != null) {
                flashTimer.stop();
            }
        } else {
            if (flashTimer != null && flashTimer.isRunning()) {
                flashTimer.stop();
            }
            flashTimer = new Timer(0, flash);
            flashTimer.setInitialDelay(75);
            image = squirrelFlashImage;
            flashTimer.start();
        }
    }

    public Ball generateBall(int xVelocity) {

        return new Ball(x - 20, y + 50, xVelocity);
    }

    private ActionListener flash = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            image = squirrelImage;
            flashTimer.stop();
        }
    };
    private ActionListener explode = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (image.equals(squirrelExp1)) {
                image = squirrelExp2;
            } else if (image.equals(squirrelExp2)) {
                image = squirrelExp3;
            } else {
                Dying = false;
                Dead = true;
                explodeTimer.stop();
            }
        }
    };


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, width, height, null);
        for (Ball ball : BallList) {
            ball.paintComponent(g2);
        }

    }

}
