/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package audio;

import audio.SoundEffects.Volumes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;

/**
 * The mute button action listener. 
 * @author Jessica Medrzycki
 * @version December 7, 2017
 *
 */
public class MuteAction implements ActionListener {
    
    /** The checkbox menu item to check if it 'muted'. */
    private final JCheckBoxMenuItem myMute;
    
    /**
     * Constructs the action.
     * @param theMute  the button the check if it was selected. 
     */
    public MuteAction(final JCheckBoxMenuItem theMute) {
        myMute = theMute;
    }

    @Override 
    public void actionPerformed(final ActionEvent theEvent) {
        if (myMute.isSelected()) { 
            
            SoundEffects.TICK.setVolume(Volumes.MUTE);
            SoundEffects.FOURLINECLEAR.setVolume(Volumes.MUTE);
            SoundEffects.THREELINECLEAR.setVolume(Volumes.MUTE);
            SoundEffects.TWOLINECLEAR.setVolume(Volumes.MUTE);
            SoundEffects.LINECLEAR.setVolume(Volumes.MUTE);
            SoundEffects.GAMEOVER.setVolume(Volumes.MUTE);

        } else {
            
            SoundEffects.TICK.setVolume(Volumes.LOW);
            SoundEffects.FOURLINECLEAR.setVolume(Volumes.LOW);
            SoundEffects.THREELINECLEAR.setVolume(Volumes.LOW);
            SoundEffects.TWOLINECLEAR.setVolume(Volumes.LOW);
            SoundEffects.LINECLEAR.setVolume(Volumes.LOW);
            SoundEffects.GAMEOVER.setVolume(Volumes.LOW);
        }

    }

}
