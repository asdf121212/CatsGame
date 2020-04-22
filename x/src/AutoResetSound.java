import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AutoResetSound {

    private Clip clip;
    private AudioInputStream stream;
    private Timer endTimer;

    public AutoResetSound(String relativePath) {
        try {
            stream = AudioSystem.getAudioInputStream(AutoResetSound.class.getResource(relativePath));
            clip = AudioSystem.getClip();
            clip.open(stream);
//            AudioFormat format = stream.getFormat();
//            DataLine.Info info = new DataLine.Info(Clip.class, format);
//            Clip clip = (Clip) AudioSystem.getLine(info);
//            return clip;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            stream = null;
            clip = null;
        }
    }

    public boolean canStart() {
        return (endTimer == null || !endTimer.isRunning());
    }
    public void Start() {
        try {
//            if (!canStart()) {
//                clip.stop();
//                stream.reset();
//            }
            clip.start();
            endTimer = new Timer(10, reset);
            endTimer.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());
        }
    }

    private ActionListener reset = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!clip.isRunning()) {
                try {
                    clip.setFramePosition(0);
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                finally {
                    endTimer.stop();
                }
            }
        }
    };

}
