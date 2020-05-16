import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JumpingRect extends RoundRectangle2D.Double {

//    ///nodes are rectangles
//    ///weights are jumping distance or deltaT of jump
//    private HashMap<JumpingRect, java.lang.Double> neighbors = new HashMap<>();

//    public RoundRectangle2D owner;

    private ArrayList<Node> nodes = new ArrayList<>();
    public void addNode(Node node) {
        nodes.add(node);
    }

    public JumpingRect(int x, int y, int width, int height) {
        super(x, y, width, height, 10, 10);
    }
//    public void addNeighbor(JumpingRect rect) {
//        double distance = 0;
//        neighbors.put(rect, distance);
//    }

    //Should use dynamic programming? - store a list of shortest paths from each node to the others. Probably not necessary
    public ArrayList<Node> getShortestPath(double mouse_x, double cat_x, JumpingRect catFloor, ArrayList<Node> nodeList) {
        //create new nodes for mousePosition and catPosition
        Node mouseNode = new Node(mouse_x, y - 5, this);
        for (Node node : nodes) {
            mouseNode.addNeighbor(node);
        }
        Node catNode = new Node(cat_x, catFloor.y - 5, catFloor);
        for (Node node : catFloor.nodes) {
            catNode.addNeighbor(node);
        }
        ArrayList<Node> Q = new ArrayList<>();
        HashMap<Node, java.lang.Double> dist = new HashMap<>();
        HashMap<Node, Node> prev = new HashMap<>();
        for (Node v : nodeList) {
            dist.put(v, 100000.0);
            Q.add(v);
        }
        dist.put(catNode, 100001.0);
        dist.put(mouseNode, 0.0);
        boolean catNodeReached = false;
        while (!catNodeReached) {
            double minDist = 100002.0;
            Node u = null;
            for (Node node : Q) {
                if (dist.get(node) < minDist) {
                    u = node;
                    minDist = dist.get(node);
                }
            }
            if (u.equals(catNode)) {
                ArrayList<Node> shortestPath = new ArrayList<>();
                while (prev.containsKey(u)) {
                    shortestPath.add(0, prev.get(u));
                    Node temp = prev.get(u);
                    prev.remove(u);
                    u = temp;
                }
                return shortestPath;
            }
            Q.remove(u);
            for (Node neighbor : u.neighbors.keySet()) {
                if (!Q.contains(neighbor)) {
                    continue;
                }
                double alt = dist.get(u) + u.neighbors.get(neighbor);
                if (alt < dist.get(neighbor)) {
                    prev.put(neighbor, u);
                }
            }
        }
        return null;
    }

}
