import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Yarnball extends Enemy {

    private static BufferedImage yarnball0 = Entity.getBufferedImage("sprites/yarnBall/yarnBall-0.png", 140, 140);
    private static BufferedImage yarnball1 = Entity.getBufferedImage("sprites/yarnBall/yarnBall-1.png", 140, 140);
    private static BufferedImage yarnball2 = Entity.getBufferedImage("sprites/yarnBall/yarnBall-2.png", 140, 140);

    private int imageChangeTicks = 0;
    private Timer attackTimer;
    private Timer dieTimer;
    private BufferedImage image = yarnball0;
    private double Range;

    public Yarnball(int x, int y, double range) {
        this.x = x;
        this.y = y;
        width = 120;
        height = 120;
        this.Range = range;
    }

    public int getContactDamage() {
        return 50;
    }

    public void Start() {
        attackTimer = new Timer(10, attack);
        attackTimer.start();
    }

    public boolean enteredAttackZone(double xCoord, double yCoord) {
        if ((attackTimer != null && attackTimer.isRunning()) || Dead || Dying) {
            return false;
        } else {
            return x - xCoord < Range && yCoord <= y + width + 10 && yCoord >= y - 10;
        }
    }

    @Override
    public void entityHit() {
        health -= 25;
        if (health <= 0) {
            Dying = true;
            attackTimer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, x, y, width, height, null);
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 5, y + 5, width, height);
    }

    private ActionListener attack = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageChangeTicks < 7) {
                imageChangeTicks++;
            } else if (imageChangeTicks < 14) {
                image = yarnball1;
                imageChangeTicks++;
            } else {
                image = yarnball2;
            }
            x -= 4;
            if (x < -70) {
                Dead = true;
                attackTimer.stop();
            }
        }
    };


}
