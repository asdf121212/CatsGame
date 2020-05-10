import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Level3 extends Level {

    private RoundRectangle2D leftWall = new RoundRectangle2D.Double(-10, 200, 80, 520, 10, 10);
    private RoundRectangle2D leftFloor = new RoundRectangle2D.Double(-10, 650, 700, 60, 10, 10);
    private RoundRectangle2D rightFloor = new RoundRectangle2D.Double(900, 400, 320, 60, 10, 10);
    private RoundRectangle2D hiddenFloor = new RoundRectangle2D.Double(750, 900, 550, 60, 10, 10);

    private DaisyEnemy daisy = new DaisyEnemy(new Rectangle2D.Double(70, 650, 620, 60), rightFloor.getFrame());

    private boolean floorRaising = false;

    public Level3() {

        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.CYAN);
        setFocusable(true);
        displayList.cat.SetXY(-5, 140);
        zinzanLives = new ZinzanLife[] {
                new ZinzanLife(10, 10),
                new ZinzanLife(37, 10),
                new ZinzanLife(64, 10),
        };

        displayList.AddEnemy(daisy);

        walls = new RoundRectangle2D[] {
                leftWall
        };

        floors = new RoundRectangle2D[] {
                leftFloor,
                rightFloor,
                leftWall,
                hiddenFloor
        };

        daisy.addLevelInfo(new LevelInfo(floors, walls, displayList.cat));

    }


    @Override
    public void update() {
        ///update any enemies and stuff that need updating

//        if (displayList.cat.GetY() > 800) {
//            displayList.cat.entityHit(200);
//        }

//        if (topYarnball.enteredAttackZone(displayList.cat.GetX() + 75, displayList.cat.GetY() + 25)) {
//            topYarnball.Start();
//        } else if (bottomYarnball.enteredAttackZone(displayList.cat.GetX() + 75, displayList.cat.GetY() + 25)) {
//            bottomYarnball.Start();
//        }
//        tinyMouse.update();
        if (daisy.Dead) {
            floorRaising = true;
        }
        if (floorRaising) {
            if (hiddenFloor.getY() > 650) {
                hiddenFloor.setRoundRect(hiddenFloor.getX(), hiddenFloor.getY() - 1,
                        hiddenFloor.getWidth(), hiddenFloor.getHeight(), 10, 10);
            } else {
                floorRaising = false;
            }
        }

        super.update();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);
        for (RoundRectangle2D rect : walls) {
            g2.fill(rect);
        }
        for (RoundRectangle2D rect : floors) {
            g2.fill(rect);
        }

        for (int i = 0; i < numLives; i++) {
            zinzanLives[i].paintComponent(g);
        }

        paintDisplayList(g2);

    }



}
