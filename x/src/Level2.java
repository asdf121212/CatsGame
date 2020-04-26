import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Level2 extends Level {


    public Level2() {

        GroundLevel = 350;
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);

        displayList.cat.SetXY(-5, 350);

        zinzanLives = new ZinzanLife[] {
                new ZinzanLife(10, 10),
                new ZinzanLife(37, 10),
                new ZinzanLife(64, 10),
        };
    }


    @Override
    protected int getGroundLevel(int xCoord, int yCoord) {
        return 350;
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

        for (int i = 0; i < numLives; i++) {
            zinzanLives[i].paintComponent(g);
        }

        paintDisplayList(g2);

    }

}
