import java.io.Serializable;

public class EntityConfigurationObject implements Serializable {

    public int x;
    public int y;
    public int width;
    public int height;
    public int optionalRangeOrBound;

    public EntityConfigurationObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public EntityConfigurationObject(int x, int y, int optionalRangeOrBound) {
        this.x = x;
        this.y = y;
        this.optionalRangeOrBound = optionalRangeOrBound;
    }
    public EntityConfigurationObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
