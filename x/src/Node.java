import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;

public class Node {

    public double x;
    public double y;

    public HashMap<Node, Double> neighbors = new HashMap<>();
    public JumpingRect owner;

    public Node(double x, double y, JumpingRect owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public void removeNeighbor(Node node) {
        neighbors.remove(node);
    }

    public void addNeighbor(Node node) {
        double xDist = node.x - x;
        double yDist = node.y - y;
        double distance = Math.sqrt(xDist*xDist + yDist*yDist);
        neighbors.put(node, distance);
    }


}
