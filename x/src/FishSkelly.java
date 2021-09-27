import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FishSkelly extends catProjectile {

    private static BufferedImage skellyImage = getBufferedImage("sprites/fishSkelly/skelly.png", 50, 50);
    private static BufferedImage skellyDisappear1 = getBufferedImage("sprites/fishSkelly/skellyDisappear1.png", 50, 50);
    private static BufferedImage skellyDisappear2 = getBufferedImage("sprites/fishSkelly/skellyDisappear2.png", 50, 50);
    private static BufferedImage skellyImageL = getBufferedImage("sprites/fishSkelly/skellyL.png", 50, 50);
    private static BufferedImage skellyDisappear1L = getBufferedImage("sprites/fishSkelly/skellyDisappear1L.png", 50, 50);
    private static BufferedImage skellyDisappear2L = getBufferedImage("sprites/fishSkelly/skellyDisappear2L.png", 50, 50);

    public FishSkelly(double x, double y, double velocityX, int negMultipler) {
        this.x = x;
        this.y = y;
        width = 20;
        height = 10;
        this.velocityX = velocityX;
        this.negMultipler = negMultipler;
        image = negMultipler < 0 ? skellyImageL : skellyImage;
    }


    @Override
    public void Dispose() {

    }

    @Override
    public void Update() {
        move();
        if (disappearing) {
            if (disappearTicks >= 10) {
                if (image.equals(skellyDisappear2) || image.equals(skellyDisappear2L)) {
                    Dead = true;
                } else {
                    if (negMultipler < 0) {
                        image = skellyDisappear2L;
                    } else {
                        image = skellyDisappear2;
                    }
                    disappearTicks = 0;
                }
            } else {
                disappearTicks++;
            }
        }
    }

    public int fluffballDamage() {
        return 10;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void entityHit(int damage) {

    }

    @Override
    public void startDying() {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }

    private void move() {
        x = (int)Math.round(x + (velocityX * negMultipler));
        velocityX += decelX;
        if (!disappearing && velocityX <= 3) {
            if (negMultipler < 0) {
                image = skellyDisappear1L;
            } else {
                image = skellyDisappear1;
            }
            disappearing = true;
            disappearTicks = 0;
        }
    }

}
