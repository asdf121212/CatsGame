import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Level1 extends JPanel {

    private DisplayList displayList;

    private Timer leftTimer;
    private Timer rightTimer;
    private Timer jumpTimer;
    private Timer repaintTimer;
    private Timer hitCheckTimer;

    private int tickCount = 180;
    private float vel_y = 0;
    private final float GRAV = 0.5f;
    private int GroundLevel = 350;
    private Shape ground;
    private boolean spaceReleased = true;

    public Level1() {

        setPreferredSize(new Dimension(1200, 700));

        displayList = new DisplayList();
        setBackground(Color.BLACK);
        ground = new Rectangle2D.Double(0, GroundLevel + 50, 1200, 300);
        displayList.AddBackgroundShape(ground);
        Squirrel squirrel = new Squirrel();
        squirrel.x = 1000;
        squirrel.y = 280;
        displayList.AddEnemy(squirrel);

        setFocusable(true);

        hitCheckTimer = new Timer(5, hitCheckListener);
        hitCheckTimer.setInitialDelay(1);
        repaintTimer = new Timer(5, repaintListener);
        repaintTimer.start();
        hitCheckTimer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (jumpTimer == null || !jumpTimer.isRunning()) {
                        jumpTimer = new Timer(10, jumpAction);
                        jumpTimer.setInitialDelay(0);
                        vel_y = -10;
                        displayList.cat.state = (byte)(displayList.cat.state | 100);
                        jumpTimer.start();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (leftTimer == null || !leftTimer.isRunning()) {
                        leftTimer = new Timer(10, moveLeft);
                        leftTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)(displayList.cat.state | 011);
                        leftTimer.start();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (rightTimer == null || !rightTimer.isRunning()) {
                        rightTimer = new Timer(10, moveRight);
                        rightTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
                        rightTimer.start();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
                    spaceReleased = false;
                    displayList.AddFluffball(displayList.cat.generateFluffball());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (leftTimer != null) {
                        leftTimer.stop();
                        displayList.cat.state = (byte)(displayList.cat.state & 101);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (rightTimer != null) {
                        rightTimer.stop();
                        displayList.cat.state = (byte)(displayList.cat.state & 101);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spaceReleased = true;
                }
            }
        });
        Fluffball f = displayList.cat.generateFluffball();
        f = null;
    }

    ActionListener hitCheckListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (displayList.getEnemies().size() == 1 && !displayList.getEnemies().get(0).deadAnimating) {
                Squirrel squirrel = (Squirrel) displayList.getEnemies().get(0);/////////not good structure- should refactor
                if (tickCount == 220) {
                    SwingUtilities.invokeLater(() -> displayList.AddDanger(squirrel.generateBall(-5)));
                    tickCount = 0;
                } else {
                    tickCount++;
                }
            }
            for (Enemy enemy : displayList.getEnemies()) {
                if (enemy.dead) {
                    SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
                    continue;
                } else if (enemy.deadAnimating) {
                    continue;
                }
                for (Fluffball fluffball : displayList.getFluffballs()) {
                    if (fluffball.getHitBox().intersects(enemy.getHitBox())) {
                        fluffball.stop();
                        SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
                        SwingUtilities.invokeLater(enemy::entityHit);
                    }
                }
            }
            for (Danger danger : displayList.getDangers()) {
                if (danger.getHitBox().intersects(displayList.cat.getHitBox()) && danger.Live) {
                    SwingUtilities.invokeLater(displayList.cat::catHit);
                    danger.Live = false;
                    if (!displayList.cat.isAlive()) {
                        
                    }
                }
            }
        }
    };
    ActionListener repaintListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    };
    ActionListener moveRight = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(3, 0);
            if (displayList.cat.state >> 2 == 0) {
                displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
            }
        }
    };
    ActionListener moveLeft = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(-3, 0);
            if (displayList.cat.state >> 2 == 0) {
                displayList.cat.state = (byte)(displayList.cat.state | 011);
            }
        }
    };
    ActionListener jumpAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(0, Math.round(vel_y));
            vel_y += GRAV;
            if (displayList.cat.GetY() >= GroundLevel) {
                displayList.cat.SetY(350);
                displayList.cat.state = (byte)(displayList.cat.state & 011);
                jumpTimer.stop();
            }
        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.MAGENTA);
        g2.fill(ground);

        for (Shape shape : displayList.getBackgroundShapes()) {
            g2.draw(shape);
        }

        for (Entity danger : displayList.getDangers()) {
            danger.paintComponent(g2);
        }
        for (Entity enemy : displayList.getEnemies()) {
            enemy.paintComponent(g2);
        }
        for (Fluffball fluffball : displayList.getFluffballs()) {
            if (!fluffball.stillMoving) {
                SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
            } else {
                fluffball.paintComponent(g2);
            }
        }

        displayList.cat.paintComponent(g2);


    }


}
