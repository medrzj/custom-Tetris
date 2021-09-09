/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package controller;
import audio.SoundEffects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import model.Board;

/**
 * The key listener that listens for movement keys
 * and moves the tetris piece. 
 * 
 * @author Jessica Medrzycki
 * @version November 25, 2017
 */
public class KeyController { 
    
    /** The focus of the window. */
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    
    /** The expression for rotating clockwise. */
    private static final String ROTATE = "Rotate Clockwise";
    
    /** The expression for moving right.  */
    private static final String RIGHT = "Move Right";
    
    /** The expression for moving left. */
    private static final String LEFT = "Move Left";
    
    /** The expression for moving down. */
    private static final String DOWN = "Move Down";
    
    /** The expression for dropping. */
    private static final String DROP = "Drop";
    
    /** Space string. */
    private static final String SPACE = "SPACE";
    
    /** Hold Piece String. */
    private static final String HOLD = "Hold Piece";
    
//    /** Pause string. */
//    private static final String PAUSE = "Pause";

    /** The tetris board. */
    private final Board myBoard;
    
    /** If the piece is movable or not. */
    private boolean myPieceMoveableStatus;

    /** 
     * Collection containing the names of the actions and the keys
     * to do them.
     */
    private final Map<String, String> myToStringControls;
    
    /** The frame to set the input/action maps. */
    private final JComponent myFrame;
    
    
    /** Creates the key listener. 
     * @param   theBoard    the board to manipulate.
     * @param   theFrame    the frame to access the keybindings. 
     */
    public KeyController(final Board theBoard, final JComponent theFrame) { 
        super();
        myBoard = theBoard;
        myFrame = theFrame;
        myToStringControls = new HashMap<String, String>();
        myPieceMoveableStatus = false;
      

        createInputMap();
        createActionMap();
        createOtherActions();
        createDefaultControls();
    }
    
    /**
     * Creates the default controls for the game. 
     */
    private void createDefaultControls() {
        
        myToStringControls.put(ROTATE,  KeyEvent.getKeyText(KeyEvent.VK_W));
        myToStringControls.put(LEFT, KeyEvent.getKeyText(KeyEvent.VK_A));
        myToStringControls.put(DOWN, KeyEvent.getKeyText(KeyEvent.VK_S));
        myToStringControls.put(RIGHT, KeyEvent.getKeyText(KeyEvent.VK_D));
        myToStringControls.put(DROP, SPACE);

        
    }

    
    /**
     * Creates the input map for the key bindings. 
     */ 
    private void createInputMap() {
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), ROTATE);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), DOWN);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), LEFT);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), RIGHT);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), ROTATE);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), LEFT);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), DOWN);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), RIGHT);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke(SPACE), DROP);
//        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("P"), PAUSE);
        myFrame.getInputMap(IFW).put(KeyStroke.getKeyStroke("H"), HOLD);

    }
    
    /**
     * Creates the action map for the key bindings.
     */
    private void createActionMap() {
        myFrame.getActionMap().put(ROTATE, new AbstractAction() {

            /** Generated serial ID. */
            private static final long serialVersionUID = 6014944257389506420L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPieceMoveableStatus) {
                    SoundEffects.TICK.play();
                    myBoard.rotateCW();
                }
            }

        });
        myFrame.getActionMap().put(RIGHT, new AbstractAction() {

            /** Generated serial ID. */
            private static final long serialVersionUID = 4383408117540254775L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPieceMoveableStatus) {
                    SoundEffects.TICK.play();
                    myBoard.right();
                }
            }

        });
        myFrame.getActionMap().put(LEFT, new AbstractAction() {

            /** Generated serial ID. */
            private static final long serialVersionUID = -2020168438458490893L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPieceMoveableStatus) {
                    SoundEffects.TICK.play();
                    myBoard.left();
                }
            }

        });

    }
    /**
     * Helper method for creating actions. 
     */
    private void createOtherActions() {
        myFrame.getActionMap().put(DOWN, new AbstractAction() {

            /** Generated serial ID. */
            private static final long serialVersionUID = 6551104403822505424L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPieceMoveableStatus) {
                    SoundEffects.TICK.play();
                    myBoard.down();
                }
            }

        });
        myFrame.getActionMap().put(DROP, new AbstractAction() {

            /** Generated serial ID. */
            private static final long serialVersionUID = -8866416254711019307L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPieceMoveableStatus) {
                    SoundEffects.TICK.play();
                    myBoard.drop();
                }
            }

        });
        myFrame.getActionMap().put(HOLD, new AbstractAction() {

            /** Generated Serial UID. */
            private static final long serialVersionUID = 6604312186441851883L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.holdPiece();
            }

        });
//        myFrame.getActionMap().put(PAUSE, new AbstractAction() {
//
//            /** Generated serial ID. */
//            private static final long serialVersionUID = 6604312186441851883L;
//
//            @Override
//            public void actionPerformed(final ActionEvent theEvent) {
//                firePropertyChange("pause", false, true);
//            }
//
//        });
    }

    /**
     * A method that returns the string representation 
     * of the controls to display on the KeyPanel.
     * @return  the controls.
     */ 
    public String toString() {  
        final String spacer = "    ";
        return new String(spacer + ROTATE 
                          + ":       \u2191  OR " + myToStringControls.get(ROTATE)
                          + '\n' + spacer + LEFT + ":                      \u2190  OR " 
                          +  myToStringControls.get(LEFT)
                          + '\n' + spacer + RIGHT + ":                   \u2192  OR "
                          + myToStringControls.get(RIGHT)
                          + '\n' + spacer + DOWN + ":                     \u2193  OR " 
                          + myToStringControls.get(DOWN)
                          + '\n' + spacer + DROP 
                          + ":                                    " 
                          + myToStringControls.get(DROP)
                          + '\n' + spacer + HOLD + ":                                H");

    }

    /**
     * Sets if the piece can be moved or not. 
     * @param theStatus if the piece should be moveable. 
     */
    public void setMovable(final boolean theStatus) {
        myPieceMoveableStatus = theStatus;
    }

    
    
}

    