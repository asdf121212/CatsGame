import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Fluffball extends Entity {

    private static BufferedImage fluffballImage = getBufferedImage("sprites/fluffball.png", 20, 20);;
    private Timer moveTimer;
    private int velocityX;

    public Fluffball(int x, int y, int velocityX) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        moveTimer = new Timer(10, moveAction);
        moveTimer.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(fluffballImage, x, y, 20, 20, null);
    }


    public ActionListener moveAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            x += velocityX;
            if (x < -50 || x > 1250) {
                moveTimer.stop();
                stillMoving = false;
            }
        }
    };

}
