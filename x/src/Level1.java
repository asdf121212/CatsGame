import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Level1 extends TutorialLevel {

    private RoundRectangle2D ground;
    private int tickCount = 0;
    private Squirrel squirrel;
    private BufferedImage rightArrow = Entity.getBufferedImage("sprites/menuImages/rightArrow.png", 300, 100);

    public Level1() {


        GroundLevel = 350;

        setPreferredSize(new Dimension(levelWidth, levelHeight));

        displayList = new DisplayList();
        setBackground(Color.BLACK);
        ground = new RoundRectangle2D.Double(0, GroundLevel + 50, 1250, 300, 0, 0);
        displayList.cat.SetXY(0, 350);
        squirrel = new Squirrel(1000, 280);
        //squirrel.x = 1000;
        //squirrel.y = 280;
        displayList.AddEnemy(squirrel);
        setFocusable(true);

//        zinzanLives = new ZinzanLife[] {
//                new ZinzanLife(10, 10),
//                new ZinzanLife(37, 10),
//                new ZinzanLife(64, 10),
//        };

        floors = new RoundRectangle2D[] {
                ground
        };
        walls = new RoundRectangle2D[] {
                new RoundRectangle2D.Double(-10, 0, 10, 710, 0, 0)
        };

    }

    @Override
    public void update() {
        ////make the squirrel shoot balls
        if (squirrel != null && !squirrel.Dying && !squirrel.Dead) {
            if (tickCount == 220) {
                Ball ball = squirrel.generateBall(-2.5);
                SwingUtilities.invokeLater(() -> displayList.AddEnemy(ball));
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

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("times", 0, 14));
        g2.drawString("jump over the projectiles and destroy the toy squirrel", 400, 200);
        g2.drawImage(rightArrow, 700, 500, 220, 50, null);

        paintDisplayList(g2);

        if (pauseImagePressed) {
            g2.drawImage(pressedPauseImage, 1145, 5, 50, 30, null);
        } else {
            g2.drawImage(pauseImage, 1145, 5, 50, 30, null);
        }


    }


}
