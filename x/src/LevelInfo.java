import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class LevelInfo {

    public RoundRectangle2D[] floors;
    public RoundRectangle2D[] walls;

    public NodeFloor[] nodeFloors;
    public ArrayList<? extends Node> nodes;

    private Cat cat;

    public RoundRectangle2D getCatFloor() {
        return cat.currentFloor;
    }

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

    public LevelInfo(IndexedNodeFloor[] rects, ArrayList<IndexedNode> nodes, Cat cat) {
        nodeFloors = rects;
        this.nodes = nodes;
        this.cat = cat;
    }

    public LevelInfo(NodeFloor[] rects, ArrayList<Node> nodes, Cat cat) {
        nodeFloors = rects;
        this.cat = cat;
        this.nodes = nodes;
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
        nodeFloors = null;
    }

}
