import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public abstract class Entity {

    protected int x;
    protected int y;
    //public boolean stillMoving = true;
    public boolean Dead = false;
    public boolean Dying = false;

    public abstract void paintComponent(Graphics g);

    protected static Clip getSoundClip(String resourceName) {
        return null;
    }

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

    public abstract Rectangle2D getHitBox();


}
