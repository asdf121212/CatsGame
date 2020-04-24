import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GameOverPanel extends JPanel {

    private BufferedImage quitDark;
    private BufferedImage quitLight;
    private BufferedImage retryDark;
    private BufferedImage retryLight;

    private boolean mouseOverQuit = false;
    private boolean mouseOverRetry = false;

    private Rectangle2D quitBox;
    private Rectangle2D retryBox;

    private int quitX = 315;
    private int quitY = 450;
    private int retryX = 625;
    private int retryY = 450;
    private int width = 300;
    private int height = 180;

    public GameOverPanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 700));
        quitDark = Entity.getBufferedImage("sprites/gameOverButtons/QuitDark.png", width, height);
        quitLight = Entity.getBufferedImage("sprites/gameOverButtons/QuitLight.png", width, height);
        retryDark = Entity.getBufferedImage("sprites/gameOverButtons/TryAgainDark.png", width, height);
        retryLight = Entity.getBufferedImage("sprites/gameOverButtons/TryAgainLight.png", width, height);

        quitBox = new Rectangle2D.Double();
        retryBox = new Rectangle2D.Double();
        quitBox.setRect(366, 474, 171, 90);
        retryBox.setRect(676, 474, 171, 90);

    }

    public boolean clickedRetry(int x, int y) {
        Point2D point = new Point2D.Double(x, y);
        return retryBox.contains(point);
    }
    public boolean clickedQuit(int x, int y) {
        Point2D point = new Point2D.Double(x, y);
        return quitBox.contains(point);
    }
    public void mouseMove(int x, int y) {
        Point2D point = new Point2D.Double(x, y);
        if (mouseOverRetry) {
            if (!retryBox.contains(point)) {
                mouseOverRetry = false;
                repaint();
            }
        } else if (mouseOverQuit) {
            if (!quitBox.contains(point)) {
                mouseOverQuit = false;
                repaint();
            }
        } else {
            if (quitBox.contains(point)) {
                mouseOverQuit = true;
                repaint();
            } else if (retryBox.contains(point)) {
                mouseOverRetry = true;
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (mouseOverQuit) {
            g2.drawImage(quitLight, quitX, quitY, null);
        } else {
            g2.drawImage(quitDark, quitX, quitY, null);
        }
        if (mouseOverRetry) {
            g2.drawImage(retryLight, retryX, retryY, null);
        } else {
            g2.drawImage(retryDark, retryX, retryY, null);
        }

    }

}
