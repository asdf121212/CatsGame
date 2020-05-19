import sun.jvm.hotspot.jdi.ArrayReferenceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class GenericLevel extends Level {

    public ArrayList<Vacuum> vacuumList = new ArrayList<>();
    public ArrayList<Yarnball> yarnballList = new ArrayList<>();
    public ArrayList<TinyMouse> tinyMouseList = new ArrayList<>();
    public ArrayList<Squirrel> squirrelList = new ArrayList<>();
    public ArrayList<Spikes> spikesList = new ArrayList<>();

    public ArrayList<Teleporter> teleporters = new ArrayList<>();

    private ArrayList<IndexedNode> nodeList = new ArrayList<>();


    public GenericLevel() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);

    }

    public void ConfigureLevel(LevelConfigurationObject configObj) {
        for (Vacuum vac : configObj.vacuumList) {
            vacuumList.add(vac);
            displayList.AddEnemy(vac);
        }
        for (Yarnball yarnball : configObj.yarnballList) {
            yarnballList.add(yarnball);
            displayList.AddEnemy(yarnball);
        }
        for (TinyMouse tinyMouse : configObj.tinyMouseList) {
            tinyMouseList.add(tinyMouse);
            displayList.AddEnemy(tinyMouse);
        }
        for (Squirrel squirrel : configObj.squirrelList) {
            squirrelList.add(squirrel);
            displayList.AddEnemy(squirrel);
        }
//        for (Teleporter teleporter : configObj.teleporters) {
//            teleporters.add(teleporter);
//            displayList........
//        }
        for (Spikes spikes : configObj.spikesList) {
            spikesList.add(spikes);
            displayList.AddEnemy(spikes);
        }
        floors = configObj.indexedNodeFloors.toArray(new RoundRectangle2D[0]);
        walls = configObj.walls.toArray(new RoundRectangle2D[0]);
        nodeList = configObj.indexedNodes;
        ConnectNodesAndFloors();
    }

    //I think I need to set up references at runtime if this is loaded from serialized data
    public void ConnectNodesAndFloors() {
        for (IndexedNode node : nodeList) {
            for (int neighborID : node.neighborIDs) {
                if (nodeList.get(neighborID).ID == neighborID) {
                    node.addNeighbor(nodeList.get(neighborID));
                } else {
                    for (IndexedNode indexedNode : nodeList) {
                        if (indexedNode.ID == neighborID) {
                            node.addNeighbor(indexedNode);
                            break;
                        }
                    }
                }
            }
            for (RoundRectangle2D floor : floors) {
                if (floor instanceof IndexedNodeFloor && ((IndexedNodeFloor)floor).ID == node.ownerID) {
                    node.SetOwner((IndexedNodeFloor) floor);
                    break;
                }
            }
        }
        for (RoundRectangle2D floor : floors) {
            if (floor instanceof IndexedNodeFloor) {
                for (int nodeID : ((IndexedNodeFloor)floor).nodeIDs) {
                    if (nodeList.get(nodeID).ID == nodeID) {
                        ((IndexedNodeFloor)floor).addNode(nodeList.get(nodeID));
                    } else {
                        for (IndexedNode indexedNode : nodeList) {
                            if (indexedNode.ID == nodeID) {
                                ((IndexedNodeFloor)floor).addNode(indexedNode);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        //Vacuums
        for (Vacuum vacuum : vacuumList) {
            vacuum.shootTicks++;
            if (vacuum.shootTicks >= 400) {
                vacuum.shootTicks = 0;
                displayList.AddEnemy(vacuum.generateBall());
            }
        }
        //Yarnballs
        for (Yarnball yarnball : yarnballList) {
            if (yarnball.enteredAttackZone(displayList.cat.GetX() + 75, displayList.cat.GetY() + 25)) {
                yarnball.Start();
            }
        }
        //TinyMice
        for (TinyMouse tinyMouse : tinyMouseList) {
            tinyMouse.update();
        }
        //Squirrels
        for (Squirrel squirrel : squirrelList) {
            if (squirrel != null && !squirrel.Dying && !squirrel.Dead) {
                if (squirrel.ticks == 220) {
                    Ball ball = squirrel.generateBall(-2.5);
                    SwingUtilities.invokeLater(() -> displayList.AddEnemy(ball));
                    squirrel.ticks = 0;
                } else {
                    squirrel.ticks++;
                }
            }
        }

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
        paintDisplayList(g2);
    }


}
