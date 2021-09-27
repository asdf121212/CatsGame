import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TutorialLevel extends Level {

    protected BufferedImage pauseImage = Entity.getBufferedImage("sprites/menuImages/pauseButton.png", 100, 100);
    protected BufferedImage pressedPauseImage = Entity.getBufferedImage("sprites/menuImages/pauseButtonDark.png", 100, 100);
    public boolean pauseImagePressed = false;


    public boolean pauseButtonContains(int x, int y) {
        Rectangle2D pauseRect = new Rectangle2D.Double(1145, 5, 50, 30);
        return pauseRect.contains(x, y);
    }


}
