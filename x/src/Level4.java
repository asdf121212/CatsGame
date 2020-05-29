import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Level4 extends Level {

    private RoundRectangle2D bottomFloor = new RoundRectangle2D.Double(-10, 650, 110, 60, 10, 10);
    private RoundRectangle2D jumpPad1 = new RoundRectangle2D.Double(170, 600, 60, 20, 10, 10);
    private RoundRectangle2D jumpPad2 = new RoundRectangle2D.Double(300, 560, 60, 20, 10, 10);
    private RoundRectangle2D jumpPad3 = new RoundRectangle2D.Double(430, 520, 60, 20, 10, 10);
    private RoundRectangle2D jumpPad4 = new RoundRectangle2D.Double(300, 430, 60, 20, 10, 10);
    //private RoundRectangle2D jumpPad5 = new RoundRectangle2D.Double(140, 430, 60, 20, 10, 10);
    private RoundRectangle2D jumpPad6 = new RoundRectangle2D.Double(-10, 340, 240, 20, 10, 10);
    private RoundRectangle2D jumpPad7 = new RoundRectangle2D.Double(900, 200, 60, 20, 10, 10);
    private RoundRectangle2D jumpPad8 = new RoundRectangle2D.Double(1000, 145, 60, 20, 10, 10);
    private RoundRectangle2D jumpPad9 = new RoundRectangle2D.Double(1100, 90, 110, 50, 10, 10);

    private MovingRectangle movePad1 = new MovingRectangle(280, 280, 120, 20, 280, 1000, 2);

    private Vacuum vacuum;
    private int shootTicks = 200;

    public Level4() {

        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);
        displayList.cat.SetXY(-5, 600);
//        zinzanLives = new ZinzanLife[] {
//                new ZinzanLife(10, 10),
//                new ZinzanLife(37, 10),
//                new ZinzanLife(64, 10),
//        };

        vacuum = new Vacuum(60, 30, 60, 1000);
        vacuum.addLevelInfo(new LevelInfo(floors, walls, displayList.cat));
        //vacuum.Start();
        displayList.AddEnemy(vacuum);

        walls = new RoundRectangle2D[] {

        };

        floors = new RoundRectangle2D[] {
                bottomFloor,
                jumpPad1,
                jumpPad2,
                jumpPad3,
                jumpPad4,
                //jumpPad5,
                jumpPad6,
                jumpPad7,
                jumpPad8,
                jumpPad9,
                movePad1
        };

        movingRectangles = new MovingRectangle[] {
                movePad1
        };

        //daisy.addLevelInfo(new LevelInfo(floors, walls, displayList.cat));

    }


    @Override
    public void update() {
        ///update any enemies and stuff that need updating
        shootTicks++;
        if (shootTicks >= 400) {
            shootTicks = 0;
            displayList.AddEnemy(vacuum.generateBall());
        }
        if (displayList.cat.x > 1185 && displayList.cat.y < 90) {
            reachedNextLevel = true;
        }
        super.update();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLUE);
        for (RoundRectangle2D rect : walls) {
            g2.fill(rect);
        }
        for (RoundRectangle2D rect : floors) {
            g2.fill(rect);
        }

//        for (int i = 0; i < numLives; i++) {
//            zinzanLives[i].paintComponent(g2);
//        }

        paintDisplayList(g2);

    }

}
