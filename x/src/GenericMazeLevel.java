import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GenericMazeLevel extends Level {

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

    public ArrayList<IndexedNode> nodeList = new ArrayList<>();

    private HashMap<Enemy, Integer> enemyIndexMap = new HashMap<>();

    private boolean levelEntered = false;

    public boolean pigMouseWaiting = false;
    public int pigMouseWaitTicks = 0;
    public int elapsedWaitTicks = 0;

    public int pigRespawnX;
    public int pigRespawnY;
    public int nextPigSpawnX;
    public int nextPigSpawnY;

    public PigMouse pigMouse;

    private LevelConfigObj2 configObj;

    public static Color floorColor = Color.ORANGE;

    private BufferedImage pauseImage = Entity.getBufferedImage("sprites/menuImages/pauseButton.png", 100, 100);
    private BufferedImage pressedPauseImage = Entity.getBufferedImage("sprites/menuImages/pauseButtonDark.png", 100, 100);
    public boolean pauseImagePressed = false;

    public GenericMazeLevel() {
        setPreferredSize(new Dimension(levelWidth, levelHeight));
        displayList = new DisplayList();
        setBackground(backGroundColor);
        setFocusable(true);

        //floors = new IndexedNodeFloor[0];
        ///just need to set the cat and the pigmouse

    }

    public boolean pauseButtonContains(int x, int y) {
        Rectangle2D pauseRect = new Rectangle2D.Double(1145, 5, 50, 30);
        return pauseRect.contains(x, y);
    }

    public void ConfigureLevel(LevelConfigObj2 configObj) {
        this.configObj = configObj;
        floors = configObj.indexedNodeFloors.toArray(new IndexedNodeFloor[0]);
        walls = configObj.walls.toArray(new RoundRectangle2D[0]);
        nodeList = configObj.indexedNodes;
        ConnectNodesAndFloors();

        int index = 0;
        for (EntityConfigurationObject vacuumConfig : configObj.vacuumConfigList) {
            Vacuum vacuum = new Vacuum(vacuumConfig.x, vacuumConfig.y, vacuumConfig.x, vacuumConfig.optionalRangeOrBound);
            vacuumList.add(vacuum);
            displayList.AddEnemy(vacuum);
            vacuum.addLevelInfo(new LevelInfo(floors, walls, displayList.cat));
            //vacuum.Start();
            enemyIndexMap.put(vacuum, index);
            index++;
        }
        index = 0;
        for (EntityConfigurationObject yarnballConfig : configObj.yarnballConfigList) {
            Yarnball yarnball = new Yarnball(yarnballConfig.x, yarnballConfig.y, yarnballConfig.optionalRangeOrBound);
            yarnballList.add(yarnball);
            displayList.AddEnemy(yarnball);
            enemyIndexMap.put(yarnball, index);
            index++;
        }
        index = 0;
        for (EntityConfigurationObject tinyMouseConfig : configObj.tinyMouseConfigList) {
            TinyMouse tinyMouse = new TinyMouse(tinyMouseConfig.x, tinyMouseConfig.optionalRangeOrBound, tinyMouseConfig.y);
            tinyMouseList.add(tinyMouse);
            displayList.AddEnemy(tinyMouse);
            //tinyMouse.Start();
            enemyIndexMap.put(tinyMouse, index);
            index++;
        }
        index = 0;
        for (EntityConfigurationObject squirrelConfig : configObj.squirrelConfigList) {
            Squirrel squirrel = new Squirrel(squirrelConfig.x, squirrelConfig.y);
            squirrelList.add(squirrel);
            displayList.AddEnemy(squirrel);
            enemyIndexMap.put(squirrel, index);
            index++;
        }
        index = 0;
        for (EntityConfigurationObject squirrelConfig : configObj.rSquirrelConfigList) {
            RSquirrel rSquirrel = new RSquirrel(squirrelConfig.x, squirrelConfig.y);
            squirrelList.add(rSquirrel);
            displayList.AddEnemy(rSquirrel);
            enemyIndexMap.put(rSquirrel, index);
            index++;
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
        for (Squirrel squirrel : squirrelList) {
            if (squirrel != null && !squirrel.Dying && !squirrel.Dead) {
                if (squirrel.shootTicks == 220) {
                    Ball ball = squirrel.generateBall(-2.5);
                    SwingUtilities.invokeLater(() -> displayList.AddEnemy(ball));
                    squirrel.shootTicks = 0;
                } else {
                    squirrel.shootTicks++;
                }
            }
        }
        if (pigMouseWaiting) {
            if (elapsedWaitTicks >= pigMouseWaitTicks) {
                pigMouseWaiting = false;
                pigMouse = new PigMouse(nextPigSpawnX, nextPigSpawnY);
                //respawnPoint = nextPigSpawn;
                pigRespawnX = nextPigSpawnX;
                pigRespawnY = nextPigSpawnY;
                pigMouse.addLevelInfo(new LevelInfo((IndexedNodeFloor[]) floors, nodeList, displayList.cat));
                displayList.AddEnemy(pigMouse);
            }
            else {
                elapsedWaitTicks++;
            }
        } else if (pigMouse != null && pigMouse.needsToRespawn) {
            pigMouse.Dispose();
            displayList.removeEnemy(pigMouse);
            pigMouse = new PigMouse(pigRespawnX, pigRespawnY);
            pigMouse.addLevelInfo(new LevelInfo((IndexedNodeFloor[]) floors, nodeList, displayList.cat));
            displayList.AddEnemy(pigMouse);
        }

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


        displayList.cat.Update();
        if (displayList.cat.GetY() > 800) {
            displayList.cat.entityHit(200);
        }
        for (Enemy enemy : displayList.getEnemies()) {
            enemy.Update();
            if (enemy.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
                SwingUtilities.invokeLater(() -> removeFromConfigObj(enemy));
                continue;
            } else if (enemy.Dying) {
                continue;
            }
            for (catProjectile catProjectile : displayList.getCatProjectiles()) {
                if (catProjectile.getHitBox().intersects(enemy.getHitBox()) && enemy.hittable) {
                    //fluffball.stop();
                    SwingUtilities.invokeLater(() -> displayList.removeCatProjectile(catProjectile));
                    SwingUtilities.invokeLater(() -> enemy.entityHit(catProjectile.fluffballDamage()));
                }
            }
            if (enemy.getHitBox().intersects(displayList.cat.getHitBox()) && !enemy.hitCoolingDown) {
                if (displayList.cat != null && !(displayList.cat.Dying || displayList.cat.Dead)) {
                    //SwingUtilities.invokeLater(() -> displayList.cat.entityHit(enemy.getContactDamage()));///threw error
                    displayList.cat.entityHit(enemy.getContactDamage());
                    SwingUtilities.invokeLater(() -> displayList.cat.bump(enemy.x + enemy.width / 2.0));
                }
                enemy.hitCat();
            }
        }
        //check if fluffballs are dead or hit a wall
        for (catProjectile catProjectile : displayList.getCatProjectiles()) {
            catProjectile.Update();
            if (catProjectile.Dead) {
                SwingUtilities.invokeLater(() -> displayList.removeCatProjectile(catProjectile));
            } else {
                for (RoundRectangle2D wall : walls) {
                    if (catProjectile.getHitBox().intersects(wall.getFrame())) {
                        //fluffball.stop();
                        SwingUtilities.invokeLater(() -> displayList.removeCatProjectile(catProjectile));
                    }
                }
            }
        }

        for (ZinzanLife extraLife : displayList.getExtraLives()) {
            if (displayList.cat.getHitBox().intersects(extraLife.getHitBox())) {
                numLives++;
                SwingUtilities.invokeLater(() -> displayList.removeExtraLife(extraLife));
            }
        }

    }

    private void removeFromConfigObj(Enemy enemy) {
        if (configObj != null) {
            if (enemy instanceof Squirrel) {
                if (enemy instanceof RSquirrel) {
                    configObj.rSquirrelConfigList.remove((int)enemyIndexMap.get(enemy));
                } else {
                    configObj.squirrelConfigList.remove((int)enemyIndexMap.get(enemy));
                }
            } else if (enemy instanceof TinyMouse) {
                configObj.tinyMouseConfigList.remove((int)enemyIndexMap.get(enemy));
            } else if (enemy instanceof Vacuum) {
                configObj.vacuumConfigList.remove((int)enemyIndexMap.get(enemy));
            } else if (enemy instanceof  Yarnball) {
                configObj.yarnballConfigList.remove((int)enemyIndexMap.get(enemy));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(floorColor);

        //walls are all floors too.
//        for (RoundRectangle2D rect : walls) {
//            g2.fill(rect);
//        }
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

        if (pauseImagePressed) {
            g2.drawImage(pressedPauseImage, 1145, 5, 50, 30, null);
        } else {
            g2.drawImage(pauseImage, 1145, 5, 50, 30, null);
        }
        paintDisplayList(g2);
    }


}
