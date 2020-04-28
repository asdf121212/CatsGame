import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class Level2 extends Level {

    private RoundRectangle2D upperLeftRect = new RoundRectangle2D.Double(-10, 155, 750, 95, 10, 10);
    private RoundRectangle2D upperRightRect = new RoundRectangle2D.Double(1050, 155, 160, 95, 10, 10);
    private RoundRectangle2D middleSideWall = new RoundRectangle2D.Double(1190, 210, 20, 200, 0, 0);
    private RoundRectangle2D middleRightRect = new RoundRectangle2D.Double(145, 400, 1055, 95, 10, 10);
    private RoundRectangle2D leftWall = new RoundRectangle2D.Double(-10, 210,  20, 440, 0, 0);
    private RoundRectangle2D leftFloor = new RoundRectangle2D.Double(-10, 650, 550, 60, 10, 10);
    private RoundRectangle2D rightFloor = new RoundRectangle2D.Double(690, 650, 520, 60, 10, 10);


    public Level2() {

        //GroundLevel = 350;
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);
        displayList.cat.SetXY(-5, 105);
        zinzanLives = new ZinzanLife[] {
                new ZinzanLife(10, 10),
                new ZinzanLife(37, 10),
                new ZinzanLife(64, 10),
        };

        walls = new RoundRectangle2D[] {
                middleSideWall,
                leftWall,
        };
        floors = new RoundRectangle2D[] {
                upperLeftRect,
                upperRightRect,
                middleRightRect,
                leftFloor,
                rightFloor
        };

//        upperLeftRect.setRoundRect(-10, 155, 750, 95, 10, 10);
//        upperRightRect.setRoundRect(1050, 155, 160, 95, 10, 10);
//        middleSideWall.setRoundRect(1190, 210, 20, 200, 0, 0);
//        middleRightRect.setRoundRect(145, 400, 1055, 95, 10, 10);
//        leftWall.setRoundRect(-10, 210,  20, 440, 0, 0);
//        leftFloor.setRoundRect(-10, 650, 550, 60, 10, 10);
//        rightFloor.setRoundRect(690, 650, 520, 60, 10, 10);

    }

//    public boolean canMoveX(int x, int y) {
//        for (RoundRectangle2D rect : walls) {
//            if (rect.contains(x, y)) {
//                return false;
//            }
//        }
//        return true;
//    }
//    public double nearestFloorY(double x, double y) {
//        for (RoundRectangle2D rect : floors) {
//            if (rect.contains(x, y)) {
//                return rect.getY();
//            }
//        }
//        return -1;
//    }

    @Override
    protected int getGroundLevel(int xCoord, int yCoord) {
//        if (yCoord <= 160) {
//            if (xCoord >= 740 && xCoord <= 1000) {
//                return 350;
//            } else {
//                return 105;
//            }
//        } else if (yCoord <= 402) {
//            if (xCoord <= 95) {
//                return 600;
//            } else {
//                return 350;
//            }
//        } else {
//            if (xCoord >= 540 && xCoord <= 640) {
//                return 800;
//            } else {
//                return 600;
//            }
//        }
        return 105;
    }

    @Override
    public void update() {
        ///update any enemies and stuff that need updating


        super.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.GREEN);
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
