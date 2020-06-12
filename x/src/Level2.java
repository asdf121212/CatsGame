import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class Level2 extends TutorialLevel {

    private RoundRectangle2D upperLeftRect = new RoundRectangle2D.Double(-10, 155, 750, 95, 10, 10);
    private RoundRectangle2D upperRightRect = new RoundRectangle2D.Double(1050, 155, 160, 95, 10, 10);
    private RoundRectangle2D middleSideWall = new RoundRectangle2D.Double(1175, 210, 35, 200, 0, 0);
    private RoundRectangle2D middleRightRect = new RoundRectangle2D.Double(145, 400, 1055, 95, 10, 10);
    private RoundRectangle2D leftWall = new RoundRectangle2D.Double(-10, 210,  35, 440, 0, 0);
    private RoundRectangle2D leftFloor = new RoundRectangle2D.Double(-10, 650, 550, 60, 10, 10);
    private RoundRectangle2D rightFloor = new RoundRectangle2D.Double(640, 650, 590, 60, 10, 10);
    private RoundRectangle2D topLeftWall = new RoundRectangle2D.Double(-10, -5, 4, 155, 0, 0);

    private Yarnball topYarnball = new Yarnball(1070, 30, 455);
    private Yarnball bottomYarnball = new Yarnball(1130, 520, 670);
    private TinyMouse tinyMouse = new TinyMouse(150, 1000, 375);
    private Acid acid = new Acid(540, 655, 100, 50);

    private boolean showText1 = false;
    private boolean showText2 = false;

    public Level2() {

        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);
        displayList.cat.SetXY(-5, 105);
//        zinzanLives = new ZinzanLife[] {
//                new ZinzanLife(10, 10),
//                new ZinzanLife(37, 10),
//                new ZinzanLife(64, 10),
//        };

        displayList.AddEnemy(acid);
        displayList.AddEnemy(topYarnball);
        displayList.AddEnemy(bottomYarnball);
        displayList.AddEnemy(tinyMouse);

        walls = new RoundRectangle2D[] {
                middleSideWall,
                leftWall,
                topLeftWall
        };
        floors = new RoundRectangle2D[] {
                upperLeftRect,
                upperRightRect,
                middleRightRect,
                leftFloor,
                rightFloor
        };
    }


    @Override
    public void update() {
        ///update any enemies and stuff that need updating

//        if (displayList.cat.GetY() > 800) {
//            displayList.cat.entityHit(200);
//        }

        if (topYarnball.enteredAttackZone(displayList.cat.GetX() + 75, displayList.cat.GetY() + 25)) {
            topYarnball.Start();
        } else if (bottomYarnball.enteredAttackZone(displayList.cat.GetX() + 75, displayList.cat.GetY() + 25)) {
            bottomYarnball.Start();
        }
        //tinyMouse.update();

        if (displayList.cat. x > 30 && displayList.cat.y < 160) {
            showText1 = true;
            showText2 = false;
        } else if (displayList.cat.x > 100 && displayList.cat.y > 590) {
            showText1 = false;
            showText2 = true;
        }

        if (displayList.cat.GetX() >= 1170 && displayList.cat.GetY() >= 600) {
            reachedNextLevel = true;
        }

        super.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.CYAN);
        for (RoundRectangle2D rect : walls) {
            g2.fill(rect);
        }
        for (RoundRectangle2D rect : floors) {
            g2.fill(rect);
        }

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("times", 0, 14));
        if (showText1) {
            g2.drawString("watch out for surprise attacks from the yarnball monsters", 300, 100);
        }
        if (showText2) {
            g2.drawString("don't fall in the acid", 300, 600);
        }

//        for (int i = 0; i < numLives; i++) {
//            zinzanLives[i].paintComponent(g);
//        }

        paintDisplayList(g2);

        if (pauseImagePressed) {
            g2.drawImage(pressedPauseImage, 1145, 5, 50, 30, null);
        } else {
            g2.drawImage(pauseImage, 1145, 5, 50, 30, null);
        }

    }

}
