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
    //private Timer dieTimer;
    private int dieTicks = 0;

    private LevelInfo levelInfo;
    //private Timer moveTimer;

    //private Timer bumpTimer;
    private double bumpVx;
    private boolean bumping = false;
    private int bumpTicks = 0;

    public double Vy = 0;
    public double Vx = 0;
    public double Gravity = 0.2;

    public RoundRectangle2D currentFloor;

    public Cat() {
        x = 150;
        y = 350;
        width = 75;
        height = 50;

        catImage = still;

        healthBarOutline = new RoundRectangle2D.Double();
        healthBarOutline.setRoundRect(10, 10, 200, 20, 10, 10);
        healthBar = new Rectangle2D.Double(12, 12, 196, 16);
    }

    public void AddLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }
//    public void enable() {
//        if (levelInfo != null) {
//            if (moveTimer != null) {
//                moveTimer.stop();
//            }
//            moveTimer = new Timer(5, move);
//            moveTimer.start();
//        }
//    }

    public void Dispose() {
        //if (bumpTimer != null) {
            //bumpTimer.stop();
        //}
//        if (dieTimer != null) {
//            dieTimer.stop();
//        }
//        if (moveTimer != null) {
//            moveTimer.stop();
//        }
    }

    @Override
    public void Update() {
        if (Dying) {
            die();
        } else {
            move();
        }
    }

    public void setHealth(int health) {
        this.health = health;
        double width = (health / 100.0)*200;
        healthBar.setRect(12, 12, width, 16);
    }
    public int getHealth() {
        return health;
    }

    public void bump(double enemy_Midpoint_x) {
        if (! bumping && !Dying && !Dead && Vy >= 0) {
            bumping = true;
            bumpTicks = 0;
            //if (Vy == 0) {
                y -= 5;
                Vy = -2;
            //}
            //bumpTimer = new Timer(5, bumpAction);
            if (enemy_Midpoint_x < x + width / 2.0) {
                bumpVx = 2;
            } else {
                bumpVx = -2;
            }
            //bumpTimer.start();
        }
    }
//    private ActionListener bumpAction = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            //x += bumpVx;
//            bumpTicks++;
//            if (bumpTicks >= 25) {
//                bumping = false;
//                bumpTimer.stop();
//                bumpTicks = 0;
//            }
//        }
//    };

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
            healthBar.setRect(12, 12, width, 16);
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
        healthBar.setRect(12, 12, 0, 16);

//        if (moveTimer != null) {
//            moveTimer.stop();
//        }

        //dieTimer = new Timer(30, die);
        //dieTimer.setInitialDelay(30);

        if ((state | 110) == 111) {
            popAnimationImages = popBackImages;
        } else {
            popAnimationImages = popImages;
        }
        catImage = popAnimationImages[0];////set forwards or backwards;
        //dieTimer.start();
    }

    public Rectangle2D getHitBox() {
        int xAdd = (state | 110) == 111 ? 4 : 15;
        return new Rectangle2D.Double(x + xAdd, y + 10, 55, 35);
    }

    public Fluffball generateFluffball() {
        double vel = (state | 101) == 111 ? 9.5 : 7.5;
        if ((state | 110) == 111) {
            return new Fluffball(x, y + 20, vel, -1);
        } else {
            return new Fluffball(x + 70, y + 20, vel, 1);
        }
    }

    //public void snapToNearestFloor(int)

    private void move() {
        if (levelInfo == null) {
            return;
        }
        if (bumping) {
            bumpTicks++;
            if (bumpTicks >= 25) {
                bumping = false;
                bumpTicks = 0;
            }
        }

        double floorCheckX1 = x + 20;
        double floorCheckX2 = x + 55;
        double wallCheckX1 = x;
        double wallCheckX2 = x + 75;
        RoundRectangle2D floor_Rect = null;
        RoundRectangle2D wall_Rect = null;
        ///maybe add -- if (currentFloor == null || currentFloor.contains(floorCheckX1))
        double tempVx = bumping ? bumpVx : Vx;
        for (RoundRectangle2D floor : levelInfo.floors) {
            if ((floor.contains(floorCheckX1, y + 51 + Vy)
                    || floor.contains(floorCheckX2, y + 51 + Vy)) && floor.getY() > y) {
                floor_Rect = floor;
                break;
            }
        }
        for (RoundRectangle2D wall : levelInfo.walls) {
            if (wall.contains(wallCheckX1, y + 25) && tempVx < 0) {
                wall_Rect = wall;
                break;
            } else if (wall.contains(wallCheckX2, y + 25) && tempVx > 0) {
                wall_Rect = wall;
                break;
            }
        }
        if (floor_Rect == null || Vy < 0) {
            state = (byte)(state | 100);
            Vy += Gravity;
            //displayList.cat.currentFloor = null;
        } else {
            if (Vy >= 0) {
                //make sure it's level with floor
                y = (int)Math.round(floor_Rect.getY()) - 50;
                currentFloor = floor_Rect;
            }
            Vy = 0;
            state = (byte)(state & 011);
        }
        if (wall_Rect != null) {
            tempVx = 0;
        }
        ///////////////////////////////////////////////////////////////////////////////////
        if (!Dying && !Dead) {
            tryIncrementXY(tempVx, Vy);
        }
    }

