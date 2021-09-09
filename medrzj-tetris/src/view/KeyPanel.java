/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;
import controller.KeyController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The panel that shows the keys to move the tetris pieces.
 * @author Jessica Medrzycki
 * @version December 1, 2017
 */
public class KeyPanel extends JPanel {

    /** Generated serial ID. */
    private static final long serialVersionUID = -6375099036281328565L;
    
    /** Size of the panel. */
    private static final Dimension PANEL_SIZE = new Dimension(220, 140);
    
    /** The font of the controls. */
    private static final String FONT = "Impact";
    
    /** The controls to be displayed. */
    private final KeyController myControls;
    
    /**
     * Creates the key panel.
     * @param theControls   the controls to display.
     */
    protected KeyPanel(final KeyController theControls) {
        super(); 
        myControls = theControls;
        setBackground(Color.WHITE); 
        setLayout(new BorderLayout());
        setPreferredSize(PANEL_SIZE);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));      
        createComponents();
        
       
    }
    

    /**
     * Creates the components on panel. 
     */
    private void createComponents() {
        
        final int controlsFont = 16;
        final int titleFont = 20;
        
        final JTextArea controls = new JTextArea(myControls.toString());
        controls.setFont(new Font(FONT, Font.PLAIN, controlsFont));

        final JLabel title = new JLabel("Controls", getWidth() / 3); 

        title.setFont(new Font(FONT, Font.PLAIN, titleFont));
        controls.setEditable(false);
        
      //  add(title, BorderLayout.NORTH);
        final JPanel temp = new JPanel(new BorderLayout());
        temp.add(controls, BorderLayout.CENTER);
        add(temp, BorderLayout.NORTH);
    }
     
}
