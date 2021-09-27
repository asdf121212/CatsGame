import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PauseMenu extends Menu {

    private RoundRectangle2D resumeButton = new RoundRectangle2D.Double(430, 170, 340, 70, 10, 10);
    private RoundRectangle2D changeCatButton = new RoundRectangle2D.Double(430, 260, 340, 70, 10, 10);
    private RoundRectangle2D restartButton = new RoundRectangle2D.Double(430, 350, 340, 70, 10, 10);
    private RoundRectangle2D mainMenuButton = new RoundRectangle2D.Double(430, 440, 340, 70, 10, 10);
    private RoundRectangle2D[] buttons = new RoundRectangle2D[] {
            resumeButton,
            changeCatButton,
            restartButton,
            mainMenuButton
    };
    private RoundRectangle2D resumeOutline = new RoundRectangle2D.Double(430, 170, 340, 70, 10, 10);
    private RoundRectangle2D changeCatOutline = new RoundRectangle2D.Double(430, 260, 340, 70, 10, 10);
    private RoundRectangle2D restartOutline = new RoundRectangle2D.Double(430, 350, 340, 70, 10, 10);
    private RoundRectangle2D mainMenuOutline = new RoundRectangle2D.Double(430, 440, 340, 70, 10, 10);

    private RoundRectangle2D changeCatBlocker = new RoundRectangle2D.Double(430, 260, 340, 70, 10, 10);
//    private boolean highlightResume = false;
//    private boolean highlightChooseCat = false;
//    private boolean highlightRestart = false;
//    private boolean highlightMainMenu = false;
    private RoundRectangle2D[] outlines = new RoundRectangle2D[] {
            resumeOutline,
            changeCatOutline,
            restartOutline,
            mainMenuOutline
    };
    private boolean[] highlightFlags = new boolean[] {
            false,
            false,
            false,
            false
    };
    private boolean[] clickedFlags = new boolean[] {
            false,
            false,
            false,
            false
    };
    public boolean clickedResume() {return clickedFlags[0];}
    public boolean clickedChangeCat() {return clickedFlags[1];}
    public boolean clickedRestart() {return clickedFlags[2];}
    public boolean clickedMainMenu() {return clickedFlags[3];}

    private boolean showChangeCat;

    public PauseMenu(boolean showChangeCat) {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        setBackground(Color.BLACK);
        this.showChangeCat = showChangeCat;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("times", 0, 60));
        g2.drawString("PAUSED", 482, 95);
        g2.setColor(Color.gray);
        for (int i = 0; i < outlines.length; i++) {
            if (highlightFlags[i]) {
                g2.fill(outlines[i]);
            }
        }

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("times", 0, 50));
        for (RoundRectangle2D button : buttons) {
            g2.draw(button);
        }
        g2.drawString("RESUME", 495, 222);
        g2.drawString("SELECT CAT", 451, 312);
        g2.drawString("RESTART", 490, 402);
        g2.drawString("MAIN MENU", 455, 492);

        if (!showChangeCat) {
            g2.setColor(Color.GREEN);
            g2.setColor(Color.BLACK);
            g2.fill(changeCatBlocker);
            g2.setColor(Color.GREEN);
            g2.draw(changeCatOutline);
            g2.setColor(Color.darkGray);
            g2.drawString("SELECT CAT", 451, 312);
        }

    }

    @Override
    public boolean transition() {
        return clickedResume() || clickedChangeCat() || clickedRestart() || clickedMainMenu();
    }

    @Override
    public void mouseMove(int x, int y) {
        for (int i = 0; i < outlines.length; i++) {
            if (outlines[i].contains(x, y)) {
                highlightFlags[i] = true;
            } else {
                highlightFlags[i] = false;
            }
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        for (int i = 0; i < outlines.length; i++) {
            if (outlines[i].contains(x, y)) {
                clickedFlags[i] = true;
            } else {
                clickedFlags[i] = false;
            }
        }
    }
}
