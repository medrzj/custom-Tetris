/*
 * TCSS 305
 * Assignment 6 - Tetris
 */

package view;

import audio.MuteAction;
import audio.SoundEffects;
import audio.SoundEffects.Volumes;
import controller.KeyController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;
import themes.BlueColorTheme;
import themes.HuskyColorTheme;
import themes.PinkColorTheme;

/**
 * The Graphical display of the game. 
 * @author Jessica Medrzycki
 * @version December 1, 2017
 *
 */
public class TetrisGUI extends Observable implements Observer {

    /** The default delay (in milliseconds) for the move timer. */
    public static final int MOVE_DELAY = 1000;
    
    /** The frame of the game. */
    private final JFrame myFrame;
    
    /** The panel that holds the pieces in the game. */
    private TetrisGamePanel myGamePanel;
    
    /** The panel that holds the next piece. */
    private NextPiecePanel myNextPiecePanel;
    
    /** The tetris board. */
    private Board myBoard;
    
    /** The timer that steps the movement of the shapes. */
    private Timer myMoveTimer;
    
    /** The controller object. */
    private KeyController myKeyController;
    
    /** The panel that displays the controls.*/
    private KeyPanel myKeyPanel;

    /** The image shown for application when docked. */
    private final ImageIcon myImageIcon = new ImageIcon("./images/tetrisIcon.png"); 
    
    /** Game over image. */
    private ImageIcon myGameOverIcon = new ImageIcon("./images/rip_gravestone-512.png");
    
    /** The button that begins the game. */
    private JMenuItem myNewGameButton;
    
    /** The button that pauses the game. */
    private JCheckBoxMenuItem myPauseButton;
    
    /** The Button that ends the game. */
    private JMenuItem myEndGameButton;
    
    /** The mute button. */
    private JCheckBoxMenuItem myMute;
    
    /** The panel that displays the score. */
    private ScoringPanel myScoringPanel;

    /** The panel that holds the holding piece. */
    private HoldPiecePanel myHoldPiecePanel;
    
    /** The pause status of the game. */
    private boolean myGameStatus;
   
    
    /**
     * Sets up the UI. 
     */
    public TetrisGUI() {
        super();
        myFrame = new JFrame("Tetris");
        myFrame.setIconImage(myImageIcon.getImage()); 
        setUpGUI();
    }
    
    /**
     * Sets up the components of the graphical display. 
     */
    private void setUpGUI() {

        initializeGameComponents();
        
        createObservers();
        
        myFrame.setJMenuBar(setUpJMenuBar());
        
        stylingTheComponents();
        
        SoundEffects.MAINTHEME.loop();
        myMoveTimer.start();
        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        myFrame.requestFocusInWindow();
    }
    
    /**
     * Initializes the game components. 
     */
    private void initializeGameComponents() {

        myMoveTimer = new Timer(MOVE_DELAY, new TimerListener());
        myGamePanel = new TetrisGamePanel();
        myBoard = new Board();
        myNextPiecePanel = new NextPiecePanel();
        myScoringPanel = new ScoringPanel(myBoard, myMoveTimer);
        myKeyController = new KeyController(myBoard, myGamePanel);
        myKeyController.setMovable(false);
        myKeyPanel = new KeyPanel(myKeyController);
        myGameStatus = false;
        myHoldPiecePanel = new HoldPiecePanel();
        SoundEffects.init();
    }
    
    /**
     * Formats the components, and 
     * adds them to the frame. 
     */
    private void stylingTheComponents() {
        final Box box = new Box(BoxLayout.PAGE_AXIS);
        final JPanel sideBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JLabel space = new JLabel(" ");
        final JLabel space2 = new JLabel("   ");
        
        final JPanel westBar = new JPanel();
        westBar.setBackground(Color.GRAY);
        westBar.add(myHoldPiecePanel);
        
        sideBar.setBackground(Color.GRAY);
        box.add(myNextPiecePanel);
        box.add(space);
        box.add(myKeyPanel);
        box.add(space2);
        box.add(myScoringPanel);
        sideBar.add(box, BorderLayout.CENTER);
        myFrame.add(sideBar, BorderLayout.EAST);
        myFrame.add(westBar, BorderLayout.WEST);
        //Adds the game panel component
        final JPanel temp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        temp.setBackground(Color.GRAY);
        temp.add(myGamePanel);
        myFrame.add(temp, BorderLayout.CENTER);
    }
    
