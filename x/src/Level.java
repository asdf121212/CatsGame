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
    //public int getGroundLevel() {
        //return GroundLevel;
    //}
    public float getGRAV() {
        return GRAV;
    }
    protected int levelWidth = 1200;
    protected int levelHeight = 700;

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//    }
    public void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    protected abstract void mouseClick(int x, int y);

    ///should be abstract
    protected abstract int getGroundLevel(int xCoord, int yCoord);

}
