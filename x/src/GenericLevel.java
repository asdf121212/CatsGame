import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class GenericLevel extends Level {

    public ArrayList<Vacuum> vacuumList = new ArrayList<>();
    public ArrayList<Yarnball> yarnballList = new ArrayList<>();
    public ArrayList<TinyMouse> tinyMouseList = new ArrayList<>();
    public ArrayList<Squirrel> squirrelList = new ArrayList<>();
    public ArrayList<Acid> acidList = new ArrayList<>();

    //public ArrayList<Teleporter> teleporters = new ArrayList<>();

    private ArrayList<IndexedNode> nodeList = new ArrayList<>();


    public GenericLevel() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);

        ///just need to set the cat and the pigmouse

    }

    public void ConfigureLevel(LevelConfigurationObject configObj) {
        floors = configObj.indexedNodeFloors.toArray(new RoundRectangle2D[0]);
        walls = configObj.walls.toArray(new RoundRectangle2D[0]);
        nodeList = configObj.indexedNodes;
        ConnectNodesAndFloors();

        for (EntityConfigurationObject vacuumConfig : configObj.vacuumConfigList) {
            Vacuum vacuum = new Vacuum(vacuumConfig.x, vacuumConfig.y, vacuumConfig.x, vacuumConfig.optionalRangeOrBound);
            vacuumList.add(vacuum);
            displayList.AddEnemy(vacuum);
            vacuum.addLevelInfo(new LevelInfo(floors, walls, displayList.cat));
        }
        for (EntityConfigurationObject yarnballConfig : configObj.yarnballConfigList) {
            Yarnball yarnball = new Yarnball(yarnballConfig.x, yarnballConfig.y, yarnballConfig.optionalRangeOrBound);
            yarnballList.add(yarnball);
            displayList.AddEnemy(yarnball);
        }
        for (EntityConfigurationObject tinyMouseConfig : configObj.tinyMouseConfigList) {
            TinyMouse tinyMouse = new TinyMouse(tinyMouseConfig.x, tinyMouseConfig.optionalRangeOrBound, tinyMouseConfig.y);
            tinyMouseList.add(tinyMouse);
            displayList.AddEnemy(tinyMouse);
        }
        for (EntityConfigurationObject squirrelConfig : configObj.squirrelConfigList) {
            Squirrel squirrel = new Squirrel(squirrelConfig.x, squirrelConfig.y);
            squirrelList.add(squirrel);
            displayList.AddEnemy(squirrel);
        }
//        for (Teleporter teleporter : configObj.teleporters) {
//            teleporters.add(teleporter);
//            displayList........
//        }
        for (EntityConfigurationObject acidConfig : configObj.acidConfigList) {
            Acid acid = new Acid(acidConfig.x, acidConfig.y, acidConfig.width, acidConfig.height);
            acidList.add(acid);
            displayList.AddEnemy(acid);
        }
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
