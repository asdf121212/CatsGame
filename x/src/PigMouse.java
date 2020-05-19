import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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
    private ArrayList<Node> path;
    private Timer planTimer;
    private Timer traverseTimer;

    private Node nextNode;
    private JumpingRect currentFloor;
    private double xVel = 0;
    private double yVel = 0;
    private final double grav = 0.2;
    private final double jump_yVel = -7;

    private final double defaultVx = 1;

    private boolean jumping = false;
    private boolean fall = false;

    public PigMouse(int x, JumpingRect currentFloor) {
        width = 59;
        height = 60;
        this.x = x;
        //this.y = y;
        this.currentFloor = currentFloor;
        this.y = currentFloor.y - height;
        image = pigMouse1;
        hittable = false;

        planTimer = new Timer(5, plan);
        traverseTimer = new Timer(5, traverse);
        planTimer.start();
        traverseTimer.setInitialDelay(200);
        //planTimer.setInitialDelay(100);
        traverseTimer.start();
    }

    private ActionListener plan = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getHitBox().contains(levelInfo.getCatX(), levelInfo.getCatY())) {
                return;
            }
            if (currentFloor == null || levelInfo.getCatFloor() == null) {
                return;
            }
            path = currentFloor.getShortestPath(x, y, levelInfo.getCatX(),
                    levelInfo.getCatY(), (JumpingRect)levelInfo.getCatFloor(), levelInfo.nodes);
        }
    };
    private ActionListener traverse = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (x > 1300 || x < -100 || y > 800 || y < -100) {
                planTimer.stop();
                traverseTimer.stop();
            }
            if (path == null) {
                return;
            }
            if (levelInfo.getCatFloor() != null && levelInfo.getCatFloor() == currentFloor) {
                nextNode = new Node(levelInfo.getCatX() + 50, levelInfo.getCatY(), (JumpingRect) levelInfo.getCatFloor());
            }
            else if ((nextNode == null || !jumping)) {
                ArrayList<Node> pathClone = (ArrayList<Node>) path.clone();
                if (pathClone == null || pathClone.size() == 0) {
                    return;
                }
                nextNode = pathClone.get(0);
                while (pathClone.size() > 1 && getHitBox().contains(nextNode.x, nextNode.y) && nextNode.owner.equals(currentFloor)) {
                    pathClone.remove(0);
                    nextNode = pathClone.get(0);
                }
                if (pathClone.size() == 0) { return; }
            }

            if (nextNode.x > x + width / 2.0) {
                xVel = defaultVx;
            } else if (nextNode.x < x + width / 2.0) {
                xVel = defaultVx * -1;
            } else {
                xVel = 0;
            }
            if (!nextNode.owner.equals(currentFloor) && !jumping) {
                jumping = true;
                if (nextNode.owner.y >= currentFloor.y) {
                    if (nextNode.owner.contains(currentFloor.x, nextNode.owner.y + 1)
                        || nextNode.owner.contains(currentFloor.x + currentFloor.width, nextNode.owner.y + 1)) {
                        yVel = -1.0;
                    } else {
                        yVel = jump_yVel;
                    }
                } else {
                    yVel = jump_yVel;
                }
            }
            JumpingRect floor = null;
            for (JumpingRect rect : levelInfo.jumpingRects) {
                if (rect.contains(x + width / 2.0 + xVel, y + height + 1 + yVel)) {
                    floor = rect;
                    break;
                }
            }
            if (floor == null && !jumping && !fall) {
                xVel = 0;
            }
            else if (floor == null || yVel < 0) {
                y += yVel;
                yVel += grav;
                xVel *= 2;
            } else {
                yVel = 0;
                y = floor.y - height;
                currentFloor = floor;
                jumping = false;
                fall = false;
            }
            x += xVel;
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
        //return 0;
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
//        g.setColor(Color.white);
//        if (nextNode != null) {
//            ((Graphics2D) g).fill(new Ellipse2D.Double(nextNode.x - 5, nextNode.y -5, 10, 10));
//        }
//        g.setColor(Color.magenta);
//        ((Graphics2D)g).draw(getHitBox());
    }

}
