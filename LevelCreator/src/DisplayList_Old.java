import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class DisplayList_Old {

    //private ArrayList<NiceShape> niceShapes = new ArrayList<>();
    //private NiceShape currentNiceShape;
    public SpawnPoint leftSpawnPoint;
    public SpawnPoint rightSpawnPoint;
    public SpawnPoint topSpawnPoint;
    public SpawnPoint bottomSpawnPoint;

    public ArrayList<DrawingFloor> drawingFloors = new ArrayList<>();
    public ArrayList<NiceLine> lines = new ArrayList<>();

    public ArrayList<Vacuum> vacuums = new ArrayList<>();
    public ArrayList<Yarnball> yarnballs = new ArrayList<>();
    public ArrayList<TinyMouse> tinyMice = new ArrayList<>();
    public ArrayList<Squirrel> squirrels = new ArrayList<>();
    //public ArrayList<IndexedNodeFloor> floors = new ArrayList<>();
    //public ArrayList<RoundRectangle2D> walls = new ArrayList<>();
    public ArrayList<IndexedNode> nodes = new ArrayList<>();
    public ArrayList<DrawingAcid> drawingAcids = new ArrayList<>();

    public DisplayList_Old() {
    }

    public LevelConfigObj2 getConfigObj2() {
        LevelConfigObj2 configObj = new LevelConfigObj2();
        for (Vacuum vacuum : vacuums) {
            EntityConfigurationObject vacuumObj = new EntityConfigurationObject((int)vacuum.x - 50,
                    (int)vacuum.y - 50, (int)vacuum.getRightBound() - 50);
            configObj.vacuumConfigList.add(vacuumObj);
        }
        for (Yarnball yarnball : yarnballs) {
            EntityConfigurationObject yarnballObj = new EntityConfigurationObject((int)yarnball.x - 50,
                    (int)yarnball.y - 50, (int)yarnball.getRange());
            configObj.yarnballConfigList.add(yarnballObj);
        }
        for (TinyMouse tinyMouse : tinyMice) {
            EntityConfigurationObject tinyMouseObj = new EntityConfigurationObject((int)tinyMouse.x - 50,
                    (int)tinyMouse.y - 50, (int)tinyMouse.getRightBound() - 50);
            configObj.tinyMouseConfigList.add(tinyMouseObj);
        }
        for (Squirrel squirrel : squirrels) {
            EntityConfigurationObject squirrelObj = new EntityConfigurationObject((int)squirrel.x - 50, (int)squirrel.y - 50);
            if (squirrel instanceof RSquirrel) {
                configObj.rSquirrelConfigList.add(squirrelObj);
            } else {
                configObj.squirrelConfigList.add(squirrelObj);
            }
        }
        for (DrawingAcid drawingAcid : drawingAcids) {
            EntityConfigurationObject acidObj = new EntityConfigurationObject((int)drawingAcid.getAcid().x - 50,
                    (int)drawingAcid.getAcid().y - 50, drawingAcid.getAcid().width, drawingAcid.getAcid().height);
            configObj.acidConfigList.add(acidObj);
        }
        for (IndexedNode node : nodes) {
            node.x -= 50;
            node.y -= 50;
            configObj.indexedNodes.add(node);
        }
        for (DrawingFloor drawingFloor : drawingFloors) {
            drawingFloor.getShape().x -= 50;
            drawingFloor.getShape().y -= 50;
            configObj.indexedNodeFloors.add(drawingFloor.getShape());
            if (drawingFloor.isAlsoWall) {
                configObj.walls.add(drawingFloor.getShape());
            }
        }
        leftSpawnPoint.x -= 50;
        leftSpawnPoint.y -= 50;
        rightSpawnPoint.x -= 50;
        rightSpawnPoint.y -= 50;
        topSpawnPoint.x -= 50;
        topSpawnPoint.y -= 50;
        bottomSpawnPoint.x -= 50;
        bottomSpawnPoint.y -= 50;
        configObj.leftSpawnPoint = leftSpawnPoint;
        configObj.rightSpawnPoint = rightSpawnPoint;
        configObj.topSpawnPoint = topSpawnPoint;
        configObj.bottomSpawnPoint = bottomSpawnPoint;
        return configObj;
    }

    //adjust for border
//    public LevelConfigurationObject getConfigObj() {
//        LevelConfigurationObject configObj = new LevelConfigurationObject();
//        for (Vacuum vacuum : vacuums) {
//            EntityConfigurationObject vacuumObj = new EntityConfigurationObject((int)vacuum.x - 50,
//                    (int)vacuum.y - 50, (int)vacuum.getRightBound() - 50);
//            configObj.vacuumConfigList.add(vacuumObj);
//        }
//        for (Yarnball yarnball : yarnballs) {
//            EntityConfigurationObject yarnballObj = new EntityConfigurationObject((int)yarnball.x - 50,
//                    (int)yarnball.y - 50, (int)yarnball.getRange());
//            configObj.yarnballConfigList.add(yarnballObj);
//        }
//        for (TinyMouse tinyMouse : tinyMice) {
//            EntityConfigurationObject tinyMouseObj = new EntityConfigurationObject((int)tinyMouse.x - 50,
//                    (int)tinyMouse.y - 50, (int)tinyMouse.getRightBound() - 50);
//            configObj.tinyMouseConfigList.add(tinyMouseObj);
//        }
//        for (Squirrel squirrel : squirrels) {
//            EntityConfigurationObject squirrelObj = new EntityConfigurationObject((int)squirrel.x - 50, (int)squirrel.y - 50);
//            configObj.squirrelConfigList.add(squirrelObj);
//        }
//        for (DrawingAcid drawingAcid : drawingAcids) {
//            EntityConfigurationObject acidObj = new EntityConfigurationObject((int)drawingAcid.getAcid().x - 50,
//                    (int)drawingAcid.getAcid().y - 50, drawingAcid.getAcid().width, drawingAcid.getAcid().height);
//            configObj.acidConfigList.add(acidObj);
//        }
//        for (IndexedNode node : nodes) {
//            node.x -= 50;
//            node.y -= 50;
//            configObj.indexedNodes.add(node);
//        }
//        for (DrawingFloor drawingFloor : drawingFloors) {
//            drawingFloor.getShape().x -= 50;
//            drawingFloor.getShape().y -= 50;
//            configObj.indexedNodeFloors.add(drawingFloor.getShape());
//            if (drawingFloor.isAlsoWall) {
//                configObj.walls.add(drawingFloor.getShape());
//            }
//        }
//        return configObj;
//    }

    //public void addNiceShape(NiceShape shape) {
        //niceShapes.add(shape);
        //currentNiceShape = shape;
    //}
    //public ArrayList<NiceShape> getNiceShapes() {
        //return (ArrayList<NiceShape>) niceShapes.clone();
    //}

    //public void removeNiceShape(int shapeIndex) {
        //niceShapes.remove(shapeIndex);
    //}


}
