import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class GenericLevel extends Level {

    public ArrayList<Vacuum> vacuumList = new ArrayList<>();
    public ArrayList<Yarnball> yarnballList = new ArrayList<>();
    public ArrayList<TinyMouse> tinyMouseList = new ArrayList<>();
    public ArrayList<Squirrel> squirrelList = new ArrayList<>();
    public ArrayList<Acid> acidList = new ArrayList<>();
    public SpawnPoint leftSpawn;
    public SpawnPoint rightSpawn;
    public SpawnPoint topSpawn;
    public SpawnPoint bottomSpawn;

    //public ArrayList<Teleporter> teleporters = new ArrayList<>();

    private ArrayList<IndexedNode> nodeList = new ArrayList<>();

    private boolean levelEntered = false;

    public boolean pigMouseWaiting = false;
    public int pigMouseWaitTicks = 0;
    public int elapsedWaitTicks = 0;
    public int pigMouseX_0 = 0;
    public int pigMouseY_0 = 0;
    public PigMouse pigMouse;

    public GenericLevel() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(Color.BLACK);
        setFocusable(true);

        //floors = new IndexedNodeFloor[0];
        ///just need to set the cat and the pigmouse

    }

    public void ConfigureLevel(LevelConfigObj2 configObj) {
        floors = configObj.indexedNodeFloors.toArray(new IndexedNodeFloor[0]);
        walls = configObj.walls.toArray(new RoundRectangle2D[0]);
        nodeList = configObj.indexedNodes;
        ConnectNodesAndFloors();

        for (EntityConfigurationObject vacuumConfig : configObj.vacuumConfigList) {
            Vacuum vacuum = new Vacuum(vacuumConfig.x, vacuumConfig.y, vacuumConfig.x, vacuumConfig.optionalRangeOrBound);
            vacuumList.add(vacuum);
            displayList.AddEnemy(vacuum);
            vacuum.addLevelInfo(new LevelInfo(floors, walls, displayList.cat));
            vacuum.Start();
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
        for (EntityConfigurationObject squirrelConfig : configObj.rSquirrelConfigList) {
            RSquirrel rSquirrel = new RSquirrel(squirrelConfig.x, squirrelConfig.y);
            squirrelList.add(rSquirrel);
            displayList.AddEnemy(rSquirrel);
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
        leftSpawn = configObj.leftSpawnPoint;
        rightSpawn = configObj.rightSpawnPoint;
        topSpawn = configObj.topSpawnPoint;
        bottomSpawn = configObj.bottomSpawnPoint;
    }

    //I think I need to set up references at runtime if this is loaded from serialized data
    private void ConnectNodesAndFloors() {
        for (IndexedNode node : nodeList) {
            for (int neighborID : node.neighborIDs) {
                if (neighborID < nodeList.size() && nodeList.get(neighborID).ID == neighborID) {
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
                    ((IndexedNodeFloor) floor).addNode(node);
                    break;
                }
            }
        }
        //I think I forgot to add nodes to the node list in the level designer. but it's good because this was inefficient
//        for (RoundRectangle2D floor : floors) {
//            if (floor instanceof IndexedNodeFloor) {
//                for (int nodeID : ((IndexedNodeFloor)floor).nodeIDs) {
//                    if (nodeList.get(nodeID).ID == nodeID) {
//                        ((IndexedNodeFloor)floor).addNode(nodeList.get(nodeID));
//                    } else {
//                        for (IndexedNode indexedNode : nodeList) {
//                            if (indexedNode.ID == nodeID) {
//                                ((IndexedNodeFloor)floor).addNode(indexedNode);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
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

//        if (pigMouseWaiting) {
//            if (elapsedWaitTicks >= pigMouseWaitTicks) {
//                pigMouseWaiting = false;
//                pigMouse = new PigMouse(pigMouseX_0, pigMouseY_0);
//                pigMouse.addLevelInfo(new LevelInfo((IndexedNodeFloor[]) floors, nodeList, displayList.cat));
//                displayList.AddEnemy(pigMouse);
//            }
//            else {
//                elapsedWaitTicks++;
//            }
//        }

        boolean tempReached = false;
        boolean absoluteReached = false;
        if (displayList.cat.x > 1165) {
            tempReached = true;
            if (displayList.cat.x > 1195) {
                absoluteReached = true;
            }
        } else if (displayList.cat.x < -35) {
            tempReached = true;
            if (displayList.cat.x < -70) {
                absoluteReached = true;
            }
        } else if (displayList.cat.y > 670) {
            tempReached = true;
            if (displayList.cat.y > 695) {
                absoluteReached = true;
            }
        } else if (displayList.cat.y < -20) {
            tempReached = true;
        }
        if (tempReached && levelEntered && !(displayList.cat.Dying || displayList.cat.Dead)) {
            reachedNextLevel = true;
        } else if (!tempReached && !levelEntered) {
            levelEntered = true;
        } else if (absoluteReached && !(displayList.cat.Dying || displayList.cat.Dead)) {
            reachedNextLevel = true;
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
//        g2.setColor(Color.blue);
//        for (Node node : nodeList) {
//            g2.fill(new Ellipse2D.Double(node.x - 5, node.y - 5, 10, 10));
//            for (Node neighbor : node.neighbors.keySet()) {
//                g2.draw(new Line2D.Double(node.x, node.y, neighbor.x, neighbor.y));
//            }
//        }
        paintDisplayList(g2);
    }


}
