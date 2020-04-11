import java.awt.*;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayList {

    public Cat cat;
    private ArrayList<Entity> entities;
    private ArrayList<Shape> shapesList;

    public DisplayList() {
        entities = new ArrayList<>();
        shapesList = new ArrayList<>();
        cat = new Cat();
    }

    public void AddShape(Shape shape) {
        shapesList.add(shape);
    }
    public void AddEntity(Entity entity) {
        entities.add(entity);
    }

    public ArrayList<Shape> getShapes() {
        return shapesList;
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

}
