import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends Danger {

    private static BufferedImage ballImage = getBufferedImage("sprites/ball.png", 50, 50);
    private static BufferedImage ballHit1 = getBufferedImage("sprites/ballHit1.png", 50, 50);
    private static BufferedImage ballHit2 = getBufferedImage("sprites/ballHit2.png", 50, 50);
    private BufferedImage image;
    private Timer moveTimer;
    private Timer deathTimer;
    private int xVel;
    private int leftBound = -60;
    private int rightBound = 1300;

    public Ball(int x, int y, int xVel) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        image = ballImage;
        moveTimer = new Timer(10, move);
        moveTimer.start();
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 9, y + 4, 20, 30);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, 50, 50, null);
    }

    public void hitTarget() {
        moveTimer.stop();
        image = ballHit1;
        Dying = true;
        deathTimer = new Timer(80, die);
        deathTimer.setInitialDelay(120);
        deathTimer.start();
    }

    private ActionListener move = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            x += xVel;
            if (x < leftBound || x > rightBound) {
                Dead = true;
                moveTimer.stop();
            }
        }
    };
    private ActionListener die = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (image.equals(ballHit1)) {
                image = ballHit2;
            } else {
                deathTimer.stop();
                Dead = true;
            }
        }
    };

}
