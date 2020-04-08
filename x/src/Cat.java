import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;


public class Cat extends Entity {

    private int x = 150;
    private int y = 350;


    public byte state = 000;
    private BufferedImage catImage;
    private int frameCount = 0;
    private int previousDx = 0;

    private Image img1 = null;
    private Image img2 = null;
    private Image img3 = null;
    private Image img4 = null;
    private Image img5 = null;
    private Image img6 = null;
    private BufferedImage still;
    private BufferedImage stillBack;
    private BufferedImage walk1;
    private BufferedImage walk1Back;
    private BufferedImage walk2;
    private BufferedImage walk2Back;


    public Cat() {


        try {
            URL imageURL1 = Cat.class.getResource("sprites/zinzanStill.png");
            URL imageURL2 = Cat.class.getResource("sprites/zinzanWalk1.png");
            URL imageURL3 = Cat.class.getResource("sprites/zinzanWalk2.png");
            URL imageURL4 = Cat.class.getResource("sprites/zinzanStillBack.png");
            URL imageURL5 = Cat.class.getResource("sprites/zinzanWalk1Back.png");
            URL imageURL6 = Cat.class.getResource("sprites/zinzanWalk2Back.png");
            img1 = ImageIO.read(imageURL1);
            img2 = ImageIO.read(imageURL2);
            img3 = ImageIO.read(imageURL3);
            img4 = ImageIO.read(imageURL4);
            img5 = ImageIO.read(imageURL5);
            img6 = ImageIO.read(imageURL6);
        } catch (IOException e) {

        }

        img1 = img1.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img2 = img2.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img3 = img3.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img4 = img4.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img5 = img5.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        img6 = img6.getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        still = new BufferedImage(img1.getWidth(null), img1.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = still.createGraphics();
        g2.drawImage(img1, 0, 0, null);

        walk1 = new BufferedImage(img2.getWidth(null), img2.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = walk1.createGraphics();
        g2.drawImage(img2, 0, 0, null);

        walk2 = new BufferedImage(img3.getWidth(null), img3.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = walk2.createGraphics();
        g2.drawImage(img3, 0, 0, null);

        stillBack = new BufferedImage(img4.getWidth(null), img4.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = stillBack.createGraphics();
        g2.drawImage(img4, 0, 0, null);

        walk1Back = new BufferedImage(img5.getWidth(null), img5.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = walk1Back.createGraphics();
        g2.drawImage(img5, 0, 0, null);

        walk2Back = new BufferedImage(img6.getWidth(null), img6.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        g2 = walk2Back.createGraphics();
        g2.drawImage(img6, 0, 0, null);

        catImage = still;

    }



    public void IncrementXY(int dx, int dy) {
        x += dx;
        y += dy;
        if ((previousDx <= 0 && dx < 0) || (previousDx >= 0 && dx > 0)) {
            frameCount++;
        } else {
            frameCount = 0;
        }
        previousDx = dx;
    }
    public void SetXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public int GetX() {
        return x;
    }
    public int GetY() {
        return y;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setSprite();//aaaaaaaaaaaaaaaa
        g2.drawImage(catImage, x, y, 75, 50, null);
//        if (num134 == 1) {
//            g2.drawImage(still, x, y, 75, 50, null);
//        } else if (num134 == 3) {
//            g2.drawImage(walk1, x, y, 75, 50, null);
//        } else {
//            g2.drawImage(walk2, x, y, 75, 50, null);
//        }

    }


    private void setSprite() {

        if (state == 010) {
            //walking
            if (frameCount < 7) {
                catImage = walk1;
            } else {
                catImage = walk2;
                if (frameCount == 14) {
                    frameCount = 0;
                }
            }
        }else if (state == 011) {
            //walking back
            if (frameCount < 7) {
                catImage = walk1Back;
            } else {
                catImage = walk2Back;
                if (frameCount == 14) {
                    frameCount = 0;
                }
            }
        } else if ((state | 110) == 111) {
            catImage = stillBack;
        } else {
            catImage = still;
        }
    }


}
