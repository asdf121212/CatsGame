import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class WinLevel extends Level {

    public WinLevel() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Level.backGroundColor);
        setFocusable(true);
        displayList.cat.SetXY(600, 0);
        floors = new RoundRectangle2D[] {
                new RoundRectangle2D.Double(-10, 650, 1220, 60, 10, 10)
        };
        walls = new RoundRectangle2D[] {
                new RoundRectangle2D.Double(-10, 0, 15, 750, 0, 0),
                new RoundRectangle2D.Double(1195, 0, 15, 750, 0, 0)
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLUE);
        //for (RoundRectangle2D rect : walls) {
            //g2.fill(rect);
        //}
        for (RoundRectangle2D rect : floors) {
            g2.fill(rect);
        }

        g2.setColor(Color.green);
        g2.setFont(new Font("times", 0, 100));
        g2.drawString("YOU WIN", 400, 300);

        paintDisplayList(g2);

    }

}
