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

    //private Timer moveTimer;

    private LevelInfo levelInfo;

    public int shootTicks = 200;

    public Vacuum(int x, int y, int leftBound, int rightBound) {
        this.x = x;
        this.y = y;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        //this.levelInfo = levelInfo;
        hittable = false;

        width = 150;
        height = 50;
        xVel = 1;
        image = vacuumR;
        //moveTimer = new Timer(5, move);
        //moveTimer.start();
    }

    public void setLeftBound(int leftBound) {
        this.leftBound = leftBound;
    }
    public void setRightBound(int rightBound) {
        this.rightBound = rightBound;
    }
    public double getRightBound() {
        return rightBound;
    }

    //public void Start() {
//        if (levelInfo != null) {
//            moveTimer = new Timer(5, move);
//            moveTimer.start();
//        }
    //}

    public void addLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    @Override
    public void Update() {
        if (x <= leftBound) {
            image = vacuumR;
            xVel = 1;
        } else if (x + width >= rightBound) {
            image = vacuumL;
            xVel = -1;
        }
        x += xVel;
    }

//    private ActionListener move = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (x <= leftBound) {
//                image = vacuumR;
//                xVel = 1;
//            } else if (x + width >= rightBound) {
//                image = vacuumL;
//                xVel = -1;
//            }
//            x += xVel;
//        }
//    };

    public Ball generateBall() {
        if (levelInfo != null) {
            blast.Start();
            double x0 = x + (width / 2.0);
            double y0 = y + height - 10;
            double xDist = levelInfo.getCatX() + 20 - x0;
            double yDist = levelInfo.getCatY() + 15 - y0;
            double hyp = Math.sqrt(xDist * xDist + yDist * yDist);
            double delta_t = hyp / 5;
            double ballVx = xDist / delta_t;
            double ballVy = yDist / delta_t;
            return new Ball(x0, y0, 25, ballVx, ballVy);
        } else {
            return new Ball(x + (width / 2.0), y + height - 10, 25, 0, 4);
        }
    }

    public void Dispose() {
//        if (moveTimer != null) {
//            moveTimer.stop();
//        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
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
