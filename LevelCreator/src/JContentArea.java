import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class JContentArea extends JPanel {

    private Tools currentTool;
    private Color currentColor = Color.BLACK;
    private int currentWidth = 2;

    private BufferedImage zinzanImage = Entity.getBufferedImage("Images/zinzanStill.png", 100, 100);

    private Rectangle2D border = new Rectangle2D.Double(50, 50, 1200, 700);

    private Rectangle2D lSpawnRect = new Rectangle2D.Double(50, 150, 100, 500);
    private Rectangle2D rSpawnRect = new Rectangle2D.Double(1150, 150, 100, 500);
    private Rectangle2D topSpawnRect = new Rectangle2D.Double(250, 50, 800, 100);
    private Rectangle2D bottomSpawnRect =new Rectangle2D.Double(250, 650, 800, 100);


    private DrawingFloor currentDrawingFloor;
    private DrawingFloor currentlySelectedDrawingFloor;
    private DrawingAcid currentDrawingAcid;
    private DrawingAcid currentlySelectedDrawingAcid;
    private NiceLine currentLine;
    private Entity currentEntity;
    private Entity currentlySelectedEntity;
    private IndexedNode currentNode;
    private IndexedNode currentlySelectedNode;
    private SpawnPoint currentSpawnPoint;
    private SpawnPoint currentlySelectedSpawnPoint;

    private boolean settingBound = false;
    private boolean settingOwner = false;
    private boolean settingSpawnSide = false;

    private boolean movingRect = false;
    private boolean movingEntity = false;
    private boolean movingNode = false;
    private boolean movingSpawnPoint = false;

    private double offsetX;
    private double offsetY;

    private Rectangle2D resizeBox1;

    private boolean resizing = false;
    private Rectangle2D currentResizeBox;
    private boolean movingAcid = false;
    private boolean resizingAcid = false;

    public DisplayList_Old displayList;

    private Point2D currentStartPoint;

    public void loadConfigObj2(LevelConfigObj2 configObj) {
        for (EntityConfigurationObject squirrelObj : configObj.squirrelConfigList) {
            displayList.squirrels.add(new Squirrel(squirrelObj.x + 50, squirrelObj.y + 50));
        }
        for (EntityConfigurationObject rSquirrelObj : configObj.rSquirrelConfigList) {
            displayList.squirrels.add(new RSquirrel(rSquirrelObj.x + 50, rSquirrelObj.y + 50));
        }
        for (EntityConfigurationObject vacuumObj : configObj.vacuumConfigList) {
            displayList.vacuums.add(new Vacuum(vacuumObj.x + 50, vacuumObj.y + 50, vacuumObj.x + 50, vacuumObj.optionalRangeOrBound + 50));
        }
        for (EntityConfigurationObject tinyMouseObj : configObj.tinyMouseConfigList) {
            displayList.tinyMice.add(new TinyMouse(tinyMouseObj.x + 50, tinyMouseObj.optionalRangeOrBound + 50, tinyMouseObj.y + 50));
        }
        for (EntityConfigurationObject yarnballObj : configObj.yarnballConfigList) {
            displayList.yarnballs.add(new Yarnball(yarnballObj.x + 50, yarnballObj.y + 50, yarnballObj.optionalRangeOrBound));
        }
        for (EntityConfigurationObject acidObj : configObj.acidConfigList) {
            displayList.drawingAcids.add(new DrawingAcid(acidObj.x + 50, acidObj.y + 50, acidObj.width, acidObj.height));
        }
        int maxFloorID = 0;
        for (IndexedNodeFloor nodeFloor : configObj.indexedNodeFloors) {
            nodeFloor.x += 50;
            nodeFloor.y += 50;
            DrawingFloor floor = new DrawingFloor(nodeFloor);
            floor.isAlsoWall = configObj.walls.contains(nodeFloor);
            displayList.drawingFloors.add(floor);
            if (nodeFloor.ID > maxFloorID) {
                maxFloorID = nodeFloor.ID;
            }
        }
        int maxNodeID = 0;
        for (IndexedNode node : configObj.indexedNodes) {
            node.x += 50;
            node.y += 50;
            displayList.nodes.add(node);
            if (node.ID > maxNodeID) {
                maxNodeID = node.ID;
            }
        }
        IndexedNode.IDcount = maxNodeID + 1;
        IndexedNodeFloor.IDcount = maxFloorID + 1;
        ConnectNodesAndFloors();

        displayList.leftSpawnPoint = configObj.leftSpawnPoint;
        displayList.rightSpawnPoint = configObj.rightSpawnPoint;
        displayList.topSpawnPoint = configObj.topSpawnPoint;
        displayList.bottomSpawnPoint = configObj.bottomSpawnPoint;
        displayList.leftSpawnPoint.x += 50;
        displayList.leftSpawnPoint.y += 50;
        displayList.rightSpawnPoint.x += 50;
        displayList.rightSpawnPoint.y += 50;
        displayList.topSpawnPoint.x += 50;
        displayList.topSpawnPoint.y += 50;
        displayList.bottomSpawnPoint.x += 50;
        displayList.bottomSpawnPoint.y += 50;
    }

    private void ConnectNodesAndFloors() {
        for (IndexedNode node : displayList.nodes) {
            for (int neighborID : node.neighborIDs) {
                for (IndexedNode indexedNode : displayList.nodes) {
                    if (indexedNode.ID == neighborID) {
                        NiceLine line = new NiceLine(node.x - 3, node.y - 3, indexedNode.x + 3, indexedNode.y + 3);
                        line.good = true;
                        displayList.lines.add(line);
                        break;
                    }
                }
            }
        }
    }

    public void delete() {
        if (currentlySelectedDrawingFloor != null) {
            displayList.drawingFloors.remove(currentlySelectedDrawingFloor);
            currentlySelectedDrawingFloor = null;
            repaint();
        } else if (currentlySelectedEntity != null) {
            if (currentlySelectedEntity instanceof Squirrel) {
                displayList.squirrels.remove(currentlySelectedEntity);
                currentlySelectedEntity = null;
                repaint();
            } else if (currentlySelectedEntity instanceof Vacuum) {
                displayList.vacuums.remove(currentlySelectedEntity);
                currentlySelectedEntity = null;
                repaint();
            } else if (currentlySelectedEntity instanceof TinyMouse) {
                displayList.tinyMice.remove(currentlySelectedEntity);
                currentlySelectedEntity = null;
                repaint();
            } else if (currentlySelectedEntity instanceof Yarnball) {
                displayList.yarnballs.remove(currentlySelectedEntity);
                currentlySelectedEntity = null;
                repaint();
            }
        } else if (currentlySelectedDrawingAcid != null) {
            displayList.drawingAcids.remove(currentlySelectedDrawingAcid);
            currentlySelectedDrawingAcid = null;
            repaint();
        } else if (currentlySelectedSpawnPoint != null) {
            if (displayList.leftSpawnPoint == currentlySelectedSpawnPoint) {
                displayList.leftSpawnPoint = null;
            } else if (displayList.rightSpawnPoint == currentlySelectedSpawnPoint) {
                displayList.rightSpawnPoint = null;
            } else if (displayList.topSpawnPoint == currentlySelectedSpawnPoint) {
                displayList.topSpawnPoint = null;
            } else if (displayList.bottomSpawnPoint == currentlySelectedSpawnPoint) {
                displayList.bottomSpawnPoint = null;
            }
            currentlySelectedSpawnPoint = null;
            repaint();
        }
    }

    public JContentArea() {

        displayList = new DisplayList_Old();

        setFocusable(true);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (settingBound) {
                    if (currentEntity != null) {
                        if (currentEntity instanceof Vacuum) {
                            ((Vacuum)currentEntity).setRightBound(e.getX());
                            repaint();
                        } else if (currentEntity instanceof TinyMouse) {
                            ((TinyMouse)currentEntity).setRight_xBound(e.getX());
                            repaint();
                        } else if (currentEntity instanceof Yarnball) {
                            ((Yarnball)currentEntity).setRange(currentEntity.x - e.getX());
                            repaint();
                        }
                    }
                } else if (settingOwner) {
                    for (DrawingFloor drawingFloor : displayList.drawingFloors) {
                        if (drawingFloor.contains(e.getX(), e.getY())) {
                            drawingFloor.highlight = true;
                        } else {
                            drawingFloor.highlight = false;
                        }
                    }
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("x: " + e.getX() + "y: " + e.getY());
                if (currentTool == Tools.LINE) {
                    IndexedNode startNode = null;
                    for (IndexedNode node : displayList.nodes) {
                        Ellipse2D ellipse = new Ellipse2D.Double(node.x - 10, node.y - 10, 20, 20);
                        if (ellipse.contains(e.getX(), e.getY())) {
                            startNode = node;
                        }
                    }
                    if (startNode != null) {
                        currentStartPoint = new Point2D.Double(e.getX(), e.getY());
                        currentLine = new NiceLine(e.getX(), e.getY(), e.getX(), e.getY());
                        displayList.lines.add(currentLine);
                    }
                } else if (currentTool == Tools.FLOOR) {
                    currentStartPoint = new Point2D.Double(e.getX(), e.getY());
                    //currentShape = new Rectangle2D.Double(e.getX(), e.getY(), 1, 1);
                    currentDrawingFloor = new DrawingFloor(e.getX(), e.getY(), 1, 1);
                    //displayList.addNiceShape(currentNiceShape);
                    displayList.drawingFloors.add(currentDrawingFloor);
                }
                else if (currentTool == Tools.ACID) {
                    currentStartPoint = new Point2D.Double(e.getX(), e.getY());
                    currentDrawingAcid = new DrawingAcid(e.getX(), e.getY(), 1, 1);
                    displayList.drawingAcids.add(currentDrawingAcid);
                }
                else if (currentTool == Tools.NODE) {
                    if (displayList.drawingFloors.size() > 0) {
                        if (!settingOwner) {
                            currentNode = new IndexedNode(e.getX(), e.getY());
                            displayList.nodes.add(currentNode);
                        } else {
                            DrawingFloor owner = null;
                            for (DrawingFloor drawingFloor : displayList.drawingFloors) {
                                if (drawingFloor.contains(e.getX(), e.getY())) {
                                    owner = drawingFloor;
                                }
                            }
                            if (owner != null) {
                                owner.highlight = false;
                                currentNode.ownerID = owner.getShape().ID;
                                currentNode = null;
                                settingOwner = false;
                            }
                        }
                    }
                }
                else if (currentTool == Tools.SPAWN || currentTool == Tools.TRAP_SPAWN) {
                    if (settingSpawnSide) {
                        if (lSpawnRect.contains(e.getX(), e.getY())) {
                            displayList.leftSpawnPoint = currentSpawnPoint;
                            settingSpawnSide = false;
                            currentSpawnPoint = null;
                        } else if (rSpawnRect.contains(e.getX(), e.getY())) {
                            displayList.rightSpawnPoint = currentSpawnPoint;
                            settingSpawnSide = false;
                            currentSpawnPoint = null;
                        } else if (topSpawnRect.contains(e.getX(), e.getY())) {
                            displayList.topSpawnPoint = currentSpawnPoint;
                            settingSpawnSide = false;
                            currentSpawnPoint = null;
                        } else if (bottomSpawnRect.contains(e.getX(), e.getY())) {
                            displayList.bottomSpawnPoint = currentSpawnPoint;
                            settingSpawnSide = false;
                            currentSpawnPoint = null;
                        }
                    } else {
                        currentSpawnPoint = new SpawnPoint();
                        currentSpawnPoint.x = e.getX();
                        currentSpawnPoint.y = e.getY();
                        currentSpawnPoint.isFallTrap = currentTool == Tools.TRAP_SPAWN;
                    }
                }
                else if (currentTool == Tools.VACUUM) {
                    if (settingBound) {
                        ((Vacuum)currentEntity).setRightBound(e.getX());
                        settingBound = false;
                        currentEntity = null;
                    } else {
                        currentEntity = new Vacuum(e.getX(), e.getY(), e.getX(), e.getX());
                        displayList.vacuums.add((Vacuum) currentEntity);
                    }
                }
                else if (currentTool == Tools.TINYMOUSE) {
                    if (settingBound) {
                        ((TinyMouse)currentEntity).setRight_xBound(e.getX());
                        settingBound = false;
                        currentEntity = null;
                    } else {
                        currentEntity = new TinyMouse(e.getX(), e.getX(), e.getY());
                        displayList.tinyMice.add((TinyMouse) currentEntity);
                    }
                }
                else if (currentTool == Tools.YARNBALL) {
                    if (settingBound) {
                        ((Yarnball)currentEntity).setRange(currentEntity.x - e.getX());
                        settingBound = false;
                        currentEntity = null;
                    } else {
                        currentEntity = new Yarnball(e.getX(), e.getY(), 0);
                        displayList.yarnballs.add((Yarnball) currentEntity);
                    }
                }
                else if (currentTool == Tools.SQUIRREL || currentTool == Tools.RSQUIRREL) {
                    if (currentTool == Tools.SQUIRREL) {
                        currentEntity = new Squirrel(e.getX() - 75, e.getY() - 60);
                    } else {
                        currentEntity = new RSquirrel(e.getX() - 75, e.getY() - 60);
                    }
                    displayList.squirrels.add((Squirrel)currentEntity);
                }
                else if (currentTool == Tools.SELECT) {
                    Rectangle2D selectBox = new Rectangle2D.Double(e.getX() - 2, e.getY() - 2, 4, 4);
                    Ellipse2D spawnEllipse = null;
                    Ellipse2D nodeEllipse = null;
                    if (currentlySelectedSpawnPoint != null) {
                        spawnEllipse = new Ellipse2D.Double(currentlySelectedSpawnPoint.x - 10, currentlySelectedSpawnPoint.y - 10, 20, 20);
                    }
                    if (currentlySelectedNode != null) {
                        nodeEllipse = new Ellipse2D.Double(currentlySelectedNode.x - 5, currentlySelectedNode.y - 5, 10, 10);
                    }
                    movingRect = false;
                    movingEntity = false;
                    movingNode = false;
                    movingAcid = false;
                    movingSpawnPoint = false;
                    resizing = false;
                    resizingAcid = false;
                    currentResizeBox = null;
                    if (resizeBox1 != null && (resizeBox1.contains(e.getPoint()))) {
                        if (currentlySelectedDrawingFloor != null) {
                            resizing = true;
                            currentStartPoint = new Point2D.Double(currentlySelectedDrawingFloor.getBoundingBox().getX(),
                                    currentlySelectedDrawingFloor.getBoundingBox().getY());
                            currentResizeBox = resizeBox1;
                        } else if (currentlySelectedDrawingAcid != null) {
                            resizingAcid = true;
                            currentStartPoint = new Point2D.Double(currentlySelectedDrawingAcid.getBoundingBox().getX(),
                                    currentlySelectedDrawingAcid.getBoundingBox().getY());
                            currentResizeBox = resizeBox1;
                        }
                    }
//                    else if (resizeBox2 != null && resizeBox2.contains(e.getPoint())) {
//                        resizing = true;
//                        currentResizeBox = resizeBox2;
//                    }
                    else if (currentlySelectedDrawingFloor != null && (currentlySelectedDrawingFloor.contains(selectBox)
                        || currentlySelectedDrawingFloor.intersects(selectBox))) {

                        ///////moving
                        movingRect = true;
                        offsetX = e.getX() - currentlySelectedDrawingFloor.getBoundingBox().getX();
                        offsetY = e.getY() - currentlySelectedDrawingFloor.getBoundingBox().getY();
                    }
                    else if (currentlySelectedDrawingAcid != null && (currentlySelectedDrawingAcid.contains(selectBox)
                        || currentlySelectedDrawingAcid.intersects(selectBox))) {
                        movingAcid = true;
                        offsetX = e.getX() - currentlySelectedDrawingAcid.getBoundingBox().getX();
                        offsetY = e.getY() - currentlySelectedDrawingAcid.getBoundingBox().getY();
                    }
                    else if (currentlySelectedEntity != null && (currentlySelectedEntity.getHitBox().contains(selectBox)
                            || currentlySelectedEntity.getHitBox().intersects(selectBox))) {

                        ///////moving
                        movingEntity = true;
                        offsetX = e.getX() - currentlySelectedEntity.getHitBox().getX();
                        offsetY = e.getY() - currentlySelectedEntity.getHitBox().getY();

                    } else if (currentlySelectedSpawnPoint != null && (spawnEllipse.contains(selectBox) || spawnEllipse.intersects(selectBox))) {
                        movingSpawnPoint = true;
                        offsetX = e.getX() - currentlySelectedSpawnPoint.x;
                        offsetY = e.getY() - currentlySelectedSpawnPoint.y;
                    }
                    else if (currentlySelectedNode != null && (nodeEllipse.getFrame().contains(selectBox) || nodeEllipse.getFrame().intersects(selectBox))) {
                            ///////moving
                            movingNode = true;
                            offsetX = e.getX() - currentlySelectedNode.x;
                            offsetY = e.getY() - currentlySelectedNode.y;
                    }
                    else {
                        currentlySelectedDrawingFloor = null;
                        currentlySelectedEntity = null;
                        currentlySelectedDrawingAcid = null;
                        currentlySelectedSpawnPoint = null;
                        currentlySelectedNode = null;
                        ///deselect
                        resizeBox1 = null;
                        //resizeBox2 = null;
                        deselectNode();
                        currentResizeBox = null;

                        for (DrawingFloor drawingFloor : displayList.drawingFloors) {
                            if (drawingFloor != currentlySelectedDrawingFloor) {
                                if (drawingFloor.intersects(selectBox) || drawingFloor.contains(selectBox)) {
                                    currentlySelectedDrawingFloor = drawingFloor;
                                }
                            }
                        }
                        if (currentlySelectedDrawingFloor != null) {
                            setSelected(currentlySelectedDrawingFloor);
                            repaint();
                        } else {
                            for (SpawnPoint spawnPoint : new SpawnPoint[] { displayList.leftSpawnPoint, displayList.rightSpawnPoint,
                                displayList.topSpawnPoint, displayList.bottomSpawnPoint }) {
                                if (spawnPoint != null) {
                                    Ellipse2D ellipse = new Ellipse2D.Double(spawnPoint.x - 10, spawnPoint.y - 10, 20, 20);
                                    if (ellipse.contains(selectBox) || ellipse.intersects(selectBox)) {
                                        movingSpawnPoint = true;
                                        offsetX = e.getX() - spawnPoint.x;
                                        offsetY = e.getY() - spawnPoint.y;
                                        currentlySelectedSpawnPoint = spawnPoint;
                                    }
                                }
                            }
                            for (DrawingAcid drawingAcid : displayList.drawingAcids) {
                                if (drawingAcid != currentlySelectedDrawingAcid) {
                                    if (drawingAcid.intersects(selectBox) || drawingAcid.contains(selectBox)) {
                                        currentlySelectedDrawingAcid = drawingAcid;
                                    }
                                }
                            }
                            if (currentlySelectedDrawingAcid != null) {
                                setSelected(currentlySelectedDrawingAcid);
                                repaint();
                            }
                            if (currentlySelectedDrawingAcid == null) {
                                for (Squirrel squirrel : displayList.squirrels) {
                                    if (squirrel.getHitBox().contains(selectBox) || squirrel.getHitBox().intersects(selectBox)) {
                                        currentlySelectedEntity = squirrel;
                                        offsetX = e.getX() - currentlySelectedEntity.x;
                                        offsetY = e.getY() - currentlySelectedEntity.y;
                                        movingEntity = true;
                                    }
                                }
                            }
                            if (currentlySelectedEntity == null) {
                                for (Vacuum vacuum : displayList.vacuums) {
                                    if (vacuum.getHitBox().contains(selectBox) || vacuum.getHitBox().intersects(selectBox)) {
                                        currentlySelectedEntity = vacuum;
                                        offsetX = e.getX() - currentlySelectedEntity.x;
                                        offsetY = e.getY() - currentlySelectedEntity.y;
                                        movingEntity = true;
                                    }
                                }
                            }
                            if (currentlySelectedEntity == null) {
                                for (Yarnball yarnball : displayList.yarnballs) {
                                    if (yarnball.getHitBox().contains(selectBox) || yarnball.getHitBox().intersects(selectBox)) {
                                        currentlySelectedEntity = yarnball;
                                        offsetX = e.getX() - currentlySelectedEntity.x;
                                        offsetY = e.getY() - currentlySelectedEntity.y;
                                        movingEntity = true;
                                    }
                                }
                            }
                            if (currentlySelectedEntity == null) {
                                for (TinyMouse tinyMouse : displayList.tinyMice) {
                                    if (tinyMouse.getHitBox().contains(selectBox) || tinyMouse.getHitBox().intersects(selectBox)) {
                                        currentlySelectedEntity = tinyMouse;
                                        offsetX = e.getX() - currentlySelectedEntity.x;
                                        offsetY = e.getY() - currentlySelectedEntity.y;
                                        movingEntity = true;
                                    }
                                }
                            }
                            if (currentlySelectedEntity == null) {
                                for (IndexedNode node : displayList.nodes) {
                                    Ellipse2D ellipse = new Ellipse2D.Double(node.x - 5, node.y - 5, 10, 10);
                                    if (selectBox.intersects(ellipse.getFrame())) {
                                        currentlySelectedNode = node;
                                        movingNode = true;
                                        setSelected(node);
                                    }
                                }
                            }
                        }
                    }
                }
                repaint();
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                if (resizing) {
                    resizing = false;
                    if (currentlySelectedDrawingFloor != null) {
                        setSelected(currentlySelectedDrawingFloor);
                    }
                }
                if (resizingAcid) {
                    resizingAcid = false;
                    if (currentlySelectedDrawingAcid != null) {
                        setSelected(currentlySelectedDrawingAcid);
                    }
                }
                if (movingRect) {
                    movingRect = false;
                }
                if (movingNode) {
                    movingNode = false;
                }
                if (movingEntity) {
                    movingEntity = false;
                }
                if (movingAcid) {
                    movingAcid = false;
                }
                if (movingSpawnPoint) {
                    movingSpawnPoint = false;
                }
                else if (currentTool == Tools.TRAP_SPAWN || currentTool == Tools.SPAWN) {
                    if (currentSpawnPoint != null && !settingSpawnSide) {
                        settingSpawnSide = true;
                    }
                }
                else if (currentTool == Tools.NODE) {
                    if (currentNode != null && !settingOwner) {
                        settingOwner = true;
                    }
                }
                else if (currentTool == Tools.WALL) {
                    for (DrawingFloor drawingFloor : displayList.drawingFloors) {
                        if (drawingFloor.contains(e.getX(), e.getY())) {
                            drawingFloor.isAlsoWall = !drawingFloor.isAlsoWall;
                        }
                    }
                }
                else if (currentTool == Tools.VACUUM) {
                    if (currentEntity != null && !settingBound) {
                        settingBound = true;
                    }
                }
                else if (currentTool == Tools.TINYMOUSE) {
                    if (currentEntity != null && !settingBound) {
                        settingBound = true;
                    }
                }
                else if (currentTool == Tools.YARNBALL) {
                    if (currentEntity != null && !settingBound) {
                        settingBound = true;
                    }
                } else if (currentTool == Tools.LINE) {
                    if (currentLine != null) {
                        currentLine.getShape().setLine(currentStartPoint.getX(), currentStartPoint.getY(), e.getX(), e.getY());
                        IndexedNode endNode = null;
                        IndexedNode startNode = null;
                        for (IndexedNode node : displayList.nodes) {
                            Ellipse2D ellipse = new Ellipse2D.Double(node.x - 10, node.y - 10, 20, 20);
                            if (ellipse.contains(e.getX(), e.getY())) {
                                endNode = node;
                            } else if (ellipse.contains(currentStartPoint.getX(), currentStartPoint.getY())) {
                                startNode = node;
                            }
                        }
                        if (endNode != null && startNode != null && !endNode.equals(startNode)) {
                            currentLine.good = true;
                            startNode.addNeighborID(endNode.ID);
                            currentLine = null;
                            currentStartPoint = null;
                        } else {
                            displayList.lines.remove(currentLine);
                            currentLine = null;
                            currentStartPoint = null;
                        }
                    }
                }
                repaint();
            }

        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentTool == Tools.LINE) {
                    if (currentStartPoint != null && currentLine != null) {
                        currentLine.getShape().setLine(currentStartPoint.getX(), currentStartPoint.getY(), e.getX(), e.getY());
                        boolean good = false;
                        for (IndexedNode node : displayList.nodes) {
                            Ellipse2D ellipse = new Ellipse2D.Double(node.x - 10, node.y - 10, 20, 20);
                            if (ellipse.contains(e.getX(), e.getY()) && !ellipse.contains(currentStartPoint)) {
                                good = true;
                                break;
                            }
                        }
                        currentLine.good = good;
                    }
                }
                else if (currentTool == Tools.FLOOR) {
                    double x = currentStartPoint.getX();
                    double y = currentStartPoint.getY();
                    double w;
                    double h;
                    if (e.getX() < x) {
                        w = x - e.getX();
                        x = e.getX();
                    } else {
                        w = e.getX() - x;
                    }
                    if (e.getY() < y) {
                        h = y - e.getY();
                        y = e.getY();
                    } else {
                        h = e.getY() - y;
                    }
                    currentDrawingFloor.setRect(x, y, w, h);
                }
                else if (currentTool == Tools.ACID) {
                    double x = currentStartPoint.getX();
                    double y = currentStartPoint.getY();
                    double w;
                    double h;
                    if (e.getX() < x) {
                        w = x - e.getX();
                        x = e.getX();
                    } else {
                        w = e.getX() - x;
                    }
                    if (e.getY() < y) {
                        h = y - e.getY();
                        y = e.getY();
                    } else {
                        h = e.getY() - y;
                    }
                    currentDrawingAcid.setRect(x, y, w, h);
                }
                else if (currentTool == Tools.NODE) {
                    if (currentNode != null) {
                        currentNode.x = e.getX();
                        currentNode.y = e.getY();
                    }
                }
                else if (currentTool == Tools.SPAWN || currentTool == Tools.TRAP_SPAWN) {
                    if (currentSpawnPoint != null) {
                        currentSpawnPoint.x = e.getX();
                        currentSpawnPoint.y = e.getY();
                    }
                }
                else if (currentTool == Tools.VACUUM || currentTool == Tools.SQUIRREL || currentTool == Tools.TINYMOUSE
                    || currentTool == Tools.YARNBALL || currentTool == Tools.RSQUIRREL) {
                    if (currentEntity != null) {
                        currentEntity.x = e.getX() - currentEntity.width / 2.0;
                        currentEntity.y = e.getY() - currentEntity.height / 2.0;

                        if (currentEntity instanceof Vacuum) {
                            ((Vacuum)currentEntity).setLeftBound(e.getX());
                            ((Vacuum)currentEntity).setRightBound(e.getX());
                        } else if (currentEntity instanceof Yarnball) {
                            //((Yarnball)c)it's already 0
                        } else if (currentEntity instanceof TinyMouse) {
                            ((TinyMouse)currentEntity).setLeft_xBound(e.getX());
                            ((TinyMouse)currentEntity).setRight_xBound(e.getX());
                        }
                    }
                }
                else if (currentTool == Tools.SELECT) {
                    if (movingRect) {
                        setSelected(currentlySelectedDrawingFloor);
                        currentlySelectedDrawingFloor.setPosition(e.getX(), e.getY(), offsetX, offsetY);
                    }
                    else if (movingAcid) {
                        setSelected(currentlySelectedDrawingAcid);
                        currentlySelectedDrawingAcid.setPosition(e.getX(), e.getY(), offsetX, offsetY);
                    }
                    else if (resizing) {
                        double x = currentStartPoint.getX();
                        double y = currentStartPoint.getY();
                        double w;
                        double h;
                        if (e.getX() < x) {
                            w = x - e.getX();
                            x = e.getX();
                        } else {
                            w = e.getX() - x;
                        }
                        if (e.getY() < y) {
                            h = y - e.getY();
                            y = e.getY();
                        } else {
                            h = e.getY() - y;
                        }
                        currentlySelectedDrawingFloor.setRect(x, y, w, h);
                        currentResizeBox.setRect(e.getX() - 5, e.getY() - 5, 10, 10);
                    }
                    else if (resizingAcid) {
                        double x = currentStartPoint.getX();
                        double y = currentStartPoint.getY();
                        double w;
                        double h;
                        if (e.getX() < x) {
                            w = x - e.getX();
                            x = e.getX();
                        } else {
                            w = e.getX() - x;
                        }
                        if (e.getY() < y) {
                            h = y - e.getY();
                            y = e.getY();
                        } else {
                            h = e.getY() - y;
                        }
                        currentlySelectedDrawingAcid.setRect(x, y, w, h);
                        currentResizeBox.setRect(e.getX() - 5, e.getY() - 5, 10, 10);
                    }
                    else if (movingEntity) {
                        double prevX = currentlySelectedEntity.x;
                        currentlySelectedEntity.x = e.getX() - offsetX;
                        currentlySelectedEntity.y = e.getY() - offsetY;
                        if (currentlySelectedEntity instanceof Vacuum) {
                            int diff = (int)Math.round(((Vacuum)currentlySelectedEntity).getRightBound() - prevX);
                            ((Vacuum)currentlySelectedEntity).setLeftBound((int)Math.round(currentlySelectedEntity.x));
                            ((Vacuum)currentlySelectedEntity).setRightBound((int)Math.round(currentlySelectedEntity.x) + diff);
                        } else if (currentlySelectedEntity instanceof TinyMouse) {
                            int diff = (int)Math.round(((TinyMouse)currentlySelectedEntity).getRightBound() - prevX);
                            ((TinyMouse)currentlySelectedEntity).setLeft_xBound((int)Math.round(currentlySelectedEntity.x));
                            ((TinyMouse)currentlySelectedEntity).setRight_xBound((int)Math.round(currentlySelectedEntity.x) + diff);
                        }
                    } else if (movingNode) {
                        currentlySelectedNode.x = e.getX();
                        currentlySelectedNode.y = e.getY();
                    } else if (movingSpawnPoint) {
                        currentlySelectedSpawnPoint.x = e.getX();
                        currentlySelectedSpawnPoint.y = e.getY();
                    }
                }
                repaint();
            }
        });
    }

    public void deselectNode() {
        for (NiceLine line : displayList.lines) {
            line.nodeSelected = false;
        }
        for (DrawingFloor floor : displayList.drawingFloors) {
            floor.highlight = false;
        }
    }

    public void setSelected(IndexedNode node) {
        Ellipse2D ellipse = new Ellipse2D.Double(node.x - 10, node.y - 10, 20, 20);
        for (NiceLine line : displayList.lines) {
            if (ellipse.contains(line.getShape().getX1(), line.getShape().getY1())) {
                line.nodeSelected = true;
            }
        }
        for (DrawingFloor floor : displayList.drawingFloors) {
            if (floor.getShape().ID == node.ownerID) {
                floor.highlight = true;
            }
        }
    }

    public void setSelected(DrawingFloor drawingFloor) {
        Rectangle2D resizeBox = new Rectangle2D.Double();
        double x = drawingFloor.getBoundingBox().getX() + drawingFloor.getBoundingBox().getWidth();
        double y = drawingFloor.getBoundingBox().getY() + drawingFloor.getBoundingBox().getHeight();
        resizeBox.setRect(x - 5, y - 5, 10, 10);
        resizeBox1 = resizeBox;
        repaint();
    }
    public void setSelected(DrawingAcid drawingAcid) {
        Rectangle2D resizeBox = new Rectangle2D.Double();
        double x = drawingAcid.getBoundingBox().getX() + drawingAcid.getBoundingBox().getWidth();
        double y = drawingAcid.getBoundingBox().getY() + drawingAcid.getBoundingBox().getHeight();
        resizeBox.setRect(x - 5, y - 5, 10, 10);
        resizeBox1 = resizeBox;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2));

        g2.setColor(Color.ORANGE);
        for (DrawingFloor floor : displayList.drawingFloors) {
            if (floor.highlight) {
                g2.setColor(Color.YELLOW);
                g2.fill(floor.getShape());
                g2.setColor(Color.ORANGE);
            } else {
                g2.fill(floor.getShape());
            }
            if (floor.isAlsoWall) {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
                g2.draw(floor.getShape());
                g2.setColor(Color.ORANGE);
                g2.setStroke(new BasicStroke(2));
            }
        }
        for (DrawingAcid acid : displayList.drawingAcids) {
            acid.getAcid().paintComponent(g2);
        }

        g2.setColor(Color.magenta);
        for (IndexedNode node : displayList.nodes) {
            g2.fill(new Ellipse2D.Double(node.x - 10, node.y - 10, 20, 20));
        }

        for (NiceLine line : displayList.lines) {
            g2.setColor(Color.GREEN);
            if (!line.good) {
                g2.setColor(Color.BLUE);
                g2.draw(line.getShape());
                g2.setColor(Color.GREEN);
            } else if (line.nodeSelected) {
                g2.setColor(Color.YELLOW);
                g2.draw(line.getShape());
                g2.setColor(Color.GREEN);
            } else {
                g2.draw(line.getShape());
            }
            g2.setColor(Color.BLACK);
            Ellipse2D ellipse = new Ellipse2D.Double(line.getShape().getX1() - 3, line.getShape().getY1() - 3, 6, 6);
            g2.fill(ellipse);
        }

        g2.setColor(Color.RED);
        for (Vacuum vacuum : displayList.vacuums) {
            vacuum.paintComponent(g2);
            if (vacuum.getRightBound() > vacuum.x) {
                Line2D line = new Line2D.Double(vacuum.x, vacuum.y, vacuum.getRightBound(), vacuum.y);
                g2.draw(line);
            }
        }
        for (Yarnball yarnball : displayList.yarnballs) {
            yarnball.paintComponent(g2);
            if (yarnball.getRange() > 0) {
                Line2D line = new Line2D.Double(yarnball.x - yarnball.getRange(), yarnball.y, yarnball.x, yarnball.y);
                g2.draw(line);
            }
        }
        for (TinyMouse tinyMouse : displayList.tinyMice) {
            tinyMouse.paintComponent(g2);
            if (tinyMouse.getRightBound() > tinyMouse.x) {
                Line2D line = new Line2D.Double(tinyMouse.x, tinyMouse.y, tinyMouse.getRightBound(), tinyMouse.y);
                g2.draw(line);
            }
        }
        for (Squirrel squirrel : displayList.squirrels) {
            squirrel.paintComponent(g2);
        }

        if (settingSpawnSide) {
            g2.setColor(Color.GRAY);
            g2.fill(lSpawnRect);
            g2.fill(rSpawnRect);
            g2.fill(topSpawnRect);
            g2.fill(bottomSpawnRect);
        }
        for (SpawnPoint spawnPoint : new SpawnPoint[] { currentSpawnPoint, displayList.leftSpawnPoint, displayList.rightSpawnPoint,
            displayList.topSpawnPoint, displayList.bottomSpawnPoint }) {
            if (spawnPoint != null) {
                g2.drawImage(zinzanImage, spawnPoint.x, spawnPoint.y, 75, 50, null);
                if (spawnPoint.isFallTrap) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.GREEN);
                }
                g2.fill(new Ellipse2D.Double(spawnPoint.x - 10, spawnPoint.y - 10, 20, 20));
            }
        }

        g2.setColor(Color.magenta);
        if (currentlySelectedEntity != null) {
            g2.draw(currentlySelectedEntity.getHitBox());
        }

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        if (resizeBox1 != null) {
            g2.draw(resizeBox1);
            g2.setColor(Color.WHITE);
            g2.fill(resizeBox1);
        }

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(1));
        g2.draw(border);
    }


    public void SetTool(Tools tool) {
        currentlySelectedDrawingFloor = null;
        resizeBox1 = null;
        //resizeBox2 = null;
        currentTool = tool;
        repaint();
    }


}
