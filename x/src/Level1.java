import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Level1 extends Level {

    private RoundRectangle2D ground;
    private int tickCount = 0;
    private Squirrel squirrel;

    public Level1() {


        GroundLevel = 350;

        setPreferredSize(new Dimension(levelWidth, levelHeight));

        displayList = new DisplayList();
        setBackground(Color.BLACK);
        ground = new RoundRectangle2D.Double(0, GroundLevel + 50, 1250, 300, 0, 0);
        displayList.cat.SetXY(150, 350);
        squirrel = new Squirrel();
        squirrel.x = 1000;
        squirrel.y = 280;
        displayList.AddEnemy(squirrel);
        setFocusable(true);

        zinzanLives = new ZinzanLife[] {
                new ZinzanLife(10, 10),
                new ZinzanLife(37, 10),
                new ZinzanLife(64, 10),
        };

        floors = new RoundRectangle2D[] {
                ground
        };
        walls = new RoundRectangle2D[] {

        };

    }

    @Override
    public void update() {
        ////make the squirrel shoot balls
        if (squirrel != null && !squirrel.Dying && !squirrel.Dead) {
            if (tickCount == 220) {
                SwingUtilities.invokeLater(() -> displayList.AddEnemy(squirrel.generateBall(-5)));
                tickCount = 0;
            } else {
                tickCount++;
            }
        }

        // do check if reached end of level
        if (displayList.cat.x > 1165) {
            reachedNextLevel = true;
        }

        super.update();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        ///might have to make shape class for filling in shapes easily
        g2.setColor(Color.MAGENTA);
        g2.fill(ground);


        for (int i = 0; i < numLives; i++) {
            zinzanLives[i].paintComponent(g);
        }

        paintDisplayList(g2);


    }


}
