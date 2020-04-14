import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends Danger {

    private static BufferedImage ballImage = getBufferedImage("sprites/ball.png", 50, 50);;
    private Timer moveTimer;
    private int xVel;

    public Ball(int x, int y, int xVel) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        moveTimer = new Timer(10, move);
        moveTimer.start();
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 9, y + 4, 20, 30);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(ballImage, x, y, 50, 50, null);
    }

    private ActionListener move = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            x += xVel;
        }
    };

}
