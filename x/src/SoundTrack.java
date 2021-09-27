import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this class doesn't do anything right now
public class SoundTrack {

    private Timer nextSongTimer;

    private Song ganymede = new Song("SoundFiles/Songs/Ganymede.aif", -2);
    private Song ancalagon = new Song("SoundFiles/Songs/Ancalagon.aif", -10);
    private Song sand = new Song("SoundFiles/Songs/Sand edit.aif", -10);
    private Song trinoculars = new Song("SoundFiles/Songs/Trinoculars.aif", -10);
    private Song[] songs = new Song[] {
            ganymede,
            ancalagon,
            sand,
            trinoculars
    };

    private Song currentSong;

    //public SoundTrack() {
//        for (Song track : songs) {
//            track.decreaseGain();
//        }
    //}

    private int songIndex = 0;
    //private Timer nextSongTimer;

    public void Stop() {
        //currentSong.
        nextSongTimer.stop();
        songs[songIndex].Stop();
    }

    public void Start() {
        nextSongTimer = new Timer(100, nextSong);
        songs[songIndex].Start();
        nextSongTimer.start();

//        Thread songThread = new Thread(() -> {
//            songs[songIndex].Start();
//            songIndex++;
//        });
        //songThread.start();

        //Thread songThread = new Thread(() -> {
//        while (songIndex < songs.length) {
//            currentSong = songs[songIndex];
//            songs[songIndex].Start();
//            songIndex++;
//        }
        //});
    }

    private ActionListener nextSong = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!songs[songIndex].isRunning()) {
                songIndex++;
                if (songIndex == songs.length) {
                    songIndex = 0;
                }
                songs[songIndex].Start();
            }
//            Thread songThread = new Thread(() -> {
//                songs[songIndex].Start();
//                songIndex++;
//            });
//            nextSongTimer.stop();
        }
    };

}
