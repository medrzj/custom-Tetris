/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Enum that creates the different sound effects for the game.
 * 
 * @author Jessica Medrzyckk
 * @version December 7, 2017
 * Code from:
 * {@link https://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html}
 * Sounds created by Austin Scheen. 
 * Game Over sound by noirenex {@link https://freesound.org/people/noirenex/sounds/159399/}
 *
 */
public enum SoundEffects {
    /** Sounds effects for a piece dropping. */
    TICK("sounds/PieceDrop4.wav"),
    
    /** Sound effect for a line cleared. */
    LINECLEAR("sounds/LineClear3.wav"),
     
    /** Sound for two lines cleared. */
    TWOLINECLEAR("sounds/LineClear4.wav"),
    
    /** Sound for three lines cleared. */
    THREELINECLEAR("sounds/Tetris3.wav"),
    
    /** Sound effect for a tetris. */ 
    FOURLINECLEAR("sounds/SoundFX3.wav"),
    
    /** Game Over. */
    GAMEOVER("sounds/powerDown.wav"),
    
    /** Tetris song. */
    MAINTHEME("sounds/Tetris3.wav"); //"sounds/TetrisTheme1.wav"
    
    /**
     * The volume.
     * @author Jessica Medrzycki
     * @version December 7, 2017.
     */
    public enum Volumes {
        
        /** The different volumes. */
        MUTE, LOW, MEDIUM, HIGH
    }

    /** Each sound effect has its own clip, loaded with its own sound file. */
    private Clip myClip;
    
    /** Initial volume. */
    private Volumes myVolume = Volumes.LOW;

    /**
     *  Constructor to construct each element of the enum with its own sound file.
     *  @param theSoundFileName     the name of the sound file.
     */
    SoundEffects(final String theSoundFileName) {
        try {
            final File soundFile = new File(theSoundFileName);
            final AudioInputStream audioInputStream = 
                            AudioSystem.getAudioInputStream(soundFile);
            
            
//            // Use URL (instead of File) to read from disk and JAR.
//            final URL url = this.getClass().getClassLoader().getResource(theSoundFileName);
//            // Set up an audio input stream piped from the sound file.
//            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            myClip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            myClip.open(audioInputStream);
            myVolume = Volumes.LOW;
        } catch (final UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the volume of the sounds. 
     * @param theVolume the volume to set it a sound at. 
     */
    public void setVolume(final Volumes theVolume) {
        myVolume = theVolume;
    }
    
    /**
     *  Play or Re-play the sound effect from the beginning, by rewinding.
     */
    public void play() {
        if (myVolume != Volumes.MUTE) {
            if (myClip.isRunning()) {
                myClip.stop();   // Stop the player if it is still running
            }
            myClip.setFramePosition(0); // rewind to the beginning
            myClip.start();     // Start playing
        }
    }
    
    /**
     * Loops the sounds. 
     */
    public void loop() {
        if (myVolume != Volumes.MUTE) {
            if (myClip.isRunning()) {
                myClip.stop();   // Stop the player if it is still running
            }
            myClip.setFramePosition(0); // rewind to the beginning
            myClip.loop(Clip.LOOP_CONTINUOUSLY);     // loops the clip
        }
    }

    /**
     * Stops the clip. 
     */
    public void stop() {
        if (myVolume != Volumes.MUTE && myClip.isRunning()) {
            myClip.stop();   // Stop the player if it is still running
        }
    }


    /** 
     * Pre-loads all the sound files.
     */
    public static void init() {
        values(); // calls the constructor for all the elements
    }

}
