import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class LevelInfo {

    public RoundRectangle2D[] floors;
    public RoundRectangle2D[] walls;

    public JumpingRect[] jumpingRects;

    private Cat cat;

    public double getCatX() {
        if (cat == null) {
            return -1;
        } else {
            return cat.GetX();
        }
    }
    public double getCatY() {
        if (cat == null) {
            return -1;
        } else {
            return cat.GetY();
        }
    }

    public LevelInfo(JumpingRect[] rects, Cat cat) {
        jumpingRects = rects;
        this.cat = cat;
    }

    public LevelInfo(RoundRectangle2D[] floors, RoundRectangle2D[] walls, Cat cat) {
        if (floors != null) {
            this.floors = floors.clone();
        }
        if (walls != null) {
            this.walls = walls.clone();
        }
        if (cat != null) {
            this.cat = cat;
        }
        jumpingRects = null;
    }

}
