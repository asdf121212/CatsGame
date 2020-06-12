import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class TutorialLevel1 extends TutorialLevel {

    private BufferedImage keyboardImage = Entity.getBufferedImage("sprites/keyboardLayout/keys.png", 800, 400);
    private BufferedImage rightArrow = Entity.getBufferedImage("sprites/menuImages/rightArrow.png", 300, 100);

    public TutorialLevel1() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        setFocusable(true);
        DisplayList.catClass = Zinzan.class;
        displayList = new DisplayList();
        displayList.cat.x = 100;
        displayList.cat.y = 450;
        RoundRectangle2D rightWallFloor = new RoundRectangle2D.Double(1150, 600, 80, 110, 10, 10);
        RoundRectangle2D jumpWallFloor1 = new RoundRectangle2D.Double(930, 420, 280, 100, 10, 10);
        RoundRectangle2D jumpWallFloor2 = new RoundRectangle2D.Double(1070, 340, 160, 100, 10, 10);
        floors = new RoundRectangle2D[] {
            new RoundRectangle2D.Double(-10, 500, 1220, 510, 0, 0),
            rightWallFloor,
            jumpWallFloor1,
            jumpWallFloor2
        };
        walls = new RoundRectangle2D[] {
            rightWallFloor,
            new RoundRectangle2D.Double(-10, 0, 15, 710, 0, 0),
            jumpWallFloor1,
            jumpWallFloor2
        };
    }

    @Override
    public void update() {
        super.update();
        if (displayList.cat.x > 1160) {
            reachedNextLevel = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.MAGENTA);
//        for (RoundRectangle2D rect : walls) {
//            g2.fill(rect);
//        }
        for (RoundRectangle2D rect : floors) {
            g2.fill(rect);
        }

        g2.drawImage(keyboardImage, 270, 50, 500, 250, null);
        g2.drawImage(rightArrow, 600, 550, 220, 50, null);

        paintDisplayList(g2);

        if (pauseImagePressed) {
            g2.drawImage(pressedPauseImage, 1145, 5, 50, 30, null);
        } else {
            g2.drawImage(pauseImage, 1145, 5, 50, 30, null);
        }

    }

}
