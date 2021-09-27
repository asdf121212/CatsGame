import com.sun.scenario.effect.impl.sw.java.JSWLinearConvolvePeer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AutoResetSound {

    private Clip clip;
    private AudioInputStream stream;
    private Timer endTimer;
    private FloatControl gainControl;

    public AutoResetSound(String relativePath) {
        try {
            stream = AudioSystem.getAudioInputStream(AutoResetSound.class.getResource(relativePath));
            clip = AudioSystem.getClip();
            clip.open(stream);
            //AudioFormat format = stream.getFormat();
            //DataLine.Info info = new DataLine.Info(Clip.class, format);
//            Clip clip = (Clip) AudioSystem.getLine(info);
//            return clip;
            //clip = (Clip) AudioSystem.getLine(info);
            decreaseGain();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            stream = null;
            clip = null;
        }
    }

    public void decreaseGain() {
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-5);
    }

    public boolean isRunning() {
        return clip.isRunning();
    }

    public boolean canStart() {
        return (endTimer == null || !endTimer.isRunning());
    }
    public void Start() {
        try {
//            if (!canStart()) {
//                endTimer.stop();
//                clip.stop();
//            }
            if (endTimer != null && endTimer.isRunning()) {
                endTimer.stop();
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
            endTimer = new Timer(10, reset);
            endTimer.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());
        }
    }
    public void Stop() {
        if (endTimer != null) {
            endTimer.stop();
        }
        if (clip.isRunning()) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    private ActionListener reset = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!clip.isRunning()) {
                try {
                    clip.stop();
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
