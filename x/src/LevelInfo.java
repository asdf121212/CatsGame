import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class LevelInfo {

    public RoundRectangle2D[] floors;
    public RoundRectangle2D[] walls;

    private Cat cat;

    public int getCatX() {
        if (cat == null) {
            return -1;
        } else {
            return cat.GetX();
        }
    }
    public int getCatY() {
        if (cat == null) {
            return -1;
        } else {
            return cat.GetY();
        }
    }


    public LevelInfo(RoundRectangle2D[] floors, RoundRectangle2D[] walls, Cat cat) {
        this.floors = floors.clone();
        this.walls = walls.clone();
        this.cat = cat;
    }

}
