import javax.swing.*;

public abstract class Level extends JPanel {

    protected final float GRAV = 0.5f;
    protected int GroundLevel;
    protected DisplayList displayList;
    public int getGroundLevel() {
        return GroundLevel;
    }
    public float getGRAV() {
        return GRAV;
    }
}
