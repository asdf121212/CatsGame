import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class Level5 extends Level {
    private NodeFloor bottomFloor = new NodeFloor(-10, 650, 1220, 60);
    private NodeFloor jumpPad1 = new NodeFloor(170, 280, 80, 20);
    //private JumpingRect jumpPad2 = new JumpingRect(300, 245, 80, 20);
    //private JumpingRect jumpPad3 = new JumpingRect(430, 190, 80, 20);
    private NodeFloor jumpPad3 = new NodeFloor(330, 230, 80, 20);
    //private RoundRectangle2D jumpPad4 = new RoundRectangle2D.Double(300, 430, 60, 20, 10, 10);
    private NodeFloor midFloorL = new NodeFloor(-10, 340, 570, 20);
    private NodeFloor midFloorR = new NodeFloor(630, 340, 590, 20);
    private NodeFloor jumpPad7 = new NodeFloor(800, 300, 80, 20);
    private NodeFloor jumpPad8 = new NodeFloor(900, 245, 80, 20);
    private NodeFloor jumpPad9 = new NodeFloor(1000, 190, 80, 20);

    private ArrayList<Node> nodeList = new ArrayList<>();

    private void CreateNodes() {
        Node midFloorL1 = new Node(150, 335, midFloorL);
        Node midFloorL2 = new Node(270, 335, midFloorL);
        Node jp1L = new Node(210, 275, jumpPad1);
        //Node jp1R = new Node(240, 275, jumpPad1);
        Node jp3L = new Node(370, 225, jumpPad3);
        //Node jp3R = new Node(400, 225, jumpPad3);

        nodeList.add(midFloorL1);
        nodeList.add(midFloorL2);
        nodeList.add(jp1L);
        //nodeList.add(jp1R);
        nodeList.add(jp3L);
        //nodeList.add(jp3R);

        midFloorL.addNode(midFloorL1);
        midFloorL.addNode(midFloorL2);
        jumpPad1.addNode(jp1L);
        //jumpPad1.addNode(jp1R);
        jumpPad3.addNode(jp3L);
        //jumpPad3.addNode(jp3R);

        midFloorL1.addNeighbor(jp1L);
        midFloorL2.addNeighbor(jp1L);
        midFloorL1.addNeighbor(midFloorL2);
        midFloorL2.addNeighbor(midFloorL1);
        jp1L.addNeighbor(midFloorL1);
        //jp1L.addNeighbor(j);
        jp1L.addNeighbor(midFloorL2);
        jp1L.addNeighbor(jp3L);
        jp3L.addNeighbor(jp1L);

    }

    private PigMouse pigMouse;

    public Level5() {

        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);
        displayList.cat.SetXY(10, 280);

        walls = new RoundRectangle2D[] {

        };
        floors = new NodeFloor[] {
                bottomFloor,
                jumpPad1,
                //jumpPad2,
                jumpPad3,
                midFloorL,
                midFloorR,
                jumpPad7,
                jumpPad8,
                jumpPad9,
        };
        CreateNodes();

        pigMouse = new PigMouse(300, 260);
        pigMouse.addLevelInfo(new LevelInfo((NodeFloor[])floors, nodeList, displayList.cat));
        displayList.AddEnemy(pigMouse);

    }


    @Override
    public void update() {
        ///update any enemies and stuff that need updating

        super.update();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.ORANGE);
        for (RoundRectangle2D rect : walls) {
            g2.fill(rect);
        }
        for (RoundRectangle2D rect : floors) {
            g2.fill(rect);
        }

        if (displayList.cat.x > 1170) {
            reachedNextLevel = true;
        }


        paintDisplayList(g2);

    }
}