//    private ActionListener move = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            if (bumping) {
//                bumpTicks++;
//                if (bumpTicks >= 25) {
//                    bumping = false;
//                    bumpTicks = 0;
//                }
//            }
//
//            double floorCheckX1 = x + 20;
//            double floorCheckX2 = x + 55;
//            double wallCheckX1 = x;
//            double wallCheckX2 = x + 75;
//            RoundRectangle2D floor_Rect = null;
//            RoundRectangle2D wall_Rect = null;
//            ///maybe add -- if (currentFloor == null || currentFloor.contains(floorCheckX1))
//            double tempVx = bumping ? bumpVx : Vx;
//            for (RoundRectangle2D floor : levelInfo.floors) {
//                if ((floor.contains(floorCheckX1, y + 51 + Vy)
//                        || floor.contains(floorCheckX2, y + 51 + Vy)) && floor.getY() > y) {
//                    floor_Rect = floor;
//                    break;
//                }
//            }
//            for (RoundRectangle2D wall : levelInfo.walls) {
//                if (wall.contains(wallCheckX1, y + 25) && tempVx < 0) {
//                    wall_Rect = wall;
//                    break;
//                } else if (wall.contains(wallCheckX2, y + 25) && tempVx > 0) {
//                    wall_Rect = wall;
//                    break;
//                }
//            }
//            if (floor_Rect == null || Vy < 0) {
//                state = (byte)(state | 100);
//                Vy += Gravity;
//                //displayList.cat.currentFloor = null;
//            } else {
//                if (Vy >= 0) {
//                    //make sure it's level with floor
//                    y = (int)Math.round(floor_Rect.getY()) - 50;
//                    currentFloor = floor_Rect;
//                }
//                Vy = 0;
//                state = (byte)(state & 011);
//            }
//            if (wall_Rect != null) {
//                tempVx = 0;
//            }
//            ///////////////////////////////////////////////////////////////////////////////////
//            if (!Dying && !Dead) {
//                tryIncrementXY(tempVx, Vy);
//            }
//        }
//    };

    public void tryIncrementXY(double dx, double dy) {
        y += dy;
        //if (!bumping) {
        x += dx;
        if ((previousDx <= 0 && dx < 0) || (previousDx >= 0 && dx > 0)) {
            frameCount++;
        } else {
            frameCount = 0;
        }
        previousDx = dx;
        //}
    }

    public void SetXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public double GetX() {
        return x;
    }
    public double GetY() {
        return y;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (!Dying && !Dead) {
            setSprite();
        }
        g2.drawImage(catImage, (int)Math.round(x), (int)Math.round(y), width, height, null);
        //move to Level's Paintcomponent.
        g2.setColor(Color.GREEN);
        g2.fill(healthBar);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.WHITE);
        g2.draw(healthBarOutline);

    }

    private void die() {
        if (dieTicks >= 6) {
            if (popIndex < 5) {
                x -= 11;
                y -= 18;
                width += 22;
                height += 22;
                popIndex++;
                catImage = popAnimationImages[popIndex];
            } else {
                //dieTimer.stop();
                Dying = false;
                Dead = true;
            }
            dieTicks = 0;
        } else {
            dieTicks++;
        }
    }

//    private ActionListener die = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (popIndex < 5) {
//                x -= 11;
//                y -= 18;
//                width += 22;
//                height += 22;
//                popIndex++;
//                catImage = popAnimationImages[popIndex];
//            } else {
//                dieTimer.stop();
//                Dying = false;
//                Dead = true;
//            }
//        }
//    };

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
