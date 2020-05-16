import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PigMouse extends SolidEnemy {

    private BufferedImage pigMouse1 = Entity.getBufferedImage("sprites/pigMouse/pigMouse.png", 100, 100);
    private BufferedImage pigMouseHot1 = Entity.getBufferedImage("sprites/pigMouse/pigMouseHot1.png", 100, 100);
    private BufferedImage pigMouseHot2 = Entity.getBufferedImage("sprites/pigMouse/pigMouseHot2.png", 100, 100);
    private BufferedImage pigMouseHot3 = Entity.getBufferedImage("sprites/pigMouse/pigMouseHot3.png", 100, 100);

    private BufferedImage image;
    private LevelInfo levelInfo;
    private ArrayList<JumpingRect> path;
    private Timer planTimer;
    private Timer traverseTimer;

    private double xVel;
    private double yVel;
    private final double grav = 0.2;

    public PigMouse(int x, int y) {
        this.x = x;
        this.y = y;
        image = pigMouse1;
        hittable = false;
        width = 59;
        height = 60;

        planTimer = new Timer(100, plan);
        traverseTimer = new Timer(5, traverse);
        planTimer.start();
        traverseTimer.setInitialDelay(200);
        traverseTimer.start();
    }

    private ActionListener plan = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
    private ActionListener traverse = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (plan != null) {

            }
            x += xVel;
            JumpingRect floor = null;
            

        }
    };

    public void addLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public void Dispose() {
        planTimer.stop();
        traverseTimer.stop();
    }

    @Override
    public int getContactDamage() {
        return 200;
    }

    @Override
    public void hitCat() {

    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }
    @Override
    public void entityHit(int damage) {
    }
    @Override
    public void startDying() {
    }

    @Override
    public void paintComponent(Graphics g) {
        ((Graphics2D)g).drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }

}
