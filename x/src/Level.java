import com.sun.deploy.pings.Pings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public abstract class Level extends JPanel {


    //protected final float GRAV = 0.25f;
    protected int GroundLevel;
    protected DisplayList displayList;
    protected ZinzanLife[] zinzanLives;
    protected static int numLives = 3;
    protected boolean reachedNextLevel = false;

    protected int levelWidth = 1200;
    protected int levelHeight = 700;

    protected RoundRectangle2D[] walls;
    protected RoundRectangle2D[] floors;
    protected MovingRectangle[] movingRectangles;

    public boolean hasReachedNextLevel() {
        return reachedNextLevel;
    }
    //public float getGRAV() {
        //return GRAV;
    //}


    public RoundRectangle2D[] getWalls() {
        //walls.clone() ?
        return walls;
    }
    public RoundRectangle2D[] getFloors() {
        return floors;
    }

    public void Dispose() {
        displayList.cat.Dispose();
        for (Enemy enemy : displayList.getEnemies()) {
            enemy.Dispose();
        }
        for (Fluffball fluffball : displayList.getFluffballs()) {
            fluffball.Dispose();
        }
    }

    public void update() {
        ////check if fluffballs hit enemies, remove dead enemies--
        if (displayList.cat.GetY() > 800) {
            displayList.cat.entityHit(200);
        }
        for (Enemy enemy : displayList.getEnemies()) {
            if (enemy.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
                continue;
            } else if (enemy.Dying) {
                continue;
            }
            for (Fluffball fluffball : displayList.getFluffballs()) {
                if (fluffball.getHitBox().intersects(enemy.getHitBox()) && enemy.hittable) {
                    fluffball.stop();
                    SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
                    SwingUtilities.invokeLater(() -> enemy.entityHit(fluffball.fluffballDamage()));
                }
            }
            if (enemy.getHitBox().intersects(displayList.cat.getHitBox()) && !enemy.hitCoolingDown) {
                if (displayList.cat != null && !(displayList.cat.Dying || displayList.cat.Dead)) {
                    //SwingUtilities.invokeLater(() -> displayList.cat.entityHit(enemy.getContactDamage()));///threw error
                    displayList.cat.entityHit(enemy.getContactDamage());
                    SwingUtilities.invokeLater(() -> displayList.cat.bump(enemy.x + enemy.width / 2));
                }
                enemy.hitCat();
            }
        }
        //check if fluffballs are dead or hit a wall
        for (Fluffball fluffball : displayList.getFluffballs()) {
            if (fluffball.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
            } else {
                for (RoundRectangle2D wall : walls) {
                    if (fluffball.getHitBox().intersects(wall.getFrame())) {
                        fluffball.stop();
                        SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
                    }
                }
            }
        }

        if (movingRectangles != null) {
            for (MovingRectangle movingRect : movingRectangles) {
                movingRect.update();
                if (displayList.cat.GetY() == movingRect.getY() - displayList.cat.height) {
                    double x = displayList.cat.GetX();
                    if (x >= movingRect.getX() - 50 && x <= movingRect.getX() + 50) {
                        displayList.cat.tryIncrementXY(movingRect.getXVel(), 0);
                        //displayList.cat.Vx += movingRect.getXVel();
                    }
                }
            }
        }
    }

    protected void paintDisplayList(Graphics2D g2) {

//        for (Shape shape : displayList.getBackgroundShapes()) {
//            g2.draw(shape);
//        }
        for (Entity enemy : displayList.getEnemies()) {
            enemy.paintComponent(g2);
        }
        for (Fluffball fluffball : displayList.getFluffballs()) {
            fluffball.paintComponent(g2);
        }
        if (displayList.cat != null) {
            displayList.cat.paintComponent(g2);
        }
//        for (Entity danger : displayList.getDangers()) {
//            danger.paintComponent(g2);
//        }


    }



    public void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    //protected abstract int getGroundLevel(int xCoord, int yCoord);

}
