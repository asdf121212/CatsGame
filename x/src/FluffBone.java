import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FluffBone extends catProjectile {

    private static BufferedImage fluffballImage = getBufferedImage("sprites/fluffbone/bone.png", 50, 50);
    private static BufferedImage fluffballDisappearA1 = getBufferedImage("sprites/fluffbone/boneDisappear1.png", 50, 50);
    private static BufferedImage fluffballDisappear2 = getBufferedImage("sprites/fluffbone/boneDisappear2.png", 50, 50);


    public FluffBone(double x, double y, double velocityX, int negMultipler) {
        this.x = x;
        this.y = y;
        width = 20;
        height = 10;
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
            image = fluffballDisappearA1;
            disappearing = true;
            disappearTicks = 0;
        }
    }


}
