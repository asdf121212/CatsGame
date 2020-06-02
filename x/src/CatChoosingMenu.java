import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CatChoosingMenu extends Menu {

    public boolean backButtonClicked = false;
    public boolean catChosen = false;
    public Class<? extends Cat> selectedCatClass;

    private boolean drawZinzanRect = false;
    private boolean drawDasiyRect = false;
    private boolean drawPheobeRect = false;

    private RoundRectangle2D borderRect1 = new RoundRectangle2D.Double(10, 10, 1180, 680, 10, 10);
    private RoundRectangle2D borderRect2 = new RoundRectangle2D.Double(30, 70, 1140, 600, 10, 10);

    private BufferedImage zinzan = Entity.getBufferedImage("sprites/zinzan/zinzanStill.png", 100, 100);
    private BufferedImage daisy = Entity.getBufferedImage("sprites/daisyEnemy/d2R.png", 100, 100);
    private BufferedImage phoebe = Entity.getBufferedImage("sprites/pheobe/phebStillR.png", 100, 100);
    private RoundRectangle2D zinzanRect = new RoundRectangle2D.Double(345, 195, 110, 76, 10, 10);
    private RoundRectangle2D daisyRect = new RoundRectangle2D.Double(545, 195, 90, 76, 10, 10);
    private RoundRectangle2D pheobeRect = new RoundRectangle2D.Double(745, 195, 110, 76, 10, 10);

    public CatChoosingMenu() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        setFocusable(true);
        setBackground(Color.BLACK);
    }

    @Override
    public boolean transition() {
        return catChosen || backButtonClicked;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (zinzanRect.contains(x, y)) {
            drawZinzanRect = true;
            drawDasiyRect = false;
            drawPheobeRect = false;
        } else if (daisyRect.contains(x, y)) {
            drawDasiyRect = true;
            drawPheobeRect = false;
            drawZinzanRect = false;
        } else if (pheobeRect.contains(x, y)) {
            drawPheobeRect = true;
            drawDasiyRect = false;
            drawZinzanRect = false;
        } else {
            drawPheobeRect = false;
            drawZinzanRect = false;
            drawDasiyRect = false;
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        if (zinzanRect.contains(x, y)) {
            catChosen = true;
            selectedCatClass = Zinzan.class;
            Level.backGroundColor = Color.BLACK;
        } else if (daisyRect.contains(x, y)) {
            catChosen = true;
            selectedCatClass = Daisy.class;
            Level.backGroundColor = Color.GRAY;
        } else if (pheobeRect.contains(x, y)) {
            catChosen = true;
            selectedCatClass = Phoebe.class;
            Level.backGroundColor = Color.GRAY;
        }
//        else if (backbutton) {
//
//        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.MAGENTA);
        g2.draw(borderRect1);
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("times", Font.ITALIC, 40));
        g2.drawString("SELECT CAT", 475, 55);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(borderRect2);

        g2.setColor(Color.MAGENTA);
        if (drawZinzanRect) {
            g2.fill(zinzanRect);
        } else if (drawDasiyRect) {
            g2.fill(daisyRect);
        } else if (drawPheobeRect) {
            g2.fill(pheobeRect);
        }
        g2.setColor(Color.WHITE);
        g2.draw(zinzanRect);
        g2.draw(daisyRect);
        g2.draw(pheobeRect);

        g2.drawImage(zinzan, 350, 200, 100, 66, null);
        g2.drawImage(daisy, 550, 200, 80, 66, null);
        g2.drawImage(phoebe, 750, 200, 100, 66, null);
        g2.setFont(new Font("times", 0, 12));
        g2.drawString("Zinzan", 380, 285);
        g2.drawString("Daisy", 580, 285);
        g2.drawString("Phoebe", 780, 285);

    }
}
