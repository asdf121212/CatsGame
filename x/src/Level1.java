import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Level1 extends Level {



    private Shape ground;



    public Level1() {

        GroundLevel = 350;

        setPreferredSize(new Dimension(1200, 700));

        displayList = new DisplayList();
        setBackground(Color.BLACK);
        ground = new Rectangle2D.Double(0, GroundLevel + 50, 1200, 300);
        displayList.AddBackgroundShape(ground);
        Squirrel squirrel = new Squirrel();
        squirrel.x = 1000;
        squirrel.y = 280;
        displayList.AddEnemy(squirrel);

        setFocusable(true);


    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.MAGENTA);
        g2.fill(ground);

        for (Shape shape : displayList.getBackgroundShapes()) {
            g2.draw(shape);
        }

        for (Entity enemy : displayList.getEnemies()) {
            enemy.paintComponent(g2);
        }
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
