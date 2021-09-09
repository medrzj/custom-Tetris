/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import audio.SoundEffects;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;
import model.TetrisPiece;

/**
 * The panel that displays the score.
 * 
 * @author Jessica Medrzycki
 * @version December 3, 2017
 */
public class ScoringPanel extends JPanel implements Observer {
    
    /** Generated serial UID. */
    private static final long serialVersionUID = -1891494144553251674L;

    /** Font for the panel. */
    private static final Font FONT = new Font("Impact", Font.BOLD, 18);
    
    /** Default size of panel. */
    private static final Dimension PANEL_SIZE = new Dimension(200, 120);
    
    /** Initial delay of the timer. */
    private static final int INITIAL_DELAY = 1000;
     
    /** The lines needed to get a next level. */
    private static final int LINES_PER_LEVEL = 5;
    
    /** The points for getting one line. */
    private static final int ONE_LINE_POINT = 40;
    
    /** The points for getting two lines. */
    private static final int TWO_LINE_POINT = 100;
    
    /** The points for getting three lines. */
    private static final int THREE_LINE_POINT = 300;
    
    /** The points for getting four lines. */
    private static final int FOUR_LINE_POINT = 1200;
    
    /** String for the score label. */
    private static final String SCORE_STRING = "Total Score:                            ";
    
    /** String for the level label. */
    private static final String LEVEL_STRING = 
                    "Level:                                            ";
      
    /** String for the lines cleared label. */
    private static final String LINES_CLEARED_STRING = "Total Lines Cleared:           ";
    
    /** String from the lines til next label. */
    private static final String LINES_TIL_NEXT_STRING = "Lines Until Next Level:      ";
    
    /** Initial score before board.newGame() is called. */
    private static final Integer SCORE_INCREMENT = 4;
    
    /** Initial score to display. */
    private static final String INITIAL_SCORE = " 0";
    
    /** The level counter. */
    private int myLevel;
    
    /** The lines cleared total. */
    private int myLinesCleared;
    
    /** The total score of the game. */
    private int myScore;
    
    /** The amount of lines needed to clear until next level. */
    private int myLinesTilNextLevel;
    
    /** The timer to increment points. */
    private final Timer myTimer;
    
    /** The score label. */
    private JLabel myScoreLabel;
    
    /** The lines cleared label. */
    private JLabel myLinesClearedLabel;
    
    /** The level label. */
    private JLabel myLevelLabel;
    
    /** The lines until the next level label. */
    private JLabel myLinesTilNextLabel;

    
    /** 
     * Constructs the scoring panel.
     * @param theBoard  the board the get updates from. 
     * @param theTimer  the timer to manipulate. 
     */
    public ScoringPanel(final Board theBoard, final Timer theTimer) {
        super();
        final Board board = theBoard;
        myTimer = theTimer;
        SoundEffects.init();
        setBackground(Color.WHITE); 
        setPreferredSize(PANEL_SIZE);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        board.addObserver(this);
        initializeFields();
        setUpGUI();
    }
    
    /**
     * Initializes the level and score of the game. 
     */
    private void initializeFields() {
        myLevel = 1;
        myScore = 0 - SCORE_INCREMENT;
        myLinesTilNextLevel = LINES_PER_LEVEL;
        myLinesCleared = 0;
    }
    
    /**
     * Sets up the components on the panel.
     */
    private void setUpGUI() {
        
        myScoreLabel = new JLabel(SCORE_STRING + INITIAL_SCORE);
        myScoreLabel.setFont(FONT);        
        myLevelLabel = new JLabel(LEVEL_STRING + myLevel);
        myLevelLabel.setFont(FONT);
        myLinesClearedLabel = new JLabel(LINES_CLEARED_STRING + myLinesCleared);
        myLinesClearedLabel.setFont(FONT);
        myLinesTilNextLabel = new JLabel(LINES_TIL_NEXT_STRING + myLinesTilNextLevel);
        myLinesTilNextLabel.setFont(FONT);

        final JPanel temp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        temp.setBackground(Color.WHITE);
       // temp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        final Box box = new Box(BoxLayout.Y_AXIS);
       
        box.add(myScoreLabel);
        box.add(myLevelLabel);
        box.add(myLinesClearedLabel);
        box.add(myLinesTilNextLabel);
        temp.add(box);
        add(temp);


    }
    

