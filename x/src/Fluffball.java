import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Fluffball extends catProjectile {

    protected static BufferedImage fluffballImage = getBufferedImage("sprites/fluffball/fluffball.png", 20, 20);
    protected static BufferedImage fluffballDisappearA1 = getBufferedImage("sprites/fluffball/fluffball-A1.png", 20, 20);
    protected static BufferedImage fluffballDisappear2 = getBufferedImage("sprites/fluffball/fluffball-A2.png", 20, 20);
//    private BufferedImage image;
//
//    private boolean disappearing = false;
//    private int disappearTicks = 0;
//
//    private double velocityX;
//    private int negMultipler;
//    private double decelX = -0.08;

    public Fluffball(double x, double y, double velocityX, int negMultipler) {
        this.x = x;
        this.y = y;
        width = 20;
        height = 20;
        this.velocityX = velocityX;
        this.negMultipler = negMultipler;
        image = fluffballImage;
    }


    @Override
    public void Dispose() {

    }

    @Override
    public void Update() {
        move();
        if (disappearing) {
            if (disappearTicks >= 10) {
                if (image.equals(fluffballDisappear2)) {
                    Dead = true;
                } else {
                    image = fluffballDisappear2;
                    disappearTicks = 0;
                }
            } else {
                disappearTicks++;
            }
        }
    }

    @Override
    public int fluffballDamage() {
        return 10;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 2, y + 2, 14, 15);
    }

    @Override
    public void entityHit(int damage) {

    }

    @Override
    public void startDying() {

    }

//    public void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D)g;
//        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
//    }

    private void move() {
        x = (int)Math.round(x + (velocityX * negMultipler));
        velocityX += decelX;
        if (!disappearing && velocityX <= 3) {
            image = fluffballDisappearA1;
            disappearing = true;
            disappearTicks = 0;
        }
    }


}
