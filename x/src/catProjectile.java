import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class catProjectile extends Entity {

    protected BufferedImage image;

    protected boolean disappearing = false;
    protected int disappearTicks = 0;

    protected double velocityX;
    protected int negMultipler;
    protected double decelX = -0.08;

    public abstract int fluffballDamage();

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, (int)Math.round(x), (int)Math.round(y), width, height, null);
    }



}
