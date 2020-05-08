import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DaisyEnemy extends SolidEnemy {

    private BufferedImage[] daisyWalk = new BufferedImage[] {
            Entity.getBufferedImage("sprites/daisyEnemy/d1L.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d2L.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d3L.png", 80, 80)
    };
    private BufferedImage[] daisyWalkR = new BufferedImage[] {
            Entity.getBufferedImage("sprites/daisyEnemy/d1R.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d2R.png", 80, 80),
            Entity.getBufferedImage("sprites/daisyEnemy/d3R.png", 80, 80)
    };

    private BufferedImage daisyFly = Entity.getBufferedImage("sprites/daisyEnemy/daisyFly.png", 80, 80);
    private BufferedImage daisyFlyR = Entity.getBufferedImage("sprites/daisyEnemy/daisyFlyR.png", 80, 80);

    private BufferedImage image;
    private BufferedImage[] walkImages;

    private LevelInfo levelInfo;
    private boolean zinzanInRange = false;

    private Line2D leftFloor;
    private Line2D rightFloor;

    private int walkTicks = 0;
    private int walkIndex = 0;
    private int indexDirection = 1;
    private int xVel;
    private double yVel = 0;
    private double grav = 0.2;

    private Timer walkBackAndForthTimer;
    private Timer jumpLTimer;


    public DaisyEnemy(Rectangle2D leftFloor, Rectangle2D rightFloor) {
        this.leftFloor = new Line2D.Double(leftFloor.getX(), leftFloor.getY(),
                leftFloor.getX() + leftFloor.getWidth(), leftFloor.getY());
        this.rightFloor = new Line2D.Double(rightFloor.getX(), rightFloor.getY(),
            rightFloor.getX() + rightFloor.getWidth(), rightFloor.getY());

        width = 60;
        height = 50;
        health = 300;

        x = (int)Math.round(rightFloor.getX());
        y = (int)Math.round(rightFloor.getY()) - height;

        walkBackAndForthTimer = new Timer(5, walk);
        walkImages = daisyWalkR;
        image = daisyWalkR[0];
        xVel = 2;
        walkBackAndForthTimer.start();

    }
//    public DaisyEnemy(Line2D attackFloor, Line2D restFloor) {
//        this.attackFloor = attackFloor;
//        this.restFloor = restFloor;
//    }

    private ActionListener walk = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (walkTicks >= 8) {
                walkIndex += indexDirection;
                walkTicks = 0;
            } else {
                walkTicks++;
            }
            if (walkIndex == 2) {
                indexDirection = -1;
            } else if (walkIndex == 0) {
                indexDirection = 1;
            }
            image = walkImages[walkIndex];
            if (xVel > 0) {
                if (x >= rightFloor.getX2() - width) {
                    walkImages = daisyWalk;
                    xVel = -2;
                    walkTicks = 0;
                }
            } else if (xVel < 0) {
                Random rand = new Random();
                if (x <= rightFloor.getX1()) {
                    if (true) {//(rand.nextBoolean()) {
                        startJump();
                    } else {
                        walkImages = daisyWalkR;
                        xVel = 2;
                        walkTicks = 0;
                    }
                }
            }
            x += xVel;

        }
    };

    private ActionListener jumpL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (y + height >= leftFloor.getY1() && x < leftFloor.getX2()) {
                jumpLTimer.stop();
                image = daisyWalk[1];
                y = (int)Math.round(leftFloor.getY1() - height);
            } else {
                x += xVel;
                y += yVel;
                yVel += grav;
            }
        }
    };

    private void startJump() {
        int catX = levelInfo.getCatX() + 25;
        int catY = levelInfo.getCatY();
        if (catX < x) {
            image = daisyFly;
        } else {
            image = daisyFlyR;
        }

        walkBackAndForthTimer.stop();
        jumpLTimer = new Timer(5, jumpL);

        xVel = -5;
        double deltaT = Math.abs((catX - x) / xVel);
        double deltaY = catY - y;
        yVel = (deltaY - (0.5 * grav * deltaT * deltaT)) / deltaT;


        jumpLTimer.start();

    }

    public void addLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
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
        return new Rectangle2D.Double(x, y, width, height);///change
    }

    @Override
    public void entityHit(int damage) {
        health -= damage;
    }

    @Override
    public void startDying() {

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, width, height, null);
    }
}
