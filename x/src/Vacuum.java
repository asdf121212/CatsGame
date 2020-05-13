import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Vacuum extends Enemy{

    private BufferedImage vacuumL = Entity.getBufferedImage("sprites/vacuum/vacuumBlast1.png", 100, 100);
    private BufferedImage vacuumR = Entity.getBufferedImage("sprites/vacuum/vacuumBlast1R.png", 100, 100);

    private AutoResetSound blast = new AutoResetSound("SoundFiles/ballShoot.wav");

    private BufferedImage image;

    private int leftBound;
    private int rightBound;
    private double xVel;

    private Timer moveTimer;

    private LevelInfo levelInfo;

    public Vacuum(int x, int y, int leftBound, int rightBound, LevelInfo levelInfo) {
        this.x = x;
        this.y = y;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.levelInfo = levelInfo;
        hittable = false;

        width = 150;
        height = 50;
        xVel = 1;
        image = vacuumR;
        moveTimer = new Timer(5, move);
        moveTimer.start();

    }

    private ActionListener move = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (x <= leftBound) {
                image = vacuumR;
                xVel = 1;
            } else if (x + width >= rightBound) {
                image = vacuumL;
                xVel = -1;
            }
            x += xVel;
        }
    };

    public Ball generateBall() {
        blast.Start();
        int x0 = x + (width / 2);
        int y0 = y + height - 10;
        double xDist = levelInfo.getCatX() + 50 - x0;
        double yDist = levelInfo.getCatY() + 25 - y0;
        double hyp = Math.sqrt(xDist * xDist + yDist * yDist);
        double delta_t = hyp / 6;
        double ballVx = xDist / delta_t;
        double ballVy = yDist / delta_t;
        return new Ball(x0, y0, ballVx, ballVy);
    }

    public void Dispose() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, width, height, null);
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
    public int getContactDamage() {
        return 0;
    }

    @Override
    public void hitCat() {

    }
}
