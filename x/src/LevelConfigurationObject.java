import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelConfigurationObject implements Serializable {

    //enemies
    public ArrayList<EntityConfigurationObject> vacuumConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> yarnballConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> tinyMouseConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> squirrelConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> acidConfigList = new ArrayList<>();

    //teleporters
    //public ArrayList<Teleporter> teleporters = new ArrayList<>();

    //walls, floors, nodes
    public ArrayList<IndexedNodeFloor> indexedNodeFloors = new ArrayList<>();
    public ArrayList<RoundRectangle2D> walls = new ArrayList<>();
    public ArrayList<IndexedNode> indexedNodes = new ArrayList<>();

}
