import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Level extends JPanel {

    protected final float GRAV = 0.5f;
    protected int GroundLevel;
    protected DisplayList displayList;
    protected ZinzanLife[] zinzanLives;
    protected int numLives = 3;
    protected boolean reachedNextLevel = false;
    //public int getGroundLevel() {
        //return GroundLevel;
    //}
    public boolean hasReachedNextLevel() {
        return reachedNextLevel;
    }
    public float getGRAV() {
        return GRAV;
    }
    protected int levelWidth = 1200;
    protected int levelHeight = 700;

    public void update() {
        ////check if fluffballs hit enemies, remove dead enemies--- kill cat if cat touches enemy
        for (Enemy enemy : displayList.getEnemies()) {
            if (enemy.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
                continue;
            } else if (enemy.Dying) {
                continue;
            }
            for (Fluffball fluffball : displayList.getFluffballs()) {
                if (fluffball.getHitBox().intersects(enemy.getHitBox())) {
                    fluffball.stop();
                    SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
                    SwingUtilities.invokeLater(enemy::entityHit);
                }
            }
            if (enemy.getHitBox().intersects(displayList.cat.getHitBox())) {
                if (displayList.cat != null && !(displayList.cat.Dying || displayList.cat.Dead)) {
                    SwingUtilities.invokeLater(() -> displayList.cat.catHit(200));///threw error
                }
            }
        }

        ////check if projectiles hit cat, remove dead dangers
        for (Danger danger : displayList.getDangers()) {
            if (danger.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
            } else if (danger.Dying) {
                continue;
            } else {
                if (danger.getHitBox().intersects(displayList.cat.getHitBox())) {
                    SwingUtilities.invokeLater(() -> displayList.cat.catHit(danger.getDamage()));//add
                    danger.hitTarget();
                }
            }
        }
        for (Fluffball fluffball : displayList.getFluffballs()) {
            if (fluffball.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
            }
        }

    }

    protected void paintDisplayList(Graphics2D g2) {

        for (Shape shape : displayList.getBackgroundShapes()) {
            g2.draw(shape);
        }
        for (Entity enemy : displayList.getEnemies()) {
            enemy.paintComponent(g2);
        }
        for (Fluffball fluffball : displayList.getFluffballs()) {
            fluffball.paintComponent(g2);
        }
        if (displayList.cat != null) {
            displayList.cat.paintComponent(g2);
        }
        for (Entity danger : displayList.getDangers()) {
            danger.paintComponent(g2);
        }
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//    }

    public void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    //protected abstract void mouseClick(int x, int y);

    ///should be abstract
    protected abstract int getGroundLevel(int xCoord, int yCoord);

}
