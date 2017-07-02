import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {
    private Clip clip;
    public Sound(String fileName) {
        // specify the sound to play
        try {
            File file = new File("assets/sounds/" + fileName);
            if (file.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                AudioFormat baseFormat = audioStream.getFormat();
                //mp3support
                AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                        baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
                AudioInputStream sound = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
             // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
                audioStream.close();
                sound.close();
            }
            else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }
    public Sound(AudioInputStream ais) {
        // specify the sound to play
        try {

             // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(ais);
  
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }
    public void setVolume(float volume){
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);
    }
    // play, stop, loop the sound clip
    public void play(){
    	if(clip.isActive()) clip.stop();
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
            clip.stop();
    }
    public void close(){
    	clip.close();
    }
 }
