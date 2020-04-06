import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;


public class Cat extends Entity {

    private int x = 250;
    private int y = 250;

    private int num134 = 1;

    private Image img1 = null;
    private Image img3 = null;
    private Image img4 = null;
    private BufferedImage buffered1;
    private BufferedImage buffered3;
    private BufferedImage buffered4;
    public Cat() {


        try {
            URL imageURL1 = Cat.class.getResource("sprites/zinzan1.png");
            URL imageURL3 = Cat.class.getResource("sprites/zinzan3.png");
            URL imageURL4 = Cat.class.getResource("sprites/zinzan4.png");
            img1 = ImageIO.read(imageURL1);
            img3 = ImageIO.read(imageURL3);
            img4 = ImageIO.read(imageURL4);
        } catch (IOException e) {

        }

        img1 = img1.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img3 = img3.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img4 = img4.getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        buffered1 = new BufferedImage(img1.getWidth(null), img1.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffered1.createGraphics();
        g2.drawImage(img1, 0, 0, null);

        buffered3 = new BufferedImage(img3.getWidth(null), img3.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = buffered3.createGraphics();
        g2.drawImage(img3, 0, 0, null);

        buffered4 = new BufferedImage(img4.getWidth(null), img4.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = buffered4.createGraphics();
        g2.drawImage(img4, 0, 0, null);

    }



    public void IncrementXY(int dx, int dy) {
        x += dx;
        y += dy;
        if (num134 == 1 || num134 == 4) {
            num134 = 3;
        } else {
            num134 = 4;
        }
    }
    public void SetXY(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        if (num134 == 1) {
            g2.drawImage(buffered1, x, y, 75, 50, null);
        } else if (num134 == 3) {
            g2.drawImage(buffered3, x, y, 75, 50, null);
        } else {
            g2.drawImage(buffered4, x, y, 75, 50, null);
        }


    }

}
