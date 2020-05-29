import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class TinyMouse extends Enemy {

    private BufferedImage mouseImage_L = Entity.getBufferedImage("sprites/tinyMouse/tinyMouse1_L.png", 30, 30);
    private BufferedImage mouseImageHit1_L = Entity.getBufferedImage("sprites/tinyMouse/tinyMouseHit1_L.png", 30, 30);
    private BufferedImage mouseImageHit2_L = Entity.getBufferedImage("sprites/tinyMouse/tinyMouseHit2_L.png", 30, 30);
    private BufferedImage mouseImage_R = Entity.getBufferedImage("sprites/tinyMouse/tinyMouse1_R.png", 30, 30);
    private BufferedImage mouseImageHit1_R = Entity.getBufferedImage("sprites/tinyMouse/tinyMouseHit1_R.png", 30, 30);
    private BufferedImage mouseImageHit2_R = Entity.getBufferedImage("sprites/tinyMouse/tinyMouseHit2_R.png", 30, 30);

    //private static AutoResetSound hitSound = new AutoResetSound("SoundFiles/slap.wav");

    private double xVel;
    private int right_xBound;
    private int left_xBound;
    //private Timer moveTimer;
    //private Timer hitTimer;

    private int hitTicks = 0;
    private int tickLimit = 0;
    private boolean hitting = false;

    private BufferedImage image;
    int cooldownTicks = 0;

    public TinyMouse(int left_xBound, int right_xBound, int y) {
        hittable = false;
        this.x = left_xBound;
        this.y = y;
        this.right_xBound = right_xBound;
        this.left_xBound = left_xBound;
        image = mouseImage_R;
        width = 60;
        height = 25;
    }

    @Override
    public void Update() {
        if (hitting) {
            if (hitTicks >= tickLimit) {
                if (image.equals(mouseImageHit2_L)) {
                    image = mouseImageHit1_L;
                    tickLimit = 30;
                    hitTicks = 0;
                } else if (image.equals(mouseImageHit2_R)) {
                    image = mouseImageHit1_R;
                    tickLimit = 30;
                    hitTicks = 0;
                } else if (image.equals(mouseImageHit1_L)) {
                    image = mouseImage_L;
                    //hitTimer.stop();
                    hitting = false;
                } else if (image.equals(mouseImageHit1_R)) {
                    image = mouseImage_R;
                    //hitTimer.stop();
                    hitting = false;
                }
            } else {
                hitTicks++;
            }
        }
        move();
    }

//    public void Start() {
//        Dispose();
//        moveTimer = new Timer(5, move);
//        moveTimer.start();
//    }

    public void setLeft_xBound(int left_xBound) {
        this.left_xBound = left_xBound;
    }
    public void setRight_xBound(int right_xBound) {
        this.right_xBound = right_xBound;
    }
    public double getRightBound() {
        return right_xBound;
    }

    public void Dispose() {
//        if (hitTimer != null) {
//            hitTimer.stop();
//        }
//        if (moveTimer != null) {
//            moveTimer.stop();
//        }
    }

    //public ActionListener move = new ActionListener() {
        //@Override
        //public void actionPerformed(ActionEvent e) {
    private void move() {
            if (cooldownTicks > 0) {
                cooldownTicks--;
                if (cooldownTicks == 0) {
                    hitCoolingDown = false;
                }
            }
            if (x >= right_xBound) {
                image = mouseImage_L;
                xVel = -3;
            } else if (x <= left_xBound) {
                image = mouseImage_R;
                xVel = 3;
            }
            x += xVel;
        }
    //};
//    public void update() {
//
//    }

    @Override
    public int getContactDamage() {
        if (cooldownTicks <= 0) {
            return 25;
        } else {
            return 0;
        }
    }
    @Override
    public void hitCat() {
        if (cooldownTicks <= 0) {
            //hitSound.Start();
            //hitTimer = new Timer(150, hit);
            //hitTimer.setInitialDelay(180);
            if (xVel < 0) {
                image = mouseImageHit2_L;
            } else {
                image = mouseImageHit2_R;
            }
            //hitTimer.start();
            hitting = true;
            hitTicks = 0;
            tickLimit = 36;
            hitCoolingDown = true;
            cooldownTicks = 80;
        }
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }
    @Override
    public void entityHit(int damage) {
        //do nothing
    }
    @Override
    public void startDying() {

    }

//    private ActionListener hit = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (image.equals(mouseImageHit1_L)) {
//                image = mouseImageHit2_L;
//            } else if (image.equals(mouseImageHit1_R)) {
//                image = mouseImageHit2_R;
//            } else if (image.equals(mouseImageHit2_L)) {
//                image = mouseImage_L;
//                hitTimer.stop();
//            } else if (image.equals(mouseImageHit2_R)) {
//                image = mouseImage_R;
//                hitTimer.stop();
//            }
//        }
//    };

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }


}
