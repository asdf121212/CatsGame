import java.awt.*;

public class StartLevel extends GenericMazeLevel {


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("times", 0, 16));
        g2.drawString("find the end of the maze before this mouse catches you", 300, 100);
    }
}