    /**
     * Adds the observers to the frame. 
     */
    private void createObservers() {
        myBoard.addObserver(myGamePanel);
        myBoard.addObserver(myNextPiecePanel);
        myBoard.addObserver(myScoringPanel);
        myBoard.addObserver(myHoldPiecePanel);
        myBoard.addObserver(this);
        addObserver(myGamePanel);
        addObserver(myNextPiecePanel);
        addObserver(myHoldPiecePanel);
        
        //sets the initial color theme
        setChanged();
        notifyObservers(new PinkColorTheme());
    }
    
    /** 
     * Sets up the menu bar. 
     * @return  the created menu. 
     */
    private JMenuBar setUpJMenuBar() {
        final JMenuBar menu = new JMenuBar();
        final JMenu file = new JMenu("Game");
        file.setMnemonic(KeyEvent.VK_G);
        final JMenu colors = createColorThemeMenu();
        final JMenu options = createOptionMenu();
        
        myNewGameButton = new JMenuItem("New Game", KeyEvent.VK_N);
        myPauseButton = new JCheckBoxMenuItem("Pause");
        myPauseButton.setMnemonic(KeyEvent.VK_P);
        myEndGameButton = new JMenuItem("End Game", KeyEvent.VK_E);
        myEndGameButton.setEnabled(false);
        myPauseButton.setEnabled(false);
        
        myNewGameButton.addActionListener(new NewGameListener());
        
        myPauseButton.addActionListener(new PauseListener());
        
        myEndGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myMoveTimer.stop();
                myNewGameButton.setEnabled(true);
                myEndGameButton.setEnabled(false);
                myPauseButton.setEnabled(false);
                myKeyController.setMovable(false);
                myGamePanel.showPauseMenu(false);
                myGamePanel.repaint();
            }
        });
       
        file.add(myNewGameButton);
        file.add(myPauseButton);
        file.add(myEndGameButton);
        menu.add(file); 
        menu.add(colors); 
        menu.add(options);
        return menu;
    }
    
    /**
     * Creates the JMenu for the color themes. 
     * @return  the created JMenu
     */
    private JMenu createColorThemeMenu() {
        final JMenu colors = new JMenu("Color Themes");
        colors.setMnemonic(KeyEvent.VK_C);
        final JCheckBoxMenuItem pink = new JCheckBoxMenuItem("Pink");
        final JCheckBoxMenuItem blue = new JCheckBoxMenuItem("Blue");
        final JCheckBoxMenuItem husky = new JCheckBoxMenuItem("Husky");
        final ButtonGroup group = new ButtonGroup();
        pink.setSelected(true);
        pink.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setChanged();
                notifyObservers(new PinkColorTheme());
            }
        
        });
        blue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setChanged();
                notifyObservers(new BlueColorTheme());
                
            }
            
        });
        husky.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setChanged();
                notifyObservers(new HuskyColorTheme());
                
            }
            
        });
        
        group.add(blue);
        group.add(pink);
        group.add(husky);
        colors.add(pink);
        colors.add(blue);
        colors.add(husky);
        return colors;
        
    }
    
    /**
     * Creates the options menu for the GUI. 
     * @return  the created options menu.
     */
    private JMenu createOptionMenu() {
        final JMenu options = new JMenu("Options");
        options.setMnemonic(KeyEvent.VK_O);
        final JMenuItem help = new JMenuItem("Help", KeyEvent.VK_H);
        myMute = new JCheckBoxMenuItem("Mute Sounds");
        myMute.setMnemonic(KeyEvent.VK_M);
        final JMenuItem muteMusic = new JCheckBoxMenuItem("Mute Music");
        muteMusic.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (muteMusic.isSelected()) {
                    SoundEffects.MAINTHEME.stop();
                 
                } else {
                    SoundEffects.MAINTHEME.loop();
                }
                
            }
            
        });

        final JCheckBoxMenuItem grid = new JCheckBoxMenuItem("Grid"); 
        myMute.addActionListener(new MuteAction(myMute));
        help.addActionListener(new HelpListener());
        grid.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (grid.isSelected()) {
                    myGamePanel.showGrid(true);
                } else {
                    myGamePanel.showGrid(false);
                }
            }
        });
        options.add(grid);
        options.addSeparator();
        options.add(muteMusic);
        options.add(myMute);
        options.addSeparator();
        options.add(help);
        return options;
    }
    

    /**
     * Observes when the board sends us the game 
     * over status. 
     */
    @Override
    public void update(final Observable theO, final Object theArg) {
        if (theArg instanceof Boolean) {
            final Image image = myGameOverIcon.getImage(); 
            final Image newimg = image.getScaledInstance(120, 120,  
                                                         java.awt.Image.SCALE_SMOOTH);  
            myGameOverIcon = new ImageIcon(newimg);
            SoundEffects.GAMEOVER.play();
            JOptionPane.showMessageDialog(myFrame, "GAME OVER", null, 0, myGameOverIcon);
            SoundEffects.TICK.setVolume(Volumes.MUTE);
            myEndGameButton.setEnabled(false);
            myPauseButton.setEnabled(false);
            myNewGameButton.setEnabled(true);
            myMoveTimer.stop();
            myKeyController.setMovable(false);
            
            
        }
        
    }
    
    /**
     * Inner class that listens for the new game button being pressed. 
     * @author Jessica Medrzycki
     * @version December 7, 2017
     *
     */
    private class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.newGame();
            myScoringPanel.resetGame();
            myMoveTimer.start();
            myGameStatus = true;
            myEndGameButton.setEnabled(true);
            myPauseButton.setEnabled(true);
            myNewGameButton.setEnabled(false);
            myKeyController.setMovable(true);
            myGamePanel.showPauseMenu(false);
            if (!myMute.isSelected()) {
                SoundEffects.TICK.setVolume(Volumes.LOW);
                SoundEffects.GAMEOVER.setVolume(Volumes.LOW);
            }
            setChanged();
            notifyObservers(false);
        }
    }

    /**
     * Inner class that listens for when the pause button has been pressed. 
     * @author Jessica Medrzycki
     * @version December 7, 2017
     *
     */
    private class PauseListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myGameStatus) {
                myGameStatus = false;
                myMoveTimer.stop();
                myKeyController.setMovable(false);
                myGamePanel.showPauseMenu(true);
                myGamePanel.repaint();

            } else {
                myGameStatus = true; 
                myGamePanel.showPauseMenu(false);
                myMoveTimer.start();
                myKeyController.setMovable(true);
                myGamePanel.showPauseMenu(false);
                myGamePanel.repaint();
            }
        }
    }
    
    /**
     * Inner class that listens for when the help 
     * button has been pressed.
     * @author Jessica Medrzycki
     * @version December 6, 2017
     *
     */
    private class HelpListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Image image = myImageIcon.getImage(); 
            final Image newimg = image.getScaledInstance(120, 120,  
                                                         java.awt.Image.SCALE_SMOOTH);  
            final ImageIcon finalImage = new ImageIcon(newimg);
            JOptionPane.showMessageDialog(myFrame,  "Every tetris game starts at Level 1 \n"
                            + "Level increases every 5 cleared lines \n"
                            + "For every dropped piece, you get 4 points. \n"
                            + "For every cleared line you get points depending \n"
                            + "on your level and number cleared. \n\n"
                            + "Single cleared lines: you get Level x 40 points. \n"
                            + "Double cleared lines: you get Level x 100 points. \n"
                            + "Triple cleared lines: you get Level x 300 points. \n"
                            + "Tetris(4) cleared lines: you get Level x 1200 points. \n", 
                            "Help Message", 0, finalImage);

        }
 
    }

    /**
     * Inner Class that reacts to the timer. 
     * @author Jessica Medrzycki
     *
     */
    public class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myGameStatus) {
                myBoard.step();
               // SoundEffects.TICK.play();
            }
        }

    }

    

    
}
