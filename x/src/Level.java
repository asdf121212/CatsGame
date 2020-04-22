import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Level extends JPanel {

    protected final float GRAV = 0.5f;
    protected int GroundLevel;
    protected DisplayList displayList;
    protected ZinzanLife[] zinzanLives;
    protected int numLives = 3;
    public int getGroundLevel() {
        return GroundLevel;
    }
    public float getGRAV() {
        return GRAV;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//    }
    public void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    public int getGroundLevel(int xCoord) {
        return 350;
    }

}
