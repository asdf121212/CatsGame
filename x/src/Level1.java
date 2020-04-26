import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class Level1 extends Level {

    private Shape ground;
    private int tickCount = 0;

    public Level1() {


        GroundLevel = 350;

        setPreferredSize(new Dimension(levelWidth, levelHeight));

        displayList = new DisplayList();
        setBackground(Color.BLACK);
        ground = new Rectangle2D.Double(0, GroundLevel + 50, 1200, 300);
        displayList.AddBackgroundShape(ground);
        Squirrel squirrel = new Squirrel();
        squirrel.x = 1000;
        squirrel.y = 280;
        displayList.AddEnemy(squirrel);
        setFocusable(true);

        zinzanLives = new ZinzanLife[] {
                new ZinzanLife(10, 10),
                new ZinzanLife(37, 10),
                new ZinzanLife(64, 10),
        };

    }

    public void update() {
        ////make the squirrel shoot balls
        if (displayList.getEnemies().size() == 1 && !displayList.getEnemies().get(0).Dying) {
            Squirrel squirrel = (Squirrel) displayList.getEnemies().get(0);/////////not good structure- should refactor
            if (tickCount == 220) {
                SwingUtilities.invokeLater(() -> displayList.AddDanger(squirrel.generateBall(-5)));
                tickCount = 0;
            } else {
                tickCount++;
            }
        }
        super.update();
    }

    public int getGroundLevel(int xCoord, int yCoord) {
        return 350;
    }

    //public void mouseClick(int x, int y) {
        //return;
    //}

    ///level is part of the "View" not the model
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.MAGENTA);
        g2.fill(ground);


        for (int i = 0; i < numLives; i++) {
            zinzanLives[i].paintComponent(g);
        }

        for (Shape shape : displayList.getBackgroundShapes()) {
            g2.draw(shape);
        }

        for (Entity enemy : displayList.getEnemies()) {
            enemy.paintComponent(g2);
        }

        ///////this needs to be in level specific update.
        for (Fluffball fluffball : displayList.getFluffballs()) {
            if (fluffball.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
            } else {
                fluffball.paintComponent(g2);
            }
        }
        if (displayList.cat != null) {
            displayList.cat.paintComponent(g2);
        }
        for (Entity danger : displayList.getDangers()) {
            danger.paintComponent(g2);
        }

    }


}
