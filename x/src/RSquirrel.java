import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class RSquirrel extends Squirrel {

    public RSquirrel(int x, int y) {
        super(x, y);
    }

    @Override
    public void LoadImages() {
        squirrelImage = getBufferedImage("sprites/squirrel/R_squirrel/r_squirrel.png", 100, 100);
        squirrelFlashImage = getBufferedImage("sprites/squirrel/R_squirrel/r_squirrelFlash.png", 100, 100);
        squirrelExp1 = getBufferedImage("sprites/squirrel/R_squirrel/r_squirrelExp1.png", 100, 100);
        squirrelExp2 = getBufferedImage("sprites/squirrel/R_squirrel/r_squirrelExp2.png", 100, 100);
        squirrelExp3 = getBufferedImage("sprites/squirrel/R_squirrel/r_squirrelExp3.png", 100, 100);
    }


    @Override
    public Ball generateBall(double xVelocity) {
        generateBallClip.Start();
        return new Ball(x + width - 20, y + 65, 40, 2.5, 0);
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x + 45, y + 56, 90, 50);
    }


}
