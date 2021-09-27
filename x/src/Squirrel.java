import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Squirrel extends Enemy {

    protected BufferedImage squirrelImage;
    protected BufferedImage squirrelFlashImage;
    protected BufferedImage squirrelExp1;
    protected BufferedImage squirrelExp2;
    protected BufferedImage squirrelExp3;
    protected BufferedImage image;
    //private Timer flashTimer;
    //private Timer explodeTimer;
    private AutoResetSound explodeClip = new AutoResetSound("SoundFiles/Explosion.wav");
    protected AutoResetSound generateBallClip = new AutoResetSound("SoundFiles/ballShoot.wav");

    //private ArrayList<Ball> BallList;
    public int shootTicks = 0;
    private int ticks = 0;
    private int tickLimit = 0;
    //private int flas
    private boolean flashing = false;

    public Squirrel(int x, int y) {
        this.x = x;
        this.y = y;
        width = 150;
        height = 120;
        health = 50;
        LoadImages();
        image = squirrelImage;
    }

    public void LoadImages() {
        squirrelImage = getBufferedImage("sprites/squirrel/squirrel.png", 100, 100);
        squirrelFlashImage = getBufferedImage("sprites/squirrel/squirrelFlash.png", 100, 100);
        squirrelExp1 = getBufferedImage("sprites/squirrel/squirrelExp1.png", 100, 100);
        squirrelExp2 = getBufferedImage("sprites/squirrel/squirrelExp2.png", 100, 100);
        squirrelExp3 = getBufferedImage("sprites/squirrel/squirrelExp3.png", 100, 100);
    }

    @Override
    public void Update() {
        if (flashing) {
            if (ticks >= tickLimit) {
                image = squirrelImage;
                flashing = false;
                ticks = 0;
            } else {
                ticks++;
            }
        } else if (Dying) {
            if (ticks >= tickLimit) {
                if (image.equals(squirrelExp1)) {
                    image = squirrelExp2;
                    tickLimit = 30;
                    ticks = 0;
                } else if (image.equals(squirrelExp2)) {
                    image = squirrelExp3;
                    ticks = 0;
                } else {
                    Dying = false;
                    Dead = true;
                }
            } else {
                ticks++;
            }
        }
    }

    @Override
    public void Dispose() {
//        if (flashTimer != null) {
//            flashTimer.stop();
//        }
//        if (explodeTimer != null) {
//            explodeTimer.stop();
//        }
    }

    public int getContactDamage() {
        return 200;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 15, y + 56, 90, 50);
    }

    public void hitCat() {
        //do nothing
    }

    public void startDying() {
        try {
            explodeClip.Start();
        }
        catch (Exception ex) {

        }
        Dying = true;
        //explodeTimer = new Timer(150, explode);
        //explodeTimer.setInitialDelay(90);
        ticks = 0;
        tickLimit = 18;
        image = squirrelExp1;
        flashing = false;
        //explodeTimer.start();
        //if (flashTimer != null) {
            //flashTimer.stop();
        //}
    }

    public void entityHit(int damage) {
        //squirrel damage is 10 from zinzan
        health -= damage;
        if (health <= 0) {
            startDying();
        } else {
            //if (flashTimer != null && flashTimer.isRunning()) {
                //flashTimer.stop();
            //}
            //flashTimer = new Timer(0, flash);
            //flashTimer.setInitialDelay(75);
            image = squirrelFlashImage;
            //flashTimer.start();
            ticks = 0;
            tickLimit = 15;
            flashing = true;
        }
    }

    public Ball generateBall(double xVelocity) {
        //generateBallClip.Stop();
        generateBallClip.Start();
        return new Ball(x - 20, y + 65, 40, xVelocity, 0);
    }

//    private ActionListener flash = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            image = squirrelImage;
//            flashTimer.stop();
//        }
//    };
//    private ActionListener explode = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (image.equals(squirrelExp1)) {
//                image = squirrelExp2;
//            } else if (image.equals(squirrelExp2)) {
//                image = squirrelExp3;
//            } else {
//                Dying = false;
//                Dead = true;
//                explodeTimer.stop();
//            }
//        }
//    };


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }

}
