import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;


public class Cat extends Entity {

    public byte state = 000;
    //private int Health = 100;
    private BufferedImage catImage;
    private int frameCount = 0;
    private double previousDx = 0;
    private BufferedImage[] popAnimationImages;
    int popIndex = 0;

    //private static final Clip popSound = loadSound("SoundFiles/Explosion.wav");
    private static AutoResetSound popSound = new AutoResetSound("SoundFiles/pop.wav");

    private static final BufferedImage still = getBufferedImage("sprites/zinzan/zinzanStill.png", 100, 50);
    private static final BufferedImage walk1 = getBufferedImage("sprites/zinzan/zinzanWalk1.png", 100, 50);
    private static final BufferedImage walk2 = getBufferedImage("sprites/zinzan/zinzanWalk2.png", 100, 50);
    private static final BufferedImage stillBack = getBufferedImage("sprites/zinzan/zinzanStillBack.png", 100, 50);
    private static final BufferedImage walk1Back = getBufferedImage("sprites/zinzan/zinzanWalk1Back.png", 100, 50);
    private static final BufferedImage walk2Back = getBufferedImage("sprites/zinzan/zinzanWalk2Back.png", 100, 50);;
    private static final BufferedImage[] popImages = new BufferedImage[] {
        getBufferedImage("sprites/zinzan/pop/zinzanPop1.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop2.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop3.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop4.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop5.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop6.png", 100, 50)
    };
    private static final BufferedImage[] popBackImages = new BufferedImage[] {
        getBufferedImage("sprites/zinzan/pop/zinzanPop1Back.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop2Back.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop3Back.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop4Back.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop5Back.png", 100, 50),
        getBufferedImage("sprites/zinzan/pop/zinzanPop6Back.png", 100, 50)
    };

    private RoundRectangle2D healthBarOutline;
    private Rectangle2D healthBar;
    private Timer dieTimer;

    private Timer bumpTimer;
    private double bumpVx;
    private boolean bumping = false;
    private int bumpTicks = 0;

    public double Vy = 0;
    public double Vx = 0;
    public double Gravity = 0.2;

    public Cat() {
        x = 150;
        y = 350;
        width = 75;
        height = 50;

        catImage = still;

        healthBarOutline = new RoundRectangle2D.Double();
        healthBarOutline.setRoundRect(100, 10, 200, 20, 10, 10);
        healthBar = new Rectangle2D.Double(102, 12, 196, 16);


    }

    public void setHealth(int health) {
        this.health = health;
        double width = (health / 100.0)*200;
        healthBar.setRect(102, 12, width, 16);
    }
    public int getHealth() {
        return health;
    }

    public void bump(double enemy_Midpoint_x) {
        if (!Dying && !Dead && Vy >= 0) {
            if (bumpTimer != null && bumpTimer.isRunning()) {
                return;
            }
            bumping = true;
            //if (Vy == 0) {
                y -= 5;
                Vy = -2;
            //}
            bumpTimer = new Timer(5, bumpAction);
            if (enemy_Midpoint_x < x + width / 2) {
                bumpVx = 2;
            } else {
                bumpVx = -2;
            }
            bumpTimer.start();
        }
    }
    private ActionListener bumpAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            x += bumpVx;
            bumpTicks++;
            if (bumpTicks >= 25) {
                bumping = false;
                bumpTimer.stop();
                bumpTicks = 0;
            }
        }
    };

    public void entityHit(int healthHit) {
        health -= healthHit;
        if (Dying) {
            return;
        }
        if (health <= 0) {
            startDying();

        } else {
            //
            double width = (health / 100.0)*200;
            //double width = healthBar.getWidth() - 40;
            healthBar.setRect(102, 12, width, 16);
        }
    }

    public void startDying() {
        try {
            popSound.Start();
        }
        catch (Exception ex) {
            System.out.println("pop sound error");
        }

        Dying = true;///////
        healthBar.setRect(102, 12, 0, 16);

        dieTimer = new Timer(30, die);
        dieTimer.setInitialDelay(30);

        if ((state | 110) == 111) {
            popAnimationImages = popBackImages;
        } else {
            popAnimationImages = popImages;
        }
        catImage = popAnimationImages[0];////set forwards or backwards;
        dieTimer.start();
    }

    public Rectangle2D getHitBox() {
        int xAdd = (state | 110) == 111 ? 4 : 15;
        return new Rectangle2D.Double(x + xAdd, y + 10, 55, 35);
    }

    public Fluffball generateFluffball() {
        int vel = (state | 101) == 111 ? 12 + 3 : 12;
        if ((state | 110) == 111) {
            return new Fluffball(x, y + 20, vel, -1);
        } else {
            return new Fluffball(x + 70, y + 20, vel, 1);
        }
    }

    //public void snapToNearestFloor(int)

    public void tryIncrementXY(double dx, double dy) {
        y += dy;
        if (!bumping) {
            x += dx;
            if ((previousDx <= 0 && dx < 0) || (previousDx >= 0 && dx > 0)) {
                frameCount++;
            } else {
                frameCount = 0;
            }
            previousDx = dx;
        }
    }

    public void SetXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public int GetX() {
        return x;
    }
    public int GetY() {
        return y;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (!Dying && !Dead) {
            setSprite();
        }
        g2.drawImage(catImage, x, y, width, height, null);
        //move to Level's Paintcomponent.
        g2.setColor(Color.GREEN);
        g2.fill(healthBar);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.WHITE);
        g2.draw(healthBarOutline);

//        Line2D check = new Line2D.Double();/////////// for development
//        check.setLine(x, y, x, y + 48);
//        g2.draw(check);
    }

    private ActionListener die = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (popIndex < 5) {
                x -= 11;
                y -= 18;
                width += 22;
                height += 22;
                popIndex++;
                catImage = popAnimationImages[popIndex];
            } else {
                dieTimer.stop();
                Dying = false;
                Dead = true;
            }
        }
    };

    private void setSprite() {

        if (state == 010) {
            //walking
            if (frameCount < 10) {
                catImage = walk1;
            } else {
                catImage = walk2;
                if (frameCount >= 20) {
                    frameCount = 0;
                }
            }
        } else if (state == 011) {
            //walking back
            if (frameCount < 10) {
                catImage = walk1Back;
            } else {
                catImage = walk2Back;
                if (frameCount >= 20) {
                    frameCount = 0;
                }
            }
        } else if ((state | 110) == 111) {
            catImage = stillBack;
        } else {
            catImage = still;
        }
    }


}
