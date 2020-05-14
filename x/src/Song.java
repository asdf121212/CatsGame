import javax.sound.sampled.*;

public class Song {

    private String filePath;
    private AudioInputStream stream;
    private SourceDataLine line;
    private FloatControl gainControl;

    //private boolean initialized = false;

    private float gain;

    public Song(String relativePath, float gain) {
        filePath = relativePath;
        this.gain = gain;
    }

//    public static void initializeLine() {
//        try {
//            line = AudioSystem.getSourceDataLine(null);
//            //gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
//        }
//        catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//    }

    public void increaseGain() {
        FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gain);
    }

    public void Stop() {
        line.stop();
        line.close();
    }

    public boolean isRunning() {
        return line.isRunning();
    }

    public void Start() {
        try {
            stream = AudioSystem.getAudioInputStream(Song.class.getResource(filePath));
            //if (!initialized) {
            line = AudioSystem.getSourceDataLine(stream.getFormat());
            line.open(stream.getFormat());
                //gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                //initialized = true;
            //} else {
                //line.open(stream.getFormat());
            //}
            while (!line.isOpen()) {
                Thread.sleep(50);
            }
            gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(gain);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        byte[] bytes = new byte[line.getBufferSize()];

        try {
            line.start();
            int numRedd = stream.read(bytes);
            while (numRedd != -1) {
                line.write(bytes, 0, numRedd);
                numRedd = stream.read(bytes);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
