import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public abstract class Level extends JPanel {

    public static Color backGroundColor = Color.BLACK;

    protected int GroundLevel;
    protected DisplayList displayList;
    protected ZinzanLife[] zinzanLives = new ZinzanLife[] {
            new ZinzanLife(230, 10),
            new ZinzanLife(257, 10),
            new ZinzanLife(284, 10),
            new ZinzanLife(311, 10),
            new ZinzanLife(338, 10)
    };
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

    public RoundRectangle2D[] getWalls() {
        return walls;
    }
    public RoundRectangle2D[] getFloors() {
        return floors;
    }

    public void Dispose() {
        if (displayList.cat != null) {
            displayList.cat.Dispose();
        }
        for (Enemy enemy : displayList.getEnemies()) {
            enemy.Dispose();
        }
        for (catProjectile catProjectile : displayList.getCatProjectiles()) {
            catProjectile.Dispose();
        }
    }

    public void update() {
        ////check if fluffballs hit enemies, remove dead enemies--
        displayList.cat.Update();
        if (displayList.cat.GetY() > 800) {
            displayList.cat.entityHit(200);
        }
        for (Enemy enemy : displayList.getEnemies()) {
            enemy.Update();
            if (enemy.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
                continue;
            } else if (enemy.Dying) {
                continue;
            }
            for (catProjectile catProjectile : displayList.getCatProjectiles()) {
                if (catProjectile.getHitBox().intersects(enemy.getHitBox()) && enemy.hittable) {
                    //fluffball.stop();
                    SwingUtilities.invokeLater(() -> displayList.removeCatProjectile(catProjectile));
                    SwingUtilities.invokeLater(() -> enemy.entityHit(catProjectile.fluffballDamage()));
                }
            }
            if (enemy.getHitBox().intersects(displayList.cat.getHitBox()) && !enemy.hitCoolingDown) {
                if (displayList.cat != null && !(displayList.cat.Dying || displayList.cat.Dead)) {
                    //SwingUtilities.invokeLater(() -> displayList.cat.entityHit(enemy.getContactDamage()));///threw error
                    displayList.cat.entityHit(enemy.getContactDamage());
                    SwingUtilities.invokeLater(() -> displayList.cat.bump(enemy.x + enemy.width / 2.0));
                }
                enemy.hitCat();
            }
        }
        //check if fluffballs are dead or hit a wall
        for (catProjectile catProjectile : displayList.getCatProjectiles()) {
            catProjectile.Update();
            if (catProjectile.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeCatProjectile(catProjectile));
            } else {
                for (RoundRectangle2D wall : walls) {
                    if (catProjectile.getHitBox().intersects(wall.getFrame())) {
                        //fluffball.stop();
                        SwingUtilities.invokeLater(() -> displayList.removeCatProjectile(catProjectile));
                    }
                }
            }
        }

        for (ZinzanLife extraLife : displayList.getExtraLives()) {
            if (displayList.cat.getHitBox().intersects(extraLife.getHitBox())) {
                numLives++;
                SwingUtilities.invokeLater(() -> displayList.removeExtraLife(extraLife));
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

        for (int i = 0; i < numLives && i < zinzanLives.length; i++) {
            zinzanLives[i].paintComponent(g2);
        }

        for (Entity enemy : displayList.getEnemies()) {
            enemy.paintComponent(g2);
        }
        for (catProjectile catProjectile : displayList.getCatProjectiles()) {
            catProjectile.paintComponent(g2);
        }
        if (displayList.cat != null && !displayList.cat.Dead) {
            displayList.cat.paintComponent(g2);
        }

        for (ZinzanLife life : displayList.getExtraLives()) {
            life.paintComponent(g2);
        }


    }



    public void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    //protected abstract int getGroundLevel(int xCoord, int yCoord);

}
