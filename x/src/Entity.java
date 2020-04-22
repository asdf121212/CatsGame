import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    public boolean Dead = false;
    public boolean Dying = false;

    public abstract void paintComponent(Graphics g);

//    protected static Clip loadSound(String path) {
//        try {
//            AudioInputStream stream = AudioSystem.getAudioInputStream(Entity.class.getResource(path));
//            Clip soundClip = AudioSystem.getClip();
//            soundClip.open(stream);
//            return soundClip;
////            AudioFormat format = stream.getFormat();
////            DataLine.Info info = new DataLine.Info(Clip.class, format);
////            Clip clip = (Clip) AudioSystem.getLine(info);
////            return clip;
//        }
//        catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            return null;
//        }
//    }

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
