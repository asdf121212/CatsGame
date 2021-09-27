import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawingFloor extends NiceShape {

    private IndexedNodeFloor floor;

    public boolean isAlsoWall = false;
    public boolean highlight = false;

    public DrawingFloor(IndexedNodeFloor floor) {
        this.floor = floor;
    }
    public DrawingFloor(int x, int y, int width, int height) {
        floor = new IndexedNodeFloor(x, y, width, height);
    }

    public void setRect(double x, double y, double width, double height) {
        floor.x = x;
        floor.y = y;
        floor.width = width;
        floor.height = height;
    }

    @Override
    public boolean contains(Rectangle2D rect) {
        return floor.contains(rect);
    }

    @Override
    public boolean intersects(Rectangle2D rect) {
        return floor.intersects(rect);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return floor.getBounds();
    }

    @Override
    public void setPosition(double x, double y, double mouseOffsetX, double mouseOffsetY) {
        floor.x = x - mouseOffsetX;
        floor.y = y - mouseOffsetY;
    }

    @Override
    public IndexedNodeFloor getShape() {
        return floor;
    }

    @Override
    public boolean contains(int x, int y) {
        return floor.contains(x, y);
    }

    public IndexedNodeFloor getRec() {
        return floor;
    }

}
