import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Fluffball extends Entity {

    private static BufferedImage fluffballImage = getBufferedImage("sprites/fluffball/fluffball.png", 20, 20);
    private static BufferedImage fluffballImageA1 = getBufferedImage("sprites/fluffball/fluffball-A1.png", 20, 20);
    private static BufferedImage fluffballImageA2 = getBufferedImage("sprites/fluffball/fluffball-A2.png", 20, 20);
    private BufferedImage image;
    private Timer moveTimer;
    private Timer disappearTimer;
    private float velocityX;
    private int negMultipler;
    private float decelX = -0.2f;

    public Fluffball(int x, int y, int velocityX, int negMultipler) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.negMultipler = negMultipler;
        image = fluffballImage;
        moveTimer = new Timer(10, moveAction);
        moveTimer.start();
    }

//    public boolean isMovingRight() {
//        return velocityX > 0;
//    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 2, y + 2, 14, 15);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, 20, 20, null);
    }

    public void stop() {
        moveTimer.stop();
    }

    private ActionListener moveAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            x = Math.round(x + (velocityX * negMultipler));
            velocityX += decelX;
            if (velocityX <= 4) {
                //moveTimer.stop();
                disappearTimer = new Timer(60, disappear);
                image = fluffballImageA1;
                disappearTimer.setInitialDelay(60);
                disappearTimer.start();
            }
        }
    };
    private ActionListener disappear = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (image.equals(fluffballImageA2)) {
                Dead = true;
                disappearTimer.stop();
                moveTimer.stop();
            } else {
                image = fluffballImageA2;
            }
        }
    };

}
