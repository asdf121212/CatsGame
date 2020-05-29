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
    //private Timer moveTimer;
    //private Timer disappearTimer;

    private boolean disappearing = false;
    private int disappearTicks = 0;

    private double velocityX;
    private int negMultipler;
    private double decelX = -0.1;

    public Fluffball(double x, double y, double velocityX, int negMultipler) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.negMultipler = negMultipler;
        image = fluffballImage;
        //moveTimer = new Timer(10, moveAction);
        //moveTimer.start();
    }

    @Override
    public void Dispose() {
//        if (moveTimer != null) {
//            moveTimer.stop();
//        }
//        if (disappearTimer != null) {
//            disappearTimer.stop();
//        }
    }

    @Override
    public void Update() {
        move();
        if (disappearing) {
            if (disappearTicks >= 12) {
                if (image.equals(fluffballImageA2)) {
                    Dead = true;
                    //disappearTimer.stop();
                    //moveTimer.stop();
                } else {
                    image = fluffballImageA2;
                    disappearTicks = 0;
                }
            } else {
                disappearTicks++;
            }
        }
    }

    public int fluffballDamage() {
        return 10;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 2, y + 2, 14, 15);
    }

    @Override
    public void entityHit(int damage) {

    }

    @Override
    public void startDying() {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), 20, 20, null);
    }

//    public void stop() {
//        moveTimer.stop();
//        if (disappearTimer != null) {
//            disappearTimer.stop();
//        }
//    }

    //private ActionListener moveAction = new ActionListener() {
        //@Override
        //public void actionPerformed(ActionEvent e) {
    private void move() {
            x = (int)Math.round(x + (velocityX * negMultipler));
            velocityX += decelX;
            if (!disappearing && velocityX <= 2) {
                //moveTimer.stop();
                //disappearTimer = new Timer(60, disappear);
                image = fluffballImageA1;
                disappearing = true;
                disappearTicks = 0;
                //disappearTimer.setInitialDelay(60);
                //disappearTimer.start();
            }
        }
    //};
//    private ActionListener disappear = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (image.equals(fluffballImageA2)) {
//                Dead = true;
//                disappearTimer.stop();
//                moveTimer.stop();
//            } else {
//                image = fluffballImageA2;
//            }
//        }
//    };

}
