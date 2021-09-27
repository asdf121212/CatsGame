import java.util.ArrayList;

public class IndexedNodeFloor extends NodeFloor {

    public int ID;
    protected static int IDcount = 0;

    public ArrayList<Integer> nodeIDs = new ArrayList<>();

    public IndexedNodeFloor(int x, int y, int width, int height) {
        super(x, y, width, height);
        ID = IDcount;
        IDcount++;
    }

    public void addNodeID(int nodeID) {
        nodeIDs.add(nodeID);
    }

}
