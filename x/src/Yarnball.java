import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Yarnball extends Enemy {

    private BufferedImage yarnball0 = Entity.getBufferedImage("sprites/yarnBall/yarnBall-0.png", 140, 140);
    private BufferedImage yarnball1 = Entity.getBufferedImage("sprites/yarnBall/yarnBall-1.png", 140, 140);
    private BufferedImage yarnball2 = Entity.getBufferedImage("sprites/yarnBall/yarnBall-2.png", 140, 140);
    private BufferedImage yarnball2Flash = Entity.getBufferedImage("sprites/yarnBall/yarnBall-2-Flash.png", 140, 140);

    private AutoResetSound yarnballDieSound = new AutoResetSound("SoundFiles/yarnballdie.wav");
    private AutoResetSound pantherRoar = new AutoResetSound("SoundFiles/panther-roar2-2.wav");

    private int imageChangeTicks = 0;
    private Timer attackTimer;
    private Timer dieTimer;
    private Timer flashTimer;
    private BufferedImage image = yarnball0;
    private double Range;
    private boolean active = false;
    private double yVel = 0;
    private double xVel = -3;

    public Yarnball(int x, int y, double range) {
        this.x = x;
        this.y = y;
        width = 120;
        height = 120;
        this.Range = range;
        health = 30;
    }

    public void Dispose() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
        if (dieTimer != null) {
            dieTimer.stop();
        }
        if (flashTimer != null) {
            flashTimer.stop();
        }
    }

    public int getContactDamage() {
        return 50;
    }

    public void Start() {
        attackTimer = new Timer(5, attack);
        attackTimer.start();
        image = yarnball1;
        active = true;
        pantherRoar.Start();
    }

    public boolean enteredAttackZone(double xCoord, double yCoord) {
        if ((attackTimer != null && attackTimer.isRunning()) || Dead || Dying) {
            return false;
        } else {
            return x - xCoord < Range && yCoord <= y + width + 10 && yCoord >= y - 10;
        }
    }

    public void hitCat() {

    }

    public void startDying() {
        Dying = true;
        dieTimer = new Timer(5, die);
        pantherRoar.Stop();
        //dieTimer.setInitialDelay(90);
        image = yarnball2Flash;
        imageChangeTicks = 0;
        dieTimer.start();
        yarnballDieSound.Start();
    }

    @Override
    public void entityHit(int damage) {
        if (!active) {
            return;
        }
        health -= damage;
        if (health <= 0) {
            attackTimer.stop();
            startDying();
        } else {
            if (flashTimer != null && flashTimer.isRunning()) {
                flashTimer.stop();
            }
            flashTimer = new Timer(0, flashListener);
            flashTimer.setInitialDelay(75);
            image = yarnball2Flash;
            flashTimer.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 18, y + 15, width - 28, height - 25);
    }

    private ActionListener flashListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            image = yarnball2;
            flashTimer.stop();
        }
    };

    private ActionListener die = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageChangeTicks < 15) {
                imageChangeTicks++;
            } else if (imageChangeTicks == 15) {
                image = yarnball0;
                imageChangeTicks++;
            }
            y += yVel;
            x += xVel;
            yVel += 0.1;
//            if (image.equals(yarnball-explode1)) {
//                image = yarnball1;
//            } else if (image.equals(yarnball1)) {
//                image = yarnball1;
//            } else {
//                Dying = false;
//                Dead = true;
//                dieTimer.stop();
//            }
        }
    };

    private ActionListener attack = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageChangeTicks < 7) {
                imageChangeTicks++;
            } else if (imageChangeTicks == 7) {
                image = yarnball2;
                imageChangeTicks++;
            }
            x += xVel;
            //should put boundary based deaths in Entity class
            if (x < -70 || y > 720) {
                Dead = true;
                attackTimer.stop();
                if (dieTimer != null) {
                    dieTimer.stop();
                }
            }
        }
    };


}