    /**
     * Updates when the board notifies that lines are cleared. 
     */
    @Override
    public void update(final Observable theO, final Object theArg) {
        if (theO instanceof Board  && theArg instanceof Integer[]) {
            final int linesCleared = ((Integer[]) theArg).length;
            calculateScore(linesCleared);
            calculateLevel(linesCleared);
             
        }
        if (theO instanceof Board && theArg instanceof TetrisPiece) {
            if (myScore < 0) {
                myScore = 0;
            }
            myScore = myScore + SCORE_INCREMENT;
            myScoreLabel.setText(SCORE_STRING + myScore);
        }
 
    }
    /** 
     * Calculates the level of the user. 
     * 
     * @param theLinesCleared   the number of lines cleared 
     */
    private void calculateLevel(final int theLinesCleared) {
        final int linesCleared = theLinesCleared;
        if (linesCleared == myLinesTilNextLevel) {
            myLevel = myLevel + 1;
            updateTimer(myLevel);
            myLinesTilNextLevel = LINES_PER_LEVEL;
            myLevelLabel.setText(LEVEL_STRING + myLevel);
            myLinesTilNextLabel.setText(LINES_TIL_NEXT_STRING + myLinesTilNextLevel);

            
        } else if (linesCleared > myLinesTilNextLevel) {
            myLevel = myLevel + 1;
            updateTimer(myLevel);
            
            final int leftover = linesCleared - myLinesTilNextLevel;
            myLinesTilNextLevel = LINES_PER_LEVEL - leftover;
            while (myLinesTilNextLevel <= 0) {
                myLevel = myLevel + 1;
                updateTimer(myLevel);
                myLinesTilNextLevel = myLinesTilNextLevel + LINES_PER_LEVEL;
            }
            myLevelLabel.setText(LEVEL_STRING + myLevel);
            myLinesTilNextLabel.setText(LINES_TIL_NEXT_STRING  + myLinesTilNextLevel);
            
        } else {
            myLinesTilNextLevel = myLinesTilNextLevel - linesCleared;
            myLinesTilNextLabel.setText(LINES_TIL_NEXT_STRING  + myLinesTilNextLevel);
        }
        
        myLinesCleared = myLinesCleared + linesCleared;
        myLinesClearedLabel.setText(LINES_CLEARED_STRING + myLinesCleared);
    }
    
    /**
    * Updates the timer delay based on the level. 
    * @param theLevel the level that changes the delay. 
    */
    private void updateTimer(final int theLevel) {
        final int maxLevel = 50;
        for (int level = 2; level <= maxLevel; level++) {
            if (theLevel == level) {
                final int delay = INITIAL_DELAY / level;
                myTimer.setDelay(delay);
                return;
            }
        }
 
    }
    
    /**
     * Calculates the score of the game.
     * 
     * @param theLinesCleared   the number of lines cleared
     */
    private void calculateScore(final int theLinesCleared) {
        final int linesCleared = theLinesCleared;
        final int lineCounter = 1;
        final int tetris = 4;
    
        if (linesCleared == lineCounter) {
            SoundEffects.LINECLEAR.play();
            myScore = myScore + (ONE_LINE_POINT * myLevel);
            myScoreLabel.setText(SCORE_STRING + myScore);
            
        } else if (linesCleared == lineCounter + 1) {
            SoundEffects.TWOLINECLEAR.play();
            myScore = myScore + (TWO_LINE_POINT * myLevel);
            myScoreLabel.setText(SCORE_STRING + myScore);
            
        } else if (linesCleared == lineCounter + 2) {
            SoundEffects.THREELINECLEAR.play();
            myScore = myScore + (THREE_LINE_POINT * myLevel);
            myScoreLabel.setText(SCORE_STRING + myScore);
            
        } else if (linesCleared == tetris) {
            SoundEffects.FOURLINECLEAR.play();
            myScore = myScore + (FOUR_LINE_POINT * myLevel);
            myScoreLabel.setText(SCORE_STRING + myScore);

        }
                       
    }

    
    /**
     * Resets the scores once new game has happened.
     */
    protected void resetGame() {
        myLevel = 1;
        myScore = 0 - SCORE_INCREMENT;
        myLinesTilNextLevel = LINES_PER_LEVEL;
        myLinesCleared = 0;
        myTimer.setDelay(INITIAL_DELAY);
        myScoreLabel.setText(SCORE_STRING + INITIAL_SCORE);
        myLevelLabel.setText(LEVEL_STRING + myLevel);
        myLinesClearedLabel.setText(LINES_CLEARED_STRING + myLinesCleared);
        myLinesTilNextLabel.setText(LINES_TIL_NEXT_STRING + myLinesTilNextLevel);

        repaint();
    }
    
 


}
