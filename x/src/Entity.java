import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public abstract class Entity {

    protected int health = 100;

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    public boolean Dead = false;
    public boolean Dying = false;

    public abstract void Dispose();

    public abstract void paintComponent(Graphics g);

    protected static BufferedImage getBufferedImage(String filePath, int scaledWidth, int scaledHeight) {
        Image img = null;
        BufferedImage bufferedImage = null;
        try {
            URL imageURL1 = Squirrel.class.getResource(filePath);
            img = ImageIO.read(imageURL1);
        } catch (IOException e) {

        }
        img = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(img, 0, 0, null);

        return bufferedImage;
    }

    public abstract void Update();

    public abstract Rectangle2D getHitBox();

    public abstract void entityHit(int damage);

    public abstract void startDying();

}
