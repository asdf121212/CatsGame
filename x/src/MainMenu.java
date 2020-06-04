import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class MainMenu extends Menu {

    //private int mouseX = 0;
    //private int mouseY = 0;
    boolean shouldTransition = false;
    public boolean playTutorial = false;
    public boolean playGame = false;
    private boolean playGameButtonHighlight = false;
    private boolean playTutorialButtonHighlight = false;

    private BufferedImage title = Entity.getBufferedImage("sprites/menuTitle/title.png", 600, 200);

    private RoundRectangle2D playGameButton = new RoundRectangle2D.Double(350, 250, 500, 70, 10, 10);
    private RoundRectangle2D playGameButtonOutline = new RoundRectangle2D.Double(350, 250, 500, 70, 10, 10);
    private RoundRectangle2D playTutorialButton = new RoundRectangle2D.Double(350, 400, 500, 70, 10, 10);
    private RoundRectangle2D playTutorialButtonOutline = new RoundRectangle2D.Double(350, 400, 500, 70, 10, 10);

    public MainMenu() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        setFocusable(true);
        setBackground(Color.BLACK);
    }

    @Override
    public boolean transition() {
        return shouldTransition;
    }

    @Override
    public void mouseClick(int x, int y) {
        if (playGameButton.contains(x, y)) {
            playGame = true;
            shouldTransition = true;
        } else if (playTutorialButton.contains(x, y)) {
            playTutorial = true;
            shouldTransition = true;
        }
    }

    @Override
    public void mouseMove(int x, int y) {
        //mouseX = x;
        //mouseY = y;
        if (playGameButton.contains(x, y)) {
            playGameButtonHighlight = true;
            playTutorialButtonHighlight = false;
        } else if (playTutorialButton.contains(x, y)) {
            playTutorialButtonHighlight = true;
            playGameButtonHighlight = false;
        } else {
            playGameButtonHighlight = false;
            playTutorialButtonHighlight = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.drawImage(title, 350, 25, 500, 120, null);

        g2.setStroke(new BasicStroke(3));
        if (playGameButtonHighlight) {
            g2.setColor(Color.WHITE);
            g2.fill(playGameButtonOutline);
        }
        if (playTutorialButtonHighlight) {
            g2.setColor(Color.white);
            g2.fill(playTutorialButtonOutline);
        }
        g2.setColor(Color.magenta);
        g2.setFont(new Font("times", 0, 55));
        g2.draw(playGameButton);
        g2.drawString("PLAY GAME", 430, 305);
        g2.draw(playTutorialButton);
        g2.drawString("PRACTICE LEVELS", 357, 455);

    }

}
