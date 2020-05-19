import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class LevelConfigurationObject {

    //enemies
    public ArrayList<Vacuum> vacuumList = new ArrayList<>();
    public ArrayList<Yarnball> yarnballList = new ArrayList<>();
    public ArrayList<TinyMouse> tinyMouseList = new ArrayList<>();
    public ArrayList<Squirrel> squirrelList = new ArrayList<>();
    public ArrayList<Spikes> spikesList = new ArrayList<>();

    //teleporters
    public ArrayList<Teleporter> teleporters = new ArrayList<>();

    //walls, floors, nodes
    public ArrayList<IndexedNodeFloor> indexedNodeFloors = new ArrayList<>();
    public ArrayList<RoundRectangle2D> walls = new ArrayList<>();
    public ArrayList<IndexedNode> indexedNodes = new ArrayList<>();

}
