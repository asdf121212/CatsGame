import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PigMouse extends Enemy {

    private BufferedImage pigMouse1 = Entity.getBufferedImage("sprites/pigMouse/pigMouse.png", 100, 100);
    private BufferedImage pigMouseHot1 = Entity.getBufferedImage("sprites/pigMouse/pigMouseHot1.png", 100, 100);
    private BufferedImage pigMouseHot2 = Entity.getBufferedImage("sprites/pigMouse/pigMouseHot2.png", 100, 100);
    private BufferedImage pigMouseHot3 = Entity.getBufferedImage("sprites/pigMouse/pigMouseHot3.png", 100, 100);

    private BufferedImage[] images = new BufferedImage[] {
            pigMouse1,
            pigMouseHot1,
            pigMouseHot2,
            pigMouseHot3
    };
    private int imageIndex;
    private boolean heating = false;
    private boolean cooling = false;
    private int heatTicks = 0;

    private LevelInfo levelInfo;
    private ArrayList<Node> path;
    //private Timer planTimer;
    //private Timer traverseTimer;

    private Node nextNode;
    private NodeFloor currentFloor;
    private double xVel = 0;
    private double yVel = 0;
    private final double grav = 0.2;
    private final double jump_yVel = -6.5;

    private final double defaultVx = 1.4;

    private boolean jumping = false;
    private boolean fall = false;
    public boolean needsToRespawn = false;

    //private Timer initialFallTimer;
    private boolean initialFalling;

    public PigMouse(int x, int y) {
        width = 59;
        height = 60;
        this.x = x;
        //this.currentFloor = currentFloor;
        //this.y = currentFloor.y - height;
        this.y = y;
        imageIndex = 0;
        hittable = false;
        //initialFallTimer = new Timer(5, initialFall);
        //initialFallTimer.start();
        initialFalling = true;
    }


    //private ActionListener initialFall = new ActionListener() {
        //@Override
        //public void actionPerformed(ActionEvent e) {
    @Override
    public void Update() {
        if (initialFalling) {
            initialFall();
        } else {
            plan();
            traverse();
        }
    }


    public void initialFall() {
        while (levelInfo == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        if (x > 1300 || x < -200 || y > 900 || y < -1000) {
            needsToRespawn = true;
        }
        NodeFloor floor = null;
        for (NodeFloor rect : levelInfo.nodeFloors) {
            if (rect.contains(x + width / 2.0 + xVel, y + height + 1 + yVel)) {
                floor = rect;
                break;
            }
        }
        if (floor == null) {
            y += yVel;
            yVel += grav;
        } else {
            yVel = 0;
            y = floor.y - height;
            currentFloor = floor;

            initialFalling = false;

//            initialFallTimer.stop();
//            planTimer = new Timer(5, plan);
//            traverseTimer = new Timer(5, traverse);
//            planTimer.start();
//            traverseTimer.setInitialDelay(200);
//            traverseTimer.start();

        }
    }
        //}
    //};

    //private ActionListener plan = new ActionListener() {
        //@Override
        //public void actionPerformed(ActionEvent e) {
    private void plan() {
            if (getHitBox().contains(levelInfo.getCatX(), levelInfo.getCatY())) {
                return;
            }
            if (currentFloor == null || levelInfo.getCatFloor() == null) {
                return;
            }
            path = currentFloor.getShortestPath(x, y, levelInfo.getCatX(),
                    levelInfo.getCatY(), (NodeFloor)levelInfo.getCatFloor(), levelInfo.nodes);
        }
    //};
    //private ActionListener traverse = new ActionListener() {
        //@Override
        //public void actionPerformed(ActionEvent e) {
    private void traverse() {
            if (x > 1300 || x < -200 || y > 900 || y < -1000) {
                //planTimer.stop();
                //traverseTimer.stop();
                needsToRespawn = true;
            }
            if (path == null) {
                return;
            }

            double xDist = Math.abs(levelInfo.getCatX() - x);
            double yDist = Math.abs(levelInfo.getCatY() - y);
            if (xDist >= 200 || yDist > 100) {
                imageIndex = 0;
                if (heating) {
                    heating = false;
                    cooling = true;
                    heatTicks = 0;
                }
            } else {
                if (heating) {
                    if (imageIndex < 3) {
                        heatTicks++;
                    }
                    if (heatTicks >= 10) {
                        imageIndex++;
                        heatTicks = 0;
                    }
                } else if (cooling) {
                    if (imageIndex > 0) {
                        heatTicks++;
                    } else {
                        cooling = false;
                    }
                    if (heatTicks >= 10) {
                        imageIndex--;
                        heatTicks = 0;
                    }
                } else {
                    heating = true;
                    imageIndex++;
                }
            }

            if (levelInfo.getCatFloor() != null && levelInfo.getCatFloor() == currentFloor) {
                nextNode = new Node(levelInfo.getCatX() + 50, levelInfo.getCatY(), (NodeFloor) levelInfo.getCatFloor());

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
            if (levelInfo.getCatFloor() != null && currentFloor != null && currentFloor == levelInfo.getCatFloor()) {

            } else {

            }

            if (nextNode.x >= x + width / 2.0) {
                xVel = defaultVx;
            } else if (nextNode.x < x + width / 2.0) {
                xVel = defaultVx * -1;
            } else {
                xVel = 0;
            }
            if (!nextNode.owner.equals(currentFloor) && !jumping) {
                jumping = true;
                if (nextNode.owner.y >= currentFloor.y + 10) {
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
            NodeFloor floor = null;
            for (NodeFloor rect : levelInfo.nodeFloors) {
                if (rect.contains(x + width / 2.0 + xVel, y + height + 1 + yVel)) {
                    floor = rect;
                    break;
                }
            }
            if (jumping && floor != nextNode.owner) {
                floor = null;
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
    //};


    public void addLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public void Dispose() {
//        if (planTimer != null) {
//            planTimer.stop();
//        }
//        if (traverseTimer != null) {
//            traverseTimer.stop();
//        }
//        if (initialFallTimer != null) {
//            initialFallTimer.stop();
//        }
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
        ((Graphics2D)g).drawImage(images[imageIndex], (int)Math.round(x), (int)Math.round(y), width, height, null);
//        g.setColor(Color.white);
//        if (nextNode != null) {
//            ((Graphics2D) g).fill(new Ellipse2D.Double(nextNode.x - 5, nextNode.y -5, 10, 10));
//        }
//        g.setColor(Color.magenta);
//        ((Graphics2D)g).draw(getHitBox());
    }

}
