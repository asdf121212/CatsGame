import java.awt.geom.Rectangle2D;

public class DrawingAcid extends NiceShape {

    private Acid acid;

    public DrawingAcid(int x, int y, int width, int height) {
        acid = new Acid(x, y, width, height);
    }

    public Acid getAcid() {
        return acid;
    }

    public void setX(double x) {
        acid.x = x;
    }
    public void setY(double y) {
        acid.y = y;
    }
    public void setWidth(int width) {
        acid.width = width;
    }
    public void setHeight(int height) {
        acid.height = height;
    }

    public void setRect(double x, double y, double width, double height) {
        acid.x = x;
        acid.y = y;
        acid.width = (int)Math.round(width);
        acid.height = (int)Math.round(height);
    }

    @Override
    public boolean contains(Rectangle2D rect) {
        Rectangle2D.Double bigRect = new Rectangle2D.Double(acid.x, acid.y, acid.width, acid.height);
        return bigRect.contains(rect);
    }

    @Override
    public boolean intersects(Rectangle2D rect) {
        Rectangle2D.Double bigRect = new Rectangle2D.Double(acid.x, acid.y, acid.width, acid.height);
        return bigRect.intersects(rect);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(acid.x, acid.y, acid.width, acid.height);
    }

    @Override
    public void setPosition(double x, double y, double mouseOffsetX, double mouseOffsetY) {
        acid.x = x - mouseOffsetX;
        acid.y = y - mouseOffsetY;
    }

    @Override
    public Rectangle2D getShape() {
        return new Rectangle2D.Double(acid.x, acid.y, acid.width, acid.height);
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle2D.Double rect = new Rectangle2D.Double(acid.x, acid.y, acid.width, acid.height);
        return rect.contains(x, y);
    }

    ///public IndexedNodeFloor getRec() {
        ///return floor;
    ///}

}
