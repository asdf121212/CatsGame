import java.io.Serializable;
import java.util.ArrayList;

public class IndexedNode extends Node {

    public int ID;
    protected static int IDcount = 0;
    public int ownerID;
    public ArrayList<Integer> neighborIDs = new ArrayList<>();

    public IndexedNode(double x, double y) {
        super(x, y, null);
        ID = IDcount;
        IDcount++;
    }

    public void SetOwner(IndexedNodeFloor owner) {
        this.owner = owner;
    }

    public void addNeighborID(int neighborID) {
        neighborIDs.add(neighborID);
    }

}
