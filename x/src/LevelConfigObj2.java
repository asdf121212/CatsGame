import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelConfigObj2 implements Serializable {

    //enemies
    public ArrayList<EntityConfigurationObject> vacuumConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> yarnballConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> tinyMouseConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> squirrelConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> rSquirrelConfigList = new ArrayList<>();
    public ArrayList<EntityConfigurationObject> acidConfigList = new ArrayList<>();


    public SpawnPoint leftSpawnPoint;
    public SpawnPoint rightSpawnPoint;
    public SpawnPoint topSpawnPoint;
    public SpawnPoint bottomSpawnPoint;


    //walls, floors, nodes
    public ArrayList<IndexedNodeFloor> indexedNodeFloors = new ArrayList<>();
    public ArrayList<RoundRectangle2D> walls = new ArrayList<>();
    public ArrayList<IndexedNode> indexedNodes = new ArrayList<>();

}
