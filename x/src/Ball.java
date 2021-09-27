import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends Enemy {

    private static final BufferedImage ballImage = getBufferedImage("sprites/ball/ball.png", 50, 50);
    private static final BufferedImage ballHit1 = getBufferedImage("sprites/ball/ballHit1.png", 50, 50);
    private static final BufferedImage ballHit2 = getBufferedImage("sprites/ball/ballHit2.png", 50, 50);
    private BufferedImage image;
    //private Timer moveTimer;
    private int dieTicks = 0;
    private int tickLimit = 24;
    //private Timer deathTimer;
    private final double xVel;
    private final double yVel;
    private int leftBound = -60;
    private int rightBound = 1300;

    public Ball(double x, double y, int width, double xVel, double yVel) {
        hittable = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = width;
        this.xVel = xVel;
        this.yVel = yVel;
        image = ballImage;
        //moveTimer = new Timer(5, move);
        //moveTimer.start();
    }

    public void Dispose() {
//        if (moveTimer != null) {
//            moveTimer.stop();
//        }
//        if (deathTimer != null) {
//            deathTimer.stop();
//        }
    }

    public int getContactDamage() {
        return 25;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + width/5.0, y + width/10.0, width - width/2.5, height - height/5.0);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
        //g2.setColor(Color.magenta);
        //g2.draw(getHitBox());
    }

    public void entityHit(int damage) {
    }

    public void hitCat() {
        startDying();
    }

    @Override
    public void Update() {
        if (Dying) {
            if (dieTicks >= tickLimit) {
                if (image.equals(ballHit1)) {
                    image = ballHit2;
                    tickLimit = 16;
                } else {
                    Dead = true;
                }
                dieTicks = 0;
            } else {
                dieTicks++;
            }
        } else {
            x += xVel;
            if (x < leftBound || x > rightBound) {
                Dead = true;
                //moveTimer.stop();
            }
            y += yVel;
            if (y < -10 - height || y > 710 + height) {
                Dead = true;
                //moveTimer.stop();
            }
        }
    }

    @Override
    public void startDying() {
        //moveTimer.stop();
        image = ballHit1;
        Dying = true;
        tickLimit = 24;
        //deathTimer = new Timer(80, die);
        //deathTimer.setInitialDelay(120);
        //deathTimer.start();
    }

    private ActionListener move = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            x += xVel;
            if (x < leftBound || x > rightBound) {
                Dead = true;
                //moveTimer.stop();
            }
            y += yVel;
            if (y < -10 - height || y > 710 + height) {
                Dead = true;
                //moveTimer.stop();
            }
        }
    };
//    private ActionListener die = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (image.equals(ballHit1)) {
//                image = ballHit2;
//            } else {
//                moveTimer.stop();
//                deathTimer.stop();
//                Dead = true;
//            }
//        }
//    };

}
