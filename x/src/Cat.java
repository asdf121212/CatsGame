import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;


public class Cat extends Entity {

    public byte state = 000;
    private int Health = 100;
    private BufferedImage catImage;
    private int frameCount = 0;
    private int previousDx = 0;
    private BufferedImage[] popAnimationImages;
    int popIndex = 0;

    private static final BufferedImage still = getBufferedImage("sprites/zinzanStill.png", 100, 50);
    private static final BufferedImage walk1 = getBufferedImage("sprites/zinzanWalk1.png", 100, 50);
    private static final BufferedImage walk2 = getBufferedImage("sprites/zinzanWalk2.png", 100, 50);
    private static final BufferedImage stillBack = getBufferedImage("sprites/zinzanStillBack.png", 100, 50);
    private static final BufferedImage walk1Back = getBufferedImage("sprites/zinzanWalk1Back.png", 100, 50);
    private static final BufferedImage walk2Back = getBufferedImage("sprites/zinzanWalk2Back.png", 100, 50);;
    private static BufferedImage[] popImages = new BufferedImage[] {
        getBufferedImage("sprites/zinzanPop1.png", 100, 50),
        getBufferedImage("sprites/zinzanPop2.png", 100, 50),
        getBufferedImage("sprites/zinzanPop3.png", 100, 50),
        getBufferedImage("sprites/zinzanPop4.png", 100, 50),
        getBufferedImage("sprites/zinzanPop5.png", 100, 50),
        getBufferedImage("sprites/zinzanPop6.png", 100, 50)
    };
    private static BufferedImage[] popBackImages = new BufferedImage[] {
        getBufferedImage("sprites/zinzanPop1Back.png", 100, 50),
        getBufferedImage("sprites/zinzanPop2Back.png", 100, 50),
        getBufferedImage("sprites/zinzanPop3Back.png", 100, 50),
        getBufferedImage("sprites/zinzanPop4Back.png", 100, 50),
        getBufferedImage("sprites/zinzanPop5Back.png", 100, 50),
        getBufferedImage("sprites/zinzanPop6Back.png", 100, 50)
    };

    private RoundRectangle2D healthBarOutline;
    private Rectangle2D healthBar;
    private Timer dieTimer;

    public Cat() {
        x = 150;
        y = 350;
        width = 75;
        height = 50;


        catImage = still;

        healthBarOutline = new RoundRectangle2D.Double();
        healthBarOutline.setRoundRect(900, 50, 200, 20, 10, 10);
        healthBar = new Rectangle2D.Double(902, 52, 196, 16);


    }

//    public boolean isAlive() {
//        return Health > 0;
//    }

    public void catHit() {
        Health -= 20;
        if (Health <= 0) {
            Dying = true;///////
            healthBar.setRect(902, 52, 0, 16);

            dieTimer = new Timer(30, die);
            dieTimer.setInitialDelay(30);

            if ((state | 110) == 111) {
                popAnimationImages = popBackImages;
            } else {
                popAnimationImages = popImages;
            }
            catImage = popAnimationImages[0];////set forwards or backwards;
            dieTimer.start();
        } else {
            double width = healthBar.getWidth() - 40;
            healthBar.setRect(902, 52, width, 16);
        }
    }

    public void die() {

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

    public void IncrementXY(int dx, int dy) {
        x += dx;
        y += dy;
        if ((previousDx <= 0 && dx < 0) || (previousDx >= 0 && dx > 0)) {
            frameCount++;
        } else {
            frameCount = 0;
        }
        previousDx = dx;
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

        g2.setColor(Color.GREEN);
        g2.fill(healthBar);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.WHITE);
        g2.draw(healthBarOutline);
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
            if (frameCount < 7) {
                catImage = walk1;
            } else {
                catImage = walk2;
                if (frameCount == 14) {
                    frameCount = 0;
                }
            }
        }else if (state == 011) {
            //walking back
            if (frameCount < 7) {
                catImage = walk1Back;
            } else {
                catImage = walk2Back;
                if (frameCount == 14) {
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
