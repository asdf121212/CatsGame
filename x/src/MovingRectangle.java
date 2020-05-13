import java.awt.geom.RoundRectangle2D;

public class MovingRectangle extends RoundRectangle2D.Double {

    private double leftBound;
    private double rightBound;
    private double xVel;

    public MovingRectangle(double x, double y, double width, double height, double leftBound, double rightBound, double xVel) {
        super(x, y, width, height, 10, 10);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.xVel = xVel;
    }

    public void update() {
        this.setRoundRect(x + xVel, y, width, height, arcwidth, archeight);
        if (xVel > 0 && x + width >= rightBound) {
            xVel *= -1;
        } else if (xVel < 0 && x <= leftBound) {
            xVel *= -1;
        }
    }

    public double getXVel() {
        return xVel;
    }

}
