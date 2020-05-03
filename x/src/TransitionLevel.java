import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;


//will get to this eventually--- need to paint levels to an image buffer and switch them out
public class TransitionLevel extends Level{

//    private RoundRectangle2D pink = new RoundRectangle2D.Double(0, -15, 1200, 0, 15, 15);
//    private RoundRectangle2D yellow = new RoundRectangle2D.Double(0, -15, 1200, 0, 15, 15);
//    private RoundRectangle2D green = new RoundRectangle2D.Double(0, -15, 1200, 0, 15, 15);
//    private RoundRectangle2D cyan = new RoundRectangle2D.Double(0, -15, 1200, 0, 15, 15);
//    private RoundRectangle2D magenta = new RoundRectangle2D.Double(0, -15, 1200, 0, 15, 15);

//    private Timer dropTimer;
//    private int yHeightMark = 0;
    //private Timer reverseTimer;


    public TransitionLevel(Level previousLevel, Level nextLevel) {

    }

    public void Start() {
        //dropTimer = new Timer(5, drop);
        //dropTimer.start();
    }

//    public void Reverse() {
//        reverseTimer = new Timer(5, reverse);
//        yMark = 0;
//        reverseTimer.start();
//    }

    //public boolean isRunning() {
        //return dropTimer != null && dropTimer.isRunning();
    //}
//    public boolean isReversing() {
//        return reverseTimer != null && reverseTimer.isRunning();
//    }

//    private ActionListener reverse = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            yMark += 5;
//            pink.setRoundRect(yMark, -15, 1200, 155, 15, 15);
//            yellow.setRoundRect(yMark, -15, 1200, 295, 15, 15);
//            green.setRoundRect(yMark, -15, 1200, 435, 15, 15);
//            cyan.setRoundRect(yMark, -15, 1200, 575, 15, 15);
//            magenta.setRoundRect(yMark, -15, 1200, 725, 15, 15);
//        }
//    };

//    private ActionListener drop = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            yMark += 5;
//            if (yMark <= 140) {
//                pink.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                yellow.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                green.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                cyan.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                magenta.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//            } else if (yMark <= 280) {
//                yellow.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                green.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                cyan.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                magenta.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//            } else if (yMark <= 420) {
//                green.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                cyan.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                magenta.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//            } else if (yMark <= 560) {
//                cyan.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//                magenta.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//            } else if (yMark <= 700){
//                magenta.setRoundRect(0, -15, 1200, yMark + 15, 15, 15);
//            } else if (yMark >= 750) {
//                dropTimer.stop();
//            }
//            repaint();
//        }
//    };

//    @Override
//    protected void paintComponent(Graphics g) {
//        //super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setColor(Color.PINK);
//        g2.fill(pink);
//        g2.setColor(Color.YELLOW);
//        g2.fill(yellow);
//        g2.setColor(Color.GREEN);
//        g2.fill(green);
//        g2.setColor(Color.CYAN);
//        g2.fill(cyan);
//        g2.setColor(Color.MAGENTA);
//        g2.fill(magenta);
//    }

}
